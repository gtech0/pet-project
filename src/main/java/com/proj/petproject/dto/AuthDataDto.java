package com.proj.petproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDataDto {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

}
