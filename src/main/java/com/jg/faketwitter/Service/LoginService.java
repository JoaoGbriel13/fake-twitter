package com.jg.faketwitter.Service;


import com.jg.faketwitter.Controller.dto.LoginRequest;
import com.jg.faketwitter.Controller.dto.LoginResponse;
import com.jg.faketwitter.Model.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface LoginService {
    public ResponseEntity<LoginResponse> findByUsername(LoginRequest loginRequest);
    public ResponseEntity createUser(LoginRequest loginRequest);
}
