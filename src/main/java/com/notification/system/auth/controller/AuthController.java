package com.notification.system.auth.controller;

import com.notification.system.auth.dto.LoginRequestDTO;
import com.notification.system.auth.dto.LoginResponseDTO;
import com.notification.system.auth.dto.SignupResponseDTO;
import com.notification.system.auth.service.JwtAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
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

    @Operation(
            summary = "Login",
            description = "Authenticate user and return JWT"
    )
    @SecurityRequirements
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(jwtAuthService.login(loginRequestDTO));
    }

    @PostMapping("signup")
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody LoginRequestDTO signupRequestDTO){
        return ResponseEntity.ok(jwtAuthService.signup(signupRequestDTO));
    }


}
