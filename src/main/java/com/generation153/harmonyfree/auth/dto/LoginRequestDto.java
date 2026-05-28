package com.generation153.harmonyfree.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {

	@NotBlank(message = "L'email è obbligatoria")
	@Email(message = "Email non valida")
	@Pattern(
			regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
			message = "L'email non rispetta il formato richiesto"
			)
	private String email;
	
	@NotBlank(message = "La password è obbligatoria")
	@Size(min = 8, max = 100, message = "La password deve contenere almeno 8 caratteri")
	private String password;

}
