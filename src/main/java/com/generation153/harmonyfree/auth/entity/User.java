package com.generation153.harmonyfree.auth.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true, nullable = false, length = 150)
	private String email;

	@JsonIgnore // la password non viene né letta né scritta nel JSON
	@Column(nullable = false)
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role",
	joinColumns = { 
			@JoinColumn(name = "id_user",
					referencedColumnName = "id", nullable = false)
	},
	inverseJoinColumns = { 
			@JoinColumn(name = "id_role",
					referencedColumnName = "id", nullable = false)
	})
	private Set<Role> roles = new HashSet<>();

	
	// ------------------------------- HELPER ----------------------------------
	
	public void addRole(Role role) {
		if (role == null) {
			throw new IllegalArgumentException("role nullo");
		}
		roles.add(role);
	}
	
	public void removeRole(Role role) {
		if (role == null) {
			throw new IllegalArgumentException("role nullo");
		}
		this.roles.remove(role);
	}
	
	/**
	 * Verifica se l'utente possiede un determinato ruolo.
	 */
	public boolean hasRole(String roleName) {
	    if (roleName == null || roleName.isBlank()) {
	        return false;
	    }

	    return roles.stream()
	            .anyMatch(role -> role.getRoleName().name().equalsIgnoreCase(roleName));
	}
	
	/**
	 * Rimuove tutti i ruoli dell'utente.
	 */
	public void clearRoles() {
	    this.roles.clear();
	    log.info("Tutti i ruoli rimossi dall'utente {}", email);
	}
	
	// ------------------------------- EQUALS & HASHCODE ----------------------------------
	
	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		User other = (User) obj;
		return Objects.equals(email, other.email);
	}
	
}
