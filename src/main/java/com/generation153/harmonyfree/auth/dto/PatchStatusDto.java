package com.generation153.harmonyfree.auth.dto;

import com.generation153.harmonyfree.auth.security.enums.EnumStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class PatchStatusDto {
	
	@NotNull(message = "Lo status è obbligatorio, e può essere solo uno tra i seguenti: ACTIVE, SUSPENDED, BANNED")
	private EnumStatus status;

}
