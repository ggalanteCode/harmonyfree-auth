package com.generation153.harmonyfree.auth.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.generation153.harmonyfree.auth.dto.UserResponseDto;
import com.generation153.harmonyfree.auth.entity.User;
import com.generation153.harmonyfree.auth.exception.ResourceNotFoundException;
import com.generation153.harmonyfree.auth.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto getCurrentUser(String email) {

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

}
