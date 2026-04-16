package com.notification.system.service;

import com.notification.system.dto.UserRequestDTO;
import com.notification.system.dto.UserResponseDTO;
import com.notification.system.entity.User;
import com.notification.system.enums.Role;
import com.notification.system.mapper.UserMapper;
import com.notification.system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        if(userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()){
            throw new IllegalStateException("Email already exists");
        }
        User user= userMapper.toEntity(userRequestDTO);
        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        User userSaved=userRepository.save(user);
        return userMapper.toResponse(userSaved);
    }

    public List<UserResponseDTO> getUsers(){
        return  userRepository.findAll().
                stream().
                map(userMapper::toResponse).
                toList();
    }

    public UserResponseDTO createAdmin(UserRequestDTO userRequestDTO){
        if(userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()){
            throw new IllegalStateException("Email already exists");
        }
        User user=userMapper.toEntity(userRequestDTO);
        user.setRole(Role.ROLE_ADMIN);
        User userSaved=userRepository.save(user);
        return userMapper.toResponse(userSaved);
    }
}
