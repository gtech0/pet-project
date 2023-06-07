package com.proj.petproject.controller;

import com.proj.petproject.dto.*;
import com.proj.petproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/registration", produces = "application/json")
    public ResponseEntity<JwtTokenDto> registration(@Valid @RequestBody RegistrationDto dto) {
        return userService.registration(dto);
    }

    @PostMapping(value = "/authorization", produces = "application/json")
    public ResponseEntity<JwtTokenDto> authorization(@Valid @RequestBody AuthDataDto dto) {
        return userService.authorization(dto);
    }

    @GetMapping(value = "/profile/{login}", produces = "application/json")
    public ResponseEntity<UserInfoDto> userProfileInfo(@PathVariable String login) {
        return userService.userProfileInfo(login);
    }

    @GetMapping(value = "/profile", produces = "application/json")
    public ResponseEntity<UserInfoDto> selfProfileInfo(Authentication authentication) {
        return userService.selfProfileInfo(authentication.getName());
    }

    @PutMapping(value = "/change", produces = "application/json")
    public ResponseEntity<ChangeProfileDto> changeProfile(@Valid @RequestBody ChangeProfileDto dto,
                                                          Authentication authentication) {
        return userService.changeProfile(dto, authentication.getName());
    }

    @DeleteMapping(value = "/delete", produces = "application/json")
    public ResponseEntity<StringDto> deleteProfile(Authentication authentication) {
        return userService.deleteProfile(authentication.getName());
    }
}
