package com.proj.petproject.service;

import com.proj.petproject.config.JwtService;
import com.proj.petproject.dto.JwtTokenDto;
import com.proj.petproject.dto.RegistrationDto;
import com.proj.petproject.entity.RoleEnum;
import com.proj.petproject.entity.UserEntity;
import com.proj.petproject.exception.UniqueConstraintViolationException;
import com.proj.petproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public ResponseEntity<JwtTokenDto> registration(RegistrationDto dto) {

        if (userRepository.findByLogin(dto.getLogin()).isPresent()) {
            log.error("User " + dto.getLogin() + " already exists");
            throw new UniqueConstraintViolationException("User " + dto.getLogin() + " already exists");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            log.error("Email " + dto.getEmail() + " is already used");
            throw new UniqueConstraintViolationException("Email " + dto.getEmail() + " is already used");
        }

        UserEntity user = UserEntity.builder()
                .id(UUID.randomUUID())
                .login(dto.getLogin())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(RoleEnum.USER)
                .numberOfEdits(0)
                .registrationTime(new Date())
                .build();
        userRepository.save(user);

        return ResponseEntity.ok(new JwtTokenDto(jwtService.generateToken(user)));
    }

}
