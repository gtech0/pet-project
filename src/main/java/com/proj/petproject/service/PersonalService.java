package com.proj.petproject.service;

import com.proj.petproject.dto.*;
import com.proj.petproject.entity.GoalEntity;
import com.proj.petproject.entity.PersonalTaskEntity;
import com.proj.petproject.entity.UserEntity;
import com.proj.petproject.exception.NotFoundException;
import com.proj.petproject.repository.GoalRepository;
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
    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    private Optional<UserEntity> getUserEntity(String login) {
        Optional<UserEntity> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            log.error("User doesn't exist");
            throw new NotFoundException("User doesn't exist");
        }
        return user;
    }

    private PersonalTaskEntity getPersonalTask(UUID dto, Optional<UserEntity> user) {
        PersonalTaskEntity task = null;
        if (dto != null) {
            Optional<PersonalTaskEntity> optionalTask = personalTaskRepository
                    .findByIdAndUser(dto, user.get());

            if (optionalTask.isEmpty()) {
                log.error("Task doesn't exist");
                throw new NotFoundException("Task doesn't exist");
            }

            task = optionalTask.get();
        }
        return task;
    }

    @Transactional
    public ResponseEntity<StringDto> addTask(CreatePersonalTaskDto dto, String login) {
        Optional<UserEntity> user = getUserEntity(login);

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
    public ResponseEntity<StringDto> updateTask(ChangePersonalTaskDto dto, UUID id, String login) {
        Optional<UserEntity> user = getUserEntity(login);

        Optional<PersonalTaskEntity> task = personalTaskRepository.findByIdAndUser(id, user.get());
        if (task.isEmpty()) {
            log.error("Task doesn't exist");
            throw new NotFoundException("Task doesn't exist");
        }

        task.get().setName(dto.getName());
        task.get().setDescription(dto.getDescription());
        task.get().setStartTime(dto.getStartTime());
        task.get().setEndTime(dto.getEndTime());
        task.get().setCompleted(dto.isCompleted());
        personalTaskRepository.save(task.get());

        return ResponseEntity.ok(new StringDto("Task updated"));
    }

    @Transactional
    public ResponseEntity<StringDto> deleteTask(UUID id, String login) {
        Optional<UserEntity> user = getUserEntity(login);

        if (personalTaskRepository.findByIdAndUser(id, user.get()).isEmpty()) {
            log.error("Task doesn't exist");
            throw new NotFoundException("Task doesn't exist");
        }
        personalTaskRepository.deleteById(id);

        return ResponseEntity.ok(new StringDto("Task deleted"));
    }

    public ResponseEntity<List<GetPersonalTaskDto>> taskCalendar(String date, String login) throws ParseException {
        Optional<UserEntity> user = getUserEntity(login);

        List<GetPersonalTaskDto> tasks;
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            tasks = personalTaskRepository.getAllByStartTimeOrEndTimeQuery(formatter.parse(date), user.get());
        } else {
            tasks = personalTaskRepository.getAllByStartTimeOrEndTimeQuery(null, user.get());
        }

        return ResponseEntity.ok(tasks);
    }

    @Transactional
    public ResponseEntity<StringDto> addGoal(CreateGoalDto dto, String login) {
        Optional<UserEntity> user = getUserEntity(login);

        PersonalTaskEntity task = getPersonalTask(dto.getTask(), user);

        GoalEntity goal = GoalEntity.builder()
                .id(UUID.randomUUID())
                .name(dto.getName())
                .description(dto.getDescription())
                .expectedTime(dto.getExpectedTime())
                .completed(false)
                .priority(dto.getPriority())
                .creationTime(new Date())
                .task(task)
                .user(user.get())
                .build();
        goalRepository.save(goal);

        return ResponseEntity.ok(new StringDto("Goal created"));
    }

    @Transactional
    public ResponseEntity<StringDto> updateGoal(ChangeGoalDto dto, UUID id, String login) {
        Optional<UserEntity> user = getUserEntity(login);

        PersonalTaskEntity task = getPersonalTask(dto.getTask(), user);

        Optional<GoalEntity> goal = goalRepository.findByIdAndTaskAndUser(id, task, user.get());
        if (goal.isEmpty()) {
            log.error("Goal doesn't exist");
            throw new NotFoundException("Goal doesn't exist");
        }

        goal.get().setName(dto.getName());
        goal.get().setDescription(dto.getDescription());
        goal.get().setExpectedTime(dto.getExpectedTime());
        goal.get().setCompleted(dto.isCompleted());
        goal.get().setTask(task);
        goalRepository.save(goal.get());

        return ResponseEntity.ok(new StringDto("Goal updated"));
    }

    @Transactional
    public ResponseEntity<StringDto> deleteGoal(UUID id, String login) {
        Optional<UserEntity> user = getUserEntity(login);

        if (goalRepository.findByIdAndUser(id, user.get()).isEmpty()) {
            log.error("Goal doesn't exist");
            throw new NotFoundException("Goal doesn't exist");
        }
        goalRepository.deleteById(id);

        return ResponseEntity.ok(new StringDto("Goal deleted"));
    }
}
