package com.notification.system.mapper;

import com.notification.system.dto.UserRequestDTO;
import com.notification.system.dto.UserResponseDTO;
import com.notification.system.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public  User toEntity(UserRequestDTO userRequestDTO){
        User user=new User();
        user.setEmail(userRequestDTO.getEmail());
        user.setPreferredChannel(userRequestDTO.getPreferredChannel());
        user.setTimezone(userRequestDTO.getTimezone());
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
