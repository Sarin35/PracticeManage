package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.InternshipGuidance;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface InternshipGuidanceRepository extends JpaRepository<InternshipGuidance, Integer> {

    Page<InternshipGuidance> findByUnitNameAndStatusNot(@Size(max = 200) String unitName, Pageable pageable, Integer status);
    Page<InternshipGuidance> findByUnitNameAndStatus(@Size(max = 200) String unitName, Pageable pageable, Integer status);

    @Modifying
    @Transactional
    @Query("UPDATE InternshipGuidance a SET a.status = :status WHERE a.id = :id")
    int updateStatusById(@Param("id") Integer id, @Param("status") Integer status);
}