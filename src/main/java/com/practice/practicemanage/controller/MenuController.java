package com.practice.practicemanage.controller;

import com.practice.practicemanage.repository.MenuRepository;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MenuController {

    @Autowired
    private MenuService menuService;


    @PostMapping("/menu")
    public ResponseMessage<Object> getMenu(){
        return ResponseMessage.success(menuService.getMenuList());
    }
}
