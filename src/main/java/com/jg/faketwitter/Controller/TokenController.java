package com.jg.faketwitter.Controller;

import com.jg.faketwitter.Controller.dto.LoginRequest;
import com.jg.faketwitter.Controller.dto.LoginResponse;
import com.jg.faketwitter.Service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private final LoginService loginService;

    public TokenController(LoginService loginService) {
        this.loginService = loginService;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        return loginService.findByUsername(loginRequest);
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody LoginRequest loginRequest){
        return loginService.createUser(loginRequest);
    }

}
