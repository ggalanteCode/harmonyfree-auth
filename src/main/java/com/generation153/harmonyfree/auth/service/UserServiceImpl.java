package com.generation153.harmonyfree.auth.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.generation153.harmonyfree.auth.dto.UpdateEmailRequestDto;
import com.generation153.harmonyfree.auth.dto.UpdateEmailResponseDto;
import com.generation153.harmonyfree.auth.dto.UserResponseDto;
import com.generation153.harmonyfree.auth.entity.User;
import com.generation153.harmonyfree.auth.exception.ResourceNotFoundException;
import com.generation153.harmonyfree.auth.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto getCurrentUser(String email) {
    	
    	log.info("SEARCHING USER WITH EMAIL = " + email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getStatus().name(),
                user.getRoles()
                        .stream()
                        .map(role -> role.getRoleName().name())
                        .collect(Collectors.toSet())
        );
    }

	@Override
	public UpdateEmailResponseDto patchEmail(String currentEmail, UpdateEmailRequestDto request) {
		User user =
	            userRepository
	                .findByEmail(currentEmail)
	                .orElseThrow(
	                    () -> new ResourceNotFoundException(
	                        "User not found"
	                    )
	                );

	    user.setEmail(request.getEmail());

	    user = userRepository.save(user);

	    return mapToDto(user);
	}

	private UpdateEmailResponseDto mapToDto(User user) {
		
		return new UpdateEmailResponseDto(user.getEmail());
		
	}

}
