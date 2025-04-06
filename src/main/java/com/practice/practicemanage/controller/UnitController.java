package com.practice.practicemanage.controller;

import com.practice.practicemanage.pojo.dto.UnitDto;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.UnitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UnitController {

    @Autowired
    private UnitServiceImpl unitService;

    @PostMapping("/addUnit")
    public ResponseMessage<Object> addUnit(@Validated @RequestBody UnitDto unitDto) {
        return unitService.addUnit(unitDto);
    }

}
