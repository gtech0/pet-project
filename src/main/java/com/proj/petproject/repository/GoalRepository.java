package com.proj.petproject.repository;

import com.proj.petproject.entity.GoalEntity;
import com.proj.petproject.entity.PersonalTaskEntity;
import com.proj.petproject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GoalRepository extends JpaRepository<GoalEntity, UUID> {
    Optional<GoalEntity> findByIdAndTaskAndUser(UUID id, PersonalTaskEntity task, UserEntity user);
    Optional<GoalEntity> findByIdAndUser(UUID id, UserEntity user);
}
