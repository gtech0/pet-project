package com.proj.petproject.controller;

import com.proj.petproject.dto.JwtTokenDto;
import com.proj.petproject.dto.RegistrationDto;
import com.proj.petproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<JwtTokenDto> registration(@Valid @RequestBody RegistrationDto dto) {
        return userService.registration(dto);
    }
}
