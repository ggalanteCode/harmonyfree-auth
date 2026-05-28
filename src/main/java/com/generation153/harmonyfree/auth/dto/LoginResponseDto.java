package com.generation153.harmonyfree.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class LoginResponseDto {

    private String token;
    private String type = "Bearer";

}
