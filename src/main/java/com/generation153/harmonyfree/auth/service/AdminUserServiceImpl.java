package com.generation153.harmonyfree.auth.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.generation153.harmonyfree.auth.entity.User;
import com.generation153.harmonyfree.auth.exception.BadRequestException;
import com.generation153.harmonyfree.auth.exception.ResourceNotFoundException;
import com.generation153.harmonyfree.auth.repository.UserRepository;
import com.generation153.harmonyfree.auth.security.enums.EnumStatus;
import com.generation153.harmonyfree.auth.security.model.CustomUserPrincipal;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdminUserServiceImpl implements AdminUserService {
	
	private final UserRepository userRepository;

    public AdminUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

	@Override
	public void updateUserStatus(Integer userId, EnumStatus status) {
		
		User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));
		
		//estrai l'authUserId dal token
		CustomUserPrincipal principal = (CustomUserPrincipal) SecurityContextHolder
				.getContext()
			    .getAuthentication()
			    .getPrincipal();
		Integer authenticatedUserId = principal.getUserId();
			
		//un admin non può modificare il proprio status.
		if (authenticatedUserId.equals(userId)) {
			throw new BadRequestException("Non è possibile modificare il proprio status");
		}
		
		//Un ADMIN può modificare lo status degli USER,
		//ma non quello di altri ADMIN.
		if (user.hasRole("ROLE_ADMIN")) {
			throw new BadRequestException("Non è possibile modificare lo status di un amministratore");
		}

        user.setStatus(status);

        userRepository.save(user);
		
	}

}
