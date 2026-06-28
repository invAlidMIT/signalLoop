package com.notification.system.user.mapper;

import com.notification.system.user.dto.UserRequestDTO;
import com.notification.system.user.dto.UserResponseDTO;
import com.notification.system.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public  User toEntity(UserRequestDTO userRequestDTO){
        User user=new User();
        user.setEmail(userRequestDTO.getEmail());
        user.setPreferredChannel(userRequestDTO.getPreferredChannel());
        user.setTimezone(userRequestDTO.getTimezone());
        user.setPassword(userRequestDTO.getPassword());
        return user;
    }

    public  UserResponseDTO toResponse(User user){
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPreferredChannel(user.getPreferredChannel());
        userResponseDTO.setTimezone(user.getTimezone());
        userResponseDTO.setCreatedAt(user.getCreatedAt());
        return userResponseDTO;
    }
}
