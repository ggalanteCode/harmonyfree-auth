package com.generation153.harmonyfree.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation153.harmonyfree.auth.dto.UserResponseDto;
import com.generation153.harmonyfree.auth.security.model.CustomUserPrincipal;
import com.generation153.harmonyfree.auth.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	private final UserService userService;

    public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser(Authentication authentication) {

		CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
		
        UserResponseDto user = userService.getCurrentUser(principal.getEmail());

        return ResponseEntity.ok(user);
        
    }

}
