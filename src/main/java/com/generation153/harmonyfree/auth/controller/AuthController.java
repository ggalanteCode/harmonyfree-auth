package com.generation153.harmonyfree.auth.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation153.harmonyfree.auth.dto.LoginRequestDto;
import com.generation153.harmonyfree.auth.dto.LoginResponseDto;
import com.generation153.harmonyfree.auth.dto.RegisterUserDto;
import com.generation153.harmonyfree.auth.entity.User;
import com.generation153.harmonyfree.auth.exception.BadRequestException;
import com.generation153.harmonyfree.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	private final AuthService authService;
	private final ModelMapper modelMapper;

	public AuthController(AuthService authService, ModelMapper modelMapper) {
		this.authService = authService;
		this.modelMapper = modelMapper;
	}
	
	@PostMapping("/register") 
	public ResponseEntity<String> register(@Valid @RequestBody RegisterUserDto dto) {
		
		log.info("AuthController: register: REGISTER CALLED");
		
		// Validazione logica
		if (!dto.getPassword().equals(dto.getConfirmPassword())) {
			throw new BadRequestException("Password e conferma password non coincidono");
		}
		// DTO -> entity
		User user = modelMapper.map(dto, User.class);
		authService.register(user);
		
		return ResponseEntity.status(HttpStatus.CREATED)
                .body("Utente registrato correttamente");
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
		
		String token = authService.login(dto.getEmail(), dto.getPassword());
		
		LoginResponseDto response = new LoginResponseDto();
		response.setToken(token);

        return ResponseEntity.ok(response);
	}

}
