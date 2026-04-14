package com.notification.system.controller;

import com.notification.system.dto.UserRequestDTO;
import com.notification.system.dto.UserResponseDTO;
import com.notification.system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/all")
    public List<UserResponseDTO> getUsers(){
        return userService.getUsers();
    }
}
