package com.proj.petproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPersonalTaskDto {

    private UUID id;

    private String name;

    private String description;

    private Date startTime;

    private Date endTime;

    private boolean completed;

}
