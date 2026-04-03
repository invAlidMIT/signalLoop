package com.notification.system.mapper;

import com.notification.system.dto.UserRequestDTO;
import com.notification.system.dto.UserResponseDTO;
import com.notification.system.entity.User;

public class UserMapper {

    public static User toEntity(UserRequestDTO userRequestDTO){
        User user=new User();
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        user.setPreferredChannel(userRequestDTO.getPreferredChannel());
        user.setTimezone(userRequestDTO.getTimezone());
        return user;
    }

    public static UserResponseDTO toResponse(User user){
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPreferredChannel(user.getPreferredChannel());
        userResponseDTO.setTimezone(user.getTimezone());
        userResponseDTO.setCreatedAt(user.getCreatedAt());
        return userResponseDTO;
    }
}
