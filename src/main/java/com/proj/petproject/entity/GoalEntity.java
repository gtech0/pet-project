package com.proj.petproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "expected_time")
    private Integer expectedTime;

    @Column
    private boolean completed;

    @Column(name = "creation_time")
    private boolean creationTime;

}
