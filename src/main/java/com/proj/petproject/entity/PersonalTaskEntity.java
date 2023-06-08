package com.proj.petproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "task")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalTaskEntity {

    @Id
    @Column
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_time")
    private Date startTime;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_time")
    private Date endTime;

    @Column
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
