package com.practice.practicemanage.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "schools")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "school_name", length = 200)
    private String schoolName;

    @Column(name = "status", nullable = false)
    private Byte status;

}