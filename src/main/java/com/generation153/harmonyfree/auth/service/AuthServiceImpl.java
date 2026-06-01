package com.generation153.harmonyfree.auth.service;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.generation153.harmonyfree.auth.entity.Role;
import com.generation153.harmonyfree.auth.entity.User;
import com.generation153.harmonyfree.auth.exception.BadRequestException;
import com.generation153.harmonyfree.auth.exception.DuplicateResourceException;
import com.generation153.harmonyfree.auth.repository.RoleRepository;
import com.generation153.harmonyfree.auth.repository.UserRepository;
import com.generation153.harmonyfree.auth.security.enums.EnumRoles;
import com.generation153.harmonyfree.auth.security.enums.EnumStatus;
import com.generation153.harmonyfree.auth.security.service.JwtService;

import jakarta.transaction.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           JwtService jwtService,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

	@Override
	@Transactional
	public void register(User user) {
		saveUserWithRoles(user, EnumRoles.ROLE_USER);
	}

	@Override
	@Transactional
	public void registerAdmin(User user) {
		saveUserWithRoles(user, EnumRoles.ROLE_USER, EnumRoles.ROLE_ADMIN);
	}

	private void saveUserWithRoles(User user, EnumRoles... roleNames) {

		// Email duplicata
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new DuplicateResourceException("Email già registrata");
		}

		// Crittografia della password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		// imposta lo status del nuovo utente, che inizialmente è ACTIVE
		user.setStatus(EnumStatus.ACTIVE);
		
		//imposta la data di creazione dell'utente
		user.setCreatedAt(LocalDateTime.now());

		// Assegna i ruoli
		for (EnumRoles roleName : roleNames) {
			Role role = roleRepository.findByRoleName(roleName)
					.orElseThrow(() -> new BadRequestException("Ruolo " + roleName + " non trovato"));
			user.addRole(role);
		}

		// Salva lo user
		userRepository.save(user);

	}

	@Override
	public String login(String email, String password) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(email, password)
				);

		return jwtService.generateToken(authentication);
	}

}
