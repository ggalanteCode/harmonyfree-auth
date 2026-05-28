package com.generation153.harmonyfree.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation153.harmonyfree.auth.entity.Role;
import com.generation153.harmonyfree.auth.security.enums.EnumRoles;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByRoleName(EnumRoles roleName);
}
