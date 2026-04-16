package com.notification.system.security;

import com.notification.system.dto.LoginRequestDTO;
import com.notification.system.dto.LoginResponseDTO;
import com.notification.system.dto.SignupResponseDTO;
import com.notification.system.entity.User;
import com.notification.system.enums.Role;
import com.notification.system.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtAuthUtil jwtAuthUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtAuthService(AuthenticationManager authenticationManager,
                          JwtAuthUtil jwtAuthUtil,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtAuthUtil = jwtAuthUtil;
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public LoginResponseDTO login(@Valid LoginRequestDTO loginRequestDTO) {

        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(),loginRequestDTO.getPassword()));

        User user=(User) authentication.getPrincipal();

        String token= jwtAuthUtil.generateAccessToken(user);
        return new LoginResponseDTO(token,user.getId());
    }

    public SignupResponseDTO signup(@Valid LoginRequestDTO signupRequestDTO){
        Optional<User> exist=userRepository.findByEmail(signupRequestDTO.getEmail());
        if(exist.isPresent()){
            throw new IllegalStateException("User is already there!");
        }
        User user=userRepository.save(
                new User(signupRequestDTO.getEmail(),
                passwordEncoder.encode(signupRequestDTO.getPassword()),
                signupRequestDTO.getPreferredChannel(),
                signupRequestDTO.getTimezone(),
                        Role.ROLE_USER));
        return new SignupResponseDTO(user.getId(),user.getUsername());
    }


}
