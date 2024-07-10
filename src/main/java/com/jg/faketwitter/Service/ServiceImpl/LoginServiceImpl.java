package com.jg.faketwitter.Service.ServiceImpl;

import com.jg.faketwitter.Controller.dto.LoginRequest;
import com.jg.faketwitter.Controller.dto.LoginResponse;
import com.jg.faketwitter.Model.Role;
import com.jg.faketwitter.Model.User;
import com.jg.faketwitter.Repositories.RoleRepository;
import com.jg.faketwitter.Repositories.UserRepository;
import com.jg.faketwitter.Service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final RoleRepository roleRepository;

    public LoginServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtEncoder jwtEncoder,
                            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.roleRepository = roleRepository;
    }
    @Override
    public ResponseEntity<LoginResponse> findByUsername(LoginRequest loginRequest){
        var user = userRepository.findByUsername(loginRequest.username());

        if (user.isEmpty() || !isLoginCorrect(loginRequest, user.get().getPassword())){
            throw new BadCredentialsException("user or password is invalid");
        }
        var now = Instant.now();
        var expiresIn = 300L;
        var scope = user.get().getRoles().stream().map(Role::getName).collect(Collectors.joining(" "));
        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.get().getId().toString())
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scope)
                .issuedAt(now).build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));

    }

    @Override
    public ResponseEntity createUser(LoginRequest loginRequest) {
        var roleAdmin = roleRepository.findByName(Role.Values.basic.name());
        var userFromDb = userRepository.findByUsername(loginRequest.username());
        if (userFromDb.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User user = new User();
        user.setUsername(loginRequest.username());
        user.setPassword(passwordEncoder.encode(loginRequest.password()));
        user.setRoles(Set.of(roleAdmin));
        userRepository.save(user);
        return ResponseEntity.ok().body("Usuario cadastrado");
    }

    private boolean isLoginCorrect(LoginRequest loginRequest, String password){
        return passwordEncoder.matches(loginRequest.password(), password);

    }

}
