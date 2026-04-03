package com.notification.system.service;

import com.notification.system.dto.UserRequestDTO;
import com.notification.system.dto.UserResponseDTO;
import com.notification.system.entity.User;
import com.notification.system.mapper.UserMapper;
import com.notification.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        User user= UserMapper.toEntity(userRequestDTO);
        User userSaved=userRepository.save(user);
        return UserMapper.toResponse(userSaved);
    }
}
