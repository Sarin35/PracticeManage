package com.practice.practicemanage.controller;

import com.practice.practicemanage.pojo.dto.SchoolDto;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.SchoolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class Schoolcontroller {

    @Autowired
    private SchoolServiceImpl schoolService;

    @PostMapping("/addSchool")
    public ResponseMessage<Object> addSchool(@Validated @RequestBody SchoolDto schoolDto) {
        return schoolService.addSchool(schoolDto);
    }
}
