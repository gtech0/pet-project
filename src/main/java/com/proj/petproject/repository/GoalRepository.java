package com.proj.petproject.repository;

import com.proj.petproject.dto.GetGoalsDto;
import com.proj.petproject.entity.GoalEntity;
import com.proj.petproject.entity.PersonalTaskEntity;
import com.proj.petproject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface GoalRepository extends JpaRepository<GoalEntity, UUID> {
    Optional<GoalEntity> findByIdAndTaskAndUser(UUID id, PersonalTaskEntity task, UserEntity user);
    Optional<GoalEntity> findByIdAndUser(UUID id, UserEntity user);
    @Query("SELECT new com.proj.petproject.dto.GetGoalsDto(" +
            "g.id, g.name, g.description, g.expectedTime, g.priority, g.completed, g.task.id) " +
            "FROM GoalEntity g " +
            "WHERE g.user.id = :userId " +
            "AND g.completed = false " +
            "ORDER BY g.creationTime DESC")
    List<GetGoalsDto> findAllOrderByCreationTimeQuery(UUID userId);
    @Query("SELECT new com.proj.petproject.dto.GetGoalsDto(" +
            "g.id, g.name, g.description, g.expectedTime, g.priority, g.completed, g.task.id) " +
            "FROM GoalEntity g " +
            "WHERE g.user.id = :userId " +
            "AND g.completed = true " +
            "ORDER BY g.creationTime DESC")
    List<GetGoalsDto> findAllCompletedOrderByCreationTimeQuery(UUID userId);
}
