package com.notification.system.controller;

import com.notification.system.dto.User.UserRequestDTO;
import com.notification.system.dto.User.UserResponseDTO;
import com.notification.system.service.UserService;
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
