package com.proj.petproject.dto;

import com.proj.petproject.entity.PriorityEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGoalDto {

    private String name;

    private String description;

    @Temporal(TemporalType.TIME)
    private Date expectedTime;

    @Enumerated(EnumType.STRING)
    private PriorityEnum priority;

    @NotBlank
    private UUID task;

}
