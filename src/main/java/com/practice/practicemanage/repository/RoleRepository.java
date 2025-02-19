package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByUserid(Integer userid);
}