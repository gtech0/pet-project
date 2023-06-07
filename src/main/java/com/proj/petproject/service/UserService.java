package com.proj.petproject.service;

import com.proj.petproject.config.JwtService;
import com.proj.petproject.dto.*;
import com.proj.petproject.entity.RoleEnum;
import com.proj.petproject.entity.UserEntity;
import com.proj.petproject.exception.UniqueConstraintViolationException;
import com.proj.petproject.exception.UserNotFoundException;
import com.proj.petproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<JwtTokenDto> registration(RegistrationDto dto) {

        if (userRepository.findByLogin(dto.getLogin()).isPresent()) {
            log.error("User " + dto.getLogin() + " already exists");
            throw new UniqueConstraintViolationException("User " + dto.getLogin() + " already exists");
        }

        UserEntity user = UserEntity.builder()
                .id(UUID.randomUUID())
                .login(dto.getLogin())
                .fullName(dto.getFullName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(RoleEnum.USER)
                .build();
        userRepository.save(user);

        return ResponseEntity.ok(new JwtTokenDto(jwtService.generateToken(user)));
    }

    public ResponseEntity<JwtTokenDto> authorization(AuthDataDto dto) {
        if (userRepository.findByLogin(dto.getLogin()).isEmpty()) {
            log.debug("User " + dto.getLogin() + " doesn't exist");
            throw new UserNotFoundException("User " + dto.getLogin() + " doesn't exist");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getLogin(),
                        dto.getPassword()
                )
        );
        UserEntity user = userRepository.findByLogin(dto.getLogin()).get();

        return ResponseEntity.ok(new JwtTokenDto(jwtService.generateToken(user)));
    }

    public ResponseEntity<UserInfoDto> userProfileInfo(String login) {
        Optional<UserEntity> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            log.info("User doesn't exist");
            throw new UserNotFoundException("User doesn't exist");
        }

        return ResponseEntity.ok(new UserInfoDto(
                user.get().getLogin(),
                user.get().getFullName()
        ));
    }

    public ResponseEntity<UserInfoDto> selfProfileInfo(String login) {
        return userProfileInfo(login);
    }

    @Transactional
    public ResponseEntity<ChangeProfileDto> changeProfile(ChangeProfileDto dto, String login) {
        Optional<UserEntity> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            log.info("User doesn't exist");
            throw new UserNotFoundException("User doesn't exist");
        }

        user.get().setFullName(dto.getFullName());
        userRepository.save(user.get());

        return ResponseEntity.ok(dto);
    }

    @Transactional
    public ResponseEntity<StringDto> deleteProfile(String login) {
        Optional<UserEntity> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            log.info("User doesn't exist");
            throw new UserNotFoundException("User doesn't exist");
        }
        userRepository.deleteByLogin(login);

        return ResponseEntity.ok(new StringDto("User " + login + " successfully deleted"));
    }

}
