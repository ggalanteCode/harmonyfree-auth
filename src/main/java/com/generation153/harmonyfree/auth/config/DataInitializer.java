package com.generation153.harmonyfree.auth.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.generation153.harmonyfree.auth.entity.Role;
import com.generation153.harmonyfree.auth.repository.RoleRepository;
import com.generation153.harmonyfree.auth.security.enums.EnumRoles;

@Configuration
public class DataInitializer {

	@SuppressWarnings("unused")
	@Bean
	CommandLineRunner initRoles(RoleRepository roleRepository) {
		return args -> {

			if (roleRepository.findByRoleName(EnumRoles.ROLE_USER).isEmpty()) {
				roleRepository.save(new Role(EnumRoles.ROLE_USER));
			}

			if (roleRepository.findByRoleName(EnumRoles.ROLE_ADMIN).isEmpty()) {
				roleRepository.save(new Role(EnumRoles.ROLE_ADMIN));
			}
		};
	}
}
