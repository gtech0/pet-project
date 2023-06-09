package com.proj.petproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePersonalTaskDto {

    @NotBlank
    private String name;

    private String description;

    private Date startTime;

    private Date endTime;

    private boolean completed;

}
