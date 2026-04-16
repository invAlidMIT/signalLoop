package com.notification.system.controller;

import com.notification.system.dto.UserRequestDTO;
import com.notification.system.dto.UserResponseDTO;
import com.notification.system.entity.User;
import com.notification.system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/all")
    public List<UserResponseDTO> getUsers(){
        return userService.getUsers();
    }

    @PostMapping
    public UserResponseDTO createAdmin(@Valid @RequestBody UserRequestDTO userRequestDTO){
        return userService.createAdmin(userRequestDTO);
    }
}
