package com.proj.petproject.service;

import com.proj.petproject.dto.CreatePersonalTaskDto;
import com.proj.petproject.dto.GetPersonalTaskDto;
import com.proj.petproject.dto.StringDto;
import com.proj.petproject.entity.PersonalTaskEntity;
import com.proj.petproject.entity.UserEntity;
import com.proj.petproject.exception.UserNotFoundException;
import com.proj.petproject.repository.PersonalTaskRepository;
import com.proj.petproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonalService {

    private final PersonalTaskRepository personalTaskRepository;
    private final UserRepository userRepository;

    public ResponseEntity<StringDto> addTask(CreatePersonalTaskDto dto, String login) {
        Optional<UserEntity> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            log.info("User doesn't exist");
            throw new UserNotFoundException("User doesn't exist");
        }

        PersonalTaskEntity task = PersonalTaskEntity.builder()
                .id(UUID.randomUUID())
                .name(dto.getName())
                .description(dto.getDescription())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .completed(false)
                .user(user.get())
                .build();
        personalTaskRepository.save(task);

        return ResponseEntity.ok(new StringDto("Task created"));
    }

    @Transactional
    public ResponseEntity<StringDto> deleteTask(UUID id, String login) {
        Optional<UserEntity> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            log.info("User doesn't exist");
            throw new UserNotFoundException("User doesn't exist");
        }

        if (personalTaskRepository.findByIdAndUser(id, user.get())) {
            log.info("Task doesn't exist");
            throw new UserNotFoundException("Task doesn't exist");
        }
        personalTaskRepository.deleteById(id);

        return ResponseEntity.ok(new StringDto("Task deleted"));
    }

    public ResponseEntity<List<GetPersonalTaskDto>> taskCalendar(String date, String login) throws ParseException {
        Optional<UserEntity> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            log.info("User doesn't exist");
            throw new UserNotFoundException("User doesn't exist");
        }

        List<GetPersonalTaskDto> tasks;
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            tasks = personalTaskRepository.getAllByStartTimeOrEndTimeQuery(formatter.parse(date), user.get());
        } else {
            tasks = personalTaskRepository.getAllByStartTimeOrEndTimeQuery(null, user.get());
        }

        return ResponseEntity.ok(tasks);
    }

}
