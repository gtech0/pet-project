package com.proj.petproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {

    @NotBlank
    private String login;

    @NotBlank
    private String fullName;

    @NotBlank
    private String password;

}
