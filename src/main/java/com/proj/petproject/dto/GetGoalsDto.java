package com.proj.petproject.dto;

import com.proj.petproject.entity.PriorityEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetGoalsDto {

    private UUID id;

    private String name;

    private String description;

    @Temporal(TemporalType.TIME)
    private Date expectedTime;

    @Enumerated(EnumType.STRING)
    private PriorityEnum priority;

    private boolean completed;

    private UUID task;

}
