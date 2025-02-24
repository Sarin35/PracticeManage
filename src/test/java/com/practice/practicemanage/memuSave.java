package com.practice.practicemanage;

import com.practice.practicemanage.pojo.Menu;
import com.practice.practicemanage.repository.MenuRepository;
import com.practice.practicemanage.utils.LogUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class memuSave {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private LogUtil logUtil;

    @Test
    public void getMenuList() {
        List<Menu> menu = menuRepository.findByParentidIsNull();
        buildMenuTree(menu);
        logUtil.info(this.getClass(), "menuList: " + menu);
    }

    public void buildMenuTree(List<Menu> menuList) {
        for (Menu menus : menuList) {
            List<Menu> childMenu = menuRepository.findByParentid(menus.getId());
            if (childMenu != null && !childMenu.isEmpty()) {
                menus.setChildren(childMenu);
                buildMenuTree(childMenu);
                logUtil.info(this.getClass(), "menuList2: " + menus);
            }
        }
    }

    @Test
    public void saveMenu() {
//        Meta meta = new Meta();
//        meta.setTitle("Guide");
//        meta.setIcon("guide");
//        meta.setAffix(null);
//        meta.setRoles(null);
//        Menu menu = new Menu();
//        menu.setId(null);
//        menu.setName("/guide/index");
//        menu.setRedirect();
//        menu.setComponent();
//        menu.setPath();
//        menu.setMeta(meta);
//        menu.setParentid();

    }

}
