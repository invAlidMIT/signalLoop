package com.notification.system.controller;

import com.notification.system.dto.User.LoginRequestDTO;
import com.notification.system.dto.User.LoginResponseDTO;
import com.notification.system.dto.User.SignupResponseDTO;
import com.notification.system.security.JwtAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final JwtAuthService jwtAuthService;


    public AuthController(JwtAuthService jwtAuthService) {
        this.jwtAuthService = jwtAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(jwtAuthService.login(loginRequestDTO));
    }

    @PostMapping("signup")
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody LoginRequestDTO signupRequestDTO){
        return ResponseEntity.ok(jwtAuthService.signup(signupRequestDTO));
    }


}
