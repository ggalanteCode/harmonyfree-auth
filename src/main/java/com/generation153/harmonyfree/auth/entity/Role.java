package com.generation153.harmonyfree.auth.entity;

import java.util.Objects;

import com.generation153.harmonyfree.auth.security.enums.EnumRoles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true, nullable = false)
	@Enumerated(EnumType.STRING)
	private EnumRoles roleName;
	
	// ------------------------------- CONSTRUCTOR ----------------------------------
	
	public Role(EnumRoles roleName) { // serve per inizializzare la tabella roles
		this.roleName = roleName;
	}

	// ------------------------------- EQUALS & HASHCODE ----------------------------------
	
	@Override
	public int hashCode() {
		return Objects.hash(roleName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Role other = (Role) obj;
		return Objects.equals(roleName, other.roleName);
	}

}
