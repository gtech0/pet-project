package com.proj.petproject.repository;

import com.proj.petproject.dto.GetPersonalTaskDto;
import com.proj.petproject.entity.PersonalTaskEntity;
import com.proj.petproject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonalTaskRepository extends JpaRepository<PersonalTaskEntity, UUID> {
    @Query("SELECT new com.proj.petproject.dto.GetPersonalTaskDto(" +
            "p.id, p.name, p.description, p.startTime, p.endTime, p.completed) " +
            "FROM PersonalTaskEntity p " +
            "WHERE p.user = :user " +
            "AND (p.startTime = :time " +
            "OR p.endTime = :time " +
            "OR cast(:time as date) IS NULL) " +
            "ORDER BY p.startTime")
    List<GetPersonalTaskDto> getAllByStartTimeOrEndTimeQuery(Date time, UserEntity user);
    Optional<PersonalTaskEntity> findByIdAndUser(UUID id, UserEntity user);
}
