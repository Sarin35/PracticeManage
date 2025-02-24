package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.Menu;
import com.practice.practicemanage.repository.MenuRepository;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.IMenuService;
import com.practice.practicemanage.utils.LogUtil;
import com.practice.practicemanage.utils.RedisUtil;
import com.practice.practicemanage.utils.TypeConversionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MenuService implements IMenuService {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private LogUtil logUtil;
    @Autowired
    private TypeConversionUtil typeConversionUtil;

    @Override
    public List<Menu> getMenuList() {
        try {
            if (redisUtil.exists("menu")) {
                return typeConversionUtil.convertObjectToList(redisUtil.get("menu"), Menu.class);
            } else {
                List<Menu> menu = menuRepository.findByParentidIsNull();
                buildMenuTree(menu);
                redisUtil.set("menu", menu, 7, TimeUnit.DAYS);

                return menu;
            }
        } catch (Exception e) {
            logUtil.error(MenuService.class, "获取菜单失败", e);
            return null;
        }
    }

    public void buildMenuTree(List<Menu> menuList) {
        for (Menu menu: menuList) {
            List<Menu> childMenu = menuRepository.findByParentid(menu.getId());
            if (childMenu != null && !childMenu.isEmpty()) {
                menu.setChildren(childMenu);
                buildMenuTree(childMenu);
            }
        }
    }
}
