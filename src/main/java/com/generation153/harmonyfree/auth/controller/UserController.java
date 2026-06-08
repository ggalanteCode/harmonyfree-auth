package com.generation153.harmonyfree.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation153.harmonyfree.auth.dto.UpdateEmailRequestDto;
import com.generation153.harmonyfree.auth.dto.UpdateEmailResponseDto;
import com.generation153.harmonyfree.auth.dto.UserResponseDto;
import com.generation153.harmonyfree.auth.security.model.CustomUserPrincipal;
import com.generation153.harmonyfree.auth.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		
		log.info("EMAIL JWT = " + principal.getEmail());
		
        UserResponseDto user = userService.getCurrentUser(principal.getEmail());

        return ResponseEntity.ok(user);
        
    }
	
	@PatchMapping("/me/email")
	public ResponseEntity<UpdateEmailResponseDto> patchEmail(
	        Authentication authentication,
	        @RequestBody UpdateEmailRequestDto request) {

	    CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();

	    UpdateEmailResponseDto response = userService.patchEmail(principal.getEmail(), request);

	    return ResponseEntity.ok(response);
	}

}
