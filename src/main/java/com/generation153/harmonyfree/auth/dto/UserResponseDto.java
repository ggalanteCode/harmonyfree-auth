package com.generation153.harmonyfree.auth.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDto {
	
	private Integer id;
    private String email;
    private String status;
    private Set<String> roles;

}
