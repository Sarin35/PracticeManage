package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.UnitUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitUserRepository extends JpaRepository<UnitUser, Integer> {
}