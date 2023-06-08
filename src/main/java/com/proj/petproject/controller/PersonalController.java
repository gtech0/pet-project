package com.proj.petproject.controller;

import com.proj.petproject.dto.CreatePersonalTaskDto;
import com.proj.petproject.dto.GetPersonalTaskDto;
import com.proj.petproject.dto.StringDto;
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

}
