package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.UnitUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitUserRepository extends JpaRepository<UnitUser, Integer> {
    List<UnitUser> findByName(String name);

    UnitUser findByPhone(String phone);

    long countByName(String name);

    List<UnitUser> findByStatus(Byte status);
}