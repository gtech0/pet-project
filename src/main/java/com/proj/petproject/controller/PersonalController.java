package com.proj.petproject.controller;

import com.proj.petproject.dto.*;
import com.proj.petproject.service.PersonalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/personal")
@RequiredArgsConstructor
public class PersonalController {

    private final PersonalService personalService;

    @PostMapping("/new/task")
    public ResponseEntity<StringDto> addTask(@RequestBody CreatePersonalTaskDto dto,
                                             Authentication authentication) {
        return personalService.addTask(dto, authentication.getName());
    }

    @PutMapping("/update/task/{id}")
    public ResponseEntity<StringDto> updateTask(@RequestBody ChangePersonalTaskDto dto,
                                                @PathVariable UUID id,
                                                Authentication authentication) {
        return personalService.updateTask(dto, id, authentication.getName());
    }

    @DeleteMapping("/delete/task/{id}")
    public ResponseEntity<StringDto> deleteTask(@PathVariable UUID id,
                                             Authentication authentication) {
        return personalService.deleteTask(id, authentication.getName());
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<GetPersonalTaskDto>> taskCalendar(@RequestParam(required = false) String date,
                                                                 Authentication authentication) throws ParseException {
        return personalService.taskCalendar(date, authentication.getName());
    }

    @PostMapping("/new/goal")
    public ResponseEntity<StringDto> addGoal(@RequestBody CreateGoalDto dto,
                                                            Authentication authentication) {
        return personalService.addGoal(dto, authentication.getName());
    }

    @PutMapping("/update/goal/{id}")
    public ResponseEntity<StringDto> updateGoal(@RequestBody ChangeGoalDto dto,
                                                @PathVariable UUID id,
                                                Authentication authentication) {
        return personalService.updateGoal(dto, id, authentication.getName());
    }

    @DeleteMapping("/delete/goal/{id}")
    public ResponseEntity<StringDto> deleteGoal(@PathVariable UUID id,
                                             Authentication authentication) {
        return personalService.deleteGoal(id, authentication.getName());
    }
}
