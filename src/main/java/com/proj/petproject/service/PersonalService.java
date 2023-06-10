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

    private PersonalTaskEntity getPersonalTask(UUID id, UserEntity user) {
        PersonalTaskEntity task = null;
        if (id != null) {
            Optional<PersonalTaskEntity> optionalTask = personalTaskRepository
                    .findByIdAndUser(id, user);

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
        UserEntity user = userRepository
                .findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Task doesn't exist"));

        PersonalTaskEntity task = PersonalTaskEntity.builder()
                .id(UUID.randomUUID())
                .name(dto.getName())
                .description(dto.getDescription())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .completed(false)
                .user(user)
                .build();
        personalTaskRepository.save(task);

        return ResponseEntity.ok(new StringDto("Task created"));
    }

    @Transactional
    public ResponseEntity<StringDto> completeTask(UUID id, String login) {
        UserEntity user = userRepository
                .findByLogin(login)
                .orElseThrow(() -> new NotFoundException("User doesn't exist"));

        PersonalTaskEntity task = personalTaskRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() -> new NotFoundException("Task doesn't exist"));

        task.setCompleted(true);
        return ResponseEntity.ok(new StringDto("Task completed"));
    }

    @Transactional
    public ResponseEntity<StringDto> updateTask(ChangePersonalTaskDto dto, UUID id, String login) {
        UserEntity user = userRepository
                .findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Task doesn't exist"));

        Optional<PersonalTaskEntity> task = personalTaskRepository.findByIdAndUser(id, user);
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
        UserEntity user = userRepository
                .findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Task doesn't exist"));

        if (personalTaskRepository.findByIdAndUser(id, user).isEmpty()) {
            log.error("Task doesn't exist");
            throw new NotFoundException("Task doesn't exist");
        }
        personalTaskRepository.deleteById(id);

        return ResponseEntity.ok(new StringDto("Task deleted"));
    }

    public ResponseEntity<List<GetPersonalTaskDto>> taskCalendar(String date, String login) throws ParseException {
        UserEntity user = userRepository
                .findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Task doesn't exist"));

        List<GetPersonalTaskDto> tasks;
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            tasks = personalTaskRepository.getAllByStartTimeOrEndTimeQuery(formatter.parse(date), user);
        } else {
            tasks = personalTaskRepository.getAllByStartTimeOrEndTimeQuery(null, user);
        }

        return ResponseEntity.ok(tasks);
    }

    @Transactional
    public ResponseEntity<StringDto> addGoal(CreateGoalDto dto, String login) {
        UserEntity user = userRepository
                .findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Task doesn't exist"));

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
                .user(user)
                .build();
        goalRepository.save(goal);

        return ResponseEntity.ok(new StringDto("Goal created"));
    }

    @Transactional
    public ResponseEntity<StringDto> completeGoal(UUID id, String login) {
        UserEntity user = userRepository
                .findByLogin(login)
                .orElseThrow(() -> new NotFoundException("User doesn't exist"));

        GoalEntity goal = goalRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() -> new NotFoundException("Goal doesn't exist"));

        goal.setCompleted(true);
        return ResponseEntity.ok(new StringDto("Goal completed"));
    }

    public ResponseEntity<List<GetGoalsDto>> getCurrentGoals(String login) {
        UserEntity user = userRepository
                .findByLogin(login)
                .orElseThrow(() -> new NotFoundException("User doesn't exist"));

        List<GetGoalsDto> goals = goalRepository.findAllOrderByCreationTimeQuery(user.getId());

        return ResponseEntity.ok(goals);
    }

    public ResponseEntity<List<GetGoalsDto>> getCompletedGoals(String login) {
        UserEntity user = userRepository
                .findByLogin(login)
                .orElseThrow(() -> new NotFoundException("User doesn't exist"));

        List<GetGoalsDto> goals = goalRepository.findAllCompletedOrderByCreationTimeQuery(user.getId());

        return ResponseEntity.ok(goals);
    }

    @Transactional
    public ResponseEntity<StringDto> updateGoal(ChangeGoalDto dto, UUID id, String login) {
        UserEntity user = userRepository
                .findByLogin(login)
                .orElseThrow(() -> new NotFoundException("User doesn't exist"));

        PersonalTaskEntity task = getPersonalTask(dto.getTask(), user);

        GoalEntity goal = goalRepository
                .findByIdAndTaskAndUser(id, task, user)
                .orElseThrow(() -> new NotFoundException("Goal doesn't exist"));

        goal.setName(dto.getName());
        goal.setDescription(dto.getDescription());
        goal.setExpectedTime(dto.getExpectedTime());
        goal.setCompleted(dto.isCompleted());
        goal.setTask(task);
        goalRepository.save(goal);

        return ResponseEntity.ok(new StringDto("Goal updated"));
    }

    @Transactional
    public ResponseEntity<StringDto> deleteGoal(UUID id, String login) {
        UserEntity user = userRepository
                .findByLogin(login)
                .orElseThrow(() -> new NotFoundException("User doesn't exist"));

        goalRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() -> new NotFoundException("Goal doesn't exist"));

        goalRepository.deleteById(id);

        return ResponseEntity.ok(new StringDto("Goal deleted"));
    }
}
