package com.generation153.harmonyfree.auth.service;

import com.generation153.harmonyfree.auth.dto.UpdateEmailRequestDto;
import com.generation153.harmonyfree.auth.dto.UpdateEmailResponseDto;
import com.generation153.harmonyfree.auth.dto.UserResponseDto;

public interface UserService {
	
	UserResponseDto getCurrentUser(String name);

	UpdateEmailResponseDto patchEmail(String email, UpdateEmailRequestDto request);

}
