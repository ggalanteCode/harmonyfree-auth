package com.generation153.harmonyfree.auth.controller;

import org.modelmapper.ModelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation153.harmonyfree.auth.dto.PatchStatusDto;
import com.generation153.harmonyfree.auth.dto.RegisterUserDto;
import com.generation153.harmonyfree.auth.entity.User;
import com.generation153.harmonyfree.auth.exception.BadRequestException;
import com.generation153.harmonyfree.auth.service.AdminUserService;
import com.generation153.harmonyfree.auth.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminUserController {

    private final AuthService authService;
    
    private final AdminUserService adminUserService;
    
    private final ModelMapper modelMapper;

    public AdminUserController(AuthService authService, AdminUserService adminUserService, ModelMapper modelMapper) {
        this.authService = authService;
        this.adminUserService = adminUserService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/users")
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody RegisterUserDto dto) {

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new BadRequestException("Password e conferma password non coincidono");
        }

        User user = modelMapper.map(dto, User.class);
        authService.registerAdmin(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Admin registrato correttamente");
    }
    
    //TODO da testare
    @PatchMapping("/users/{id}/status")
    public ResponseEntity<Void> patchUserStatus(@PathVariable Integer id, @Valid @RequestBody PatchStatusDto dto) {
    	
    	adminUserService.updateUserStatus(id, dto.getStatus());

        return ResponseEntity.noContent().build();
    	
    }
    
}
