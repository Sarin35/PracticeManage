package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.School;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {
    @Transactional
    @Query("update School u set u.status = 0 where u.id = :id")
    @Modifying
    void updateStatusToZeroById(@NotNull(message = "id不能为空") Integer id);

    List<School> findByStatus(Byte status);
}