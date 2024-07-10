package com.jg.faketwitter.Service.ServiceImpl;

import com.jg.faketwitter.Repositories.UserRepository;
import com.jg.faketwitter.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity listAll() {
        return ResponseEntity.ok().body(userRepository.findAll());
    }
}
