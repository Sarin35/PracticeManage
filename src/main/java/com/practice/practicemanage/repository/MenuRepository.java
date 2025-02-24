package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    /**
     * 根据父菜单 id 查询子菜单
     * @param parentid
     * @return
     */
    List<Menu> findByParentid(Integer parentid);

    /**
     * 查询所有父菜单
     * @return
     */
    List<Menu> findByParentidIsNull();
}