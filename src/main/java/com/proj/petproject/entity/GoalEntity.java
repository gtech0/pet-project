package com.proj.petproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "goal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoalEntity {

    @Id
    @Column
    private UUID id;

    @Column
    private String name;

    @Column
    private String description;

    @Temporal(TemporalType.TIME)
    @Column(name = "expected_time")
    private Date expectedTime;

    @Column
    private boolean completed;

    @Enumerated(EnumType.STRING)
    @Column
    private PriorityEnum priority;

    @Column(name = "creation_time")
    private Date creationTime;

}
