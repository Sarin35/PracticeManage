package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.Unit;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Integer> {
    @Transactional
    @Query("update Unit u set u.status = 0 where u.id = :id")
    @Modifying
    void updateStatusToZeroById(@NotNull(message = "id不能为空") Integer id);
}