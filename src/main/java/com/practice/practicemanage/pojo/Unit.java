package com.practice.practicemanage.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "units")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "unit_name", length = 200)
    private String unitName;

    @Column(name = "status", nullable = false)
    private Byte status;

}