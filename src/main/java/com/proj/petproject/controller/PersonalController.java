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

    @PostMapping("/task/new")
    public ResponseEntity<StringDto> addTask(@RequestBody CreatePersonalTaskDto dto,
                                             Authentication authentication) {
        return personalService.addTask(dto, authentication.getName());
    }

    @PostMapping("/task/complete/{id}")
    public ResponseEntity<StringDto> completeTask(@PathVariable UUID id,
                                                  Authentication authentication) {
        return personalService.completeTask(id, authentication.getName());
    }

    @PutMapping("/task/update/{id}")
    public ResponseEntity<StringDto> updateTask(@RequestBody ChangePersonalTaskDto dto,
                                                @PathVariable UUID id,
                                                Authentication authentication) {
        return personalService.updateTask(dto, id, authentication.getName());
    }

    @DeleteMapping("/task/delete/{id}")
    public ResponseEntity<StringDto> deleteTask(@PathVariable UUID id,
                                                Authentication authentication) {
        return personalService.deleteTask(id, authentication.getName());
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<GetPersonalTaskDto>> taskCalendar(@RequestParam(required = false) String date,
                                                                 Authentication authentication) throws ParseException {
        return personalService.taskCalendar(date, authentication.getName());
    }

    @PostMapping("/goal/new")
    public ResponseEntity<StringDto> addGoal(@RequestBody CreateGoalDto dto,
                                             Authentication authentication) {
        return personalService.addGoal(dto, authentication.getName());
    }

    @PostMapping("/goal/complete/{id}")
    public ResponseEntity<StringDto> completeGoal(@PathVariable UUID id,
                                                  Authentication authentication) {
        return personalService.completeGoal(id, authentication.getName());
    }

    @GetMapping("/goals")
    public ResponseEntity<List<GetGoalsDto>> getCurrentGoals(Authentication authentication) {
        return personalService.getCurrentGoals(authentication.getName());
    }

    @GetMapping("/goals/completed")
    public ResponseEntity<List<GetGoalsDto>> getCompletedGoals(Authentication authentication) {
        return personalService.getCompletedGoals(authentication.getName());
    }

    @PutMapping("/goal/update/{id}")
    public ResponseEntity<StringDto> updateGoal(@RequestBody ChangeGoalDto dto,
                                                @PathVariable UUID id,
                                                Authentication authentication) {
        return personalService.updateGoal(dto, id, authentication.getName());
    }

    @DeleteMapping("/goal/delete/{id}")
    public ResponseEntity<StringDto> deleteGoal(@PathVariable UUID id,
                                                Authentication authentication) {
        return personalService.deleteGoal(id, authentication.getName());
    }
}
