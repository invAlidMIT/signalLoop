package com.notification.system.user.controller;

import com.notification.system.user.dto.UserRequestDTO;
import com.notification.system.user.dto.UserResponseDTO;
import com.notification.system.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping
    public UserResponseDTO createUser(@Valid @RequestBody UserRequestDTO userRequestDTO){
        return userService.createUser(userRequestDTO);
    }
}
