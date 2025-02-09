package com.practice.practicemanage.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "unit_users")
public class UnitUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "unit_name", length = 200)
    private String unitName;

    @Column(name = "status", nullable = false)
    private Byte status;

}