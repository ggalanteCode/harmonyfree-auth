package com.generation153.harmonyfree.auth.service;

import com.generation153.harmonyfree.auth.dto.UserResponseDto;

public interface UserService {
	
	UserResponseDto getCurrentUser(String name);

}
