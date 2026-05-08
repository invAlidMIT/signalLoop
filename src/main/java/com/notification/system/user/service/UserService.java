package com.notification.system.user.service;

import com.notification.system.user.dto.UserRequestDTO;
import com.notification.system.user.dto.UserResponseDTO;
import com.notification.system.user.entity.User;
import com.notification.system.auth.enums.Role;
import com.notification.system.user.exception.EmailExistException;
import com.notification.system.user.mapper.UserMapper;
import com.notification.system.user.repository.UserRepository;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        log.info("Creating user with email={}", userRequestDTO.getEmail());
        if(userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()){
            log.warn("User creation failed: email already exists, email={}", userRequestDTO.getEmail());
            throw new EmailExistException("Email already exists");
        }
        User user= userMapper.toEntity(userRequestDTO);
        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        User userSaved=userRepository.save(user);
        log.info("User created successfully with id={}", userSaved.getId());
        return userMapper.toResponse(userSaved);
    }

    public List<UserResponseDTO> getUsers(){
        return  userRepository.findAll().
                stream().
                map(userMapper::toResponse).
                toList();
    }

    public UserResponseDTO createAdmin(UserRequestDTO userRequestDTO){
        log.info("Creating ADMIN user with email={}", userRequestDTO.getEmail());
        if(userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()){
            log.warn("Admin creation failed: email already exists, email={}", userRequestDTO.getEmail());
            throw new EmailExistException("Email already exists");
        }
        User user=userMapper.toEntity(userRequestDTO);
        user.setRole(Role.ROLE_ADMIN);
        User userSaved=userRepository.save(user);
        log.info("Admin created successfully with id={}", userSaved.getId());
        return userMapper.toResponse(userSaved);
    }
}
