package com.practice.practicemanage.controller;

import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MenuController {

    @Autowired
    private MenuServiceImpl menuService;


    @PostMapping("/menu")
    public ResponseMessage<Object> getMenu(){
        return ResponseMessage.success(menuService.getMenuList());
    }
}
