package com.generation153.harmonyfree.auth.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.generation153.harmonyfree.auth.entity.User;
import com.generation153.harmonyfree.auth.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;

	// aggiunto
	private final UserRepository userRepository;

	// aggiunto
	public JwtService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	// GENERAZIONE TOKEN
	public String generateToken(Authentication authentication) {

		// aggiunto per recuperare l'ID dello user
		String email = authentication.getName();
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Utente non trovato: " + email));

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + expiration);

		List<String> roles = authentication.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.toList();

		return Jwts.builder()
				//.subject(authentication.getName())
				.subject(email)
				// aggiunto
				.claim("id", user.getId())
				.claim("roles", roles)
				.issuedAt(now)
				.expiration(expiryDate)
				.signWith(getSigningKey())
				.compact();
	}

	// METODO INTERNO
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith((javax.crypto.SecretKey) getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	// EMAIL (SUBJECT)
	public String extractEmail(String token) {
		return extractAllClaims(token).getSubject();
	}

	// RUOLI
	public List<String> extractRoles(String token) {
		List<?> roles = extractAllClaims(token).get("roles", List.class);
		return roles.stream()
				.map(Object::toString)
				.toList();
	}

	// VALIDAZIONE TOKEN
	public boolean isTokenValid(String token) {
		try {
			extractAllClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}
