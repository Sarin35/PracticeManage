package com.practice.practicemanage.pojo;

import jakarta.persistence.*;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

}