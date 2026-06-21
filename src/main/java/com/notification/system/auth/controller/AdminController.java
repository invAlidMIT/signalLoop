package com.notification.system.auth.controller;

import com.notification.system.notification.scoringAlogirthm.dto.FactorWeightsRequestDTO;
import com.notification.system.notification.scoringAlogirthm.service.FactorPercentageService;
import com.notification.system.user.dto.UserRequestDTO;
import com.notification.system.user.dto.UserResponseDTO;
import com.notification.system.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final FactorPercentageService factorPercentageService;

    @GetMapping("/all")
    public List<UserResponseDTO> getUsers(){
        return userService.getUsers();
    }

    @PostMapping
    public UserResponseDTO createAdmin(@Valid @RequestBody UserRequestDTO userRequestDTO){
        return userService.createAdmin(userRequestDTO);
    }

    @PutMapping("/scoring/factors")
    public void updateFactorsWeight(@Valid @RequestBody FactorWeightsRequestDTO factorWeightsRequestDTO){
        factorPercentageService.updateFactors(factorWeightsRequestDTO);
    }
}
