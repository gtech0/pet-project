package com.proj.petproject.dto;

import jakarta.validation.constraints.Email;
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

    @Email(message = "Email is incorrect", regexp = "[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z]+")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank
    private String password;

}
