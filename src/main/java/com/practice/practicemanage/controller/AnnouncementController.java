package com.practice.practicemanage.controller;

import com.practice.practicemanage.pojo.dto.AnnouncementDto;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.AnnouncementServiceImpl;
import com.practice.practicemanage.utils.TypeConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping
@RestController
public class AnnouncementController {

    @Autowired
    private AnnouncementServiceImpl announcementService;
    @Autowired
    private TypeConversionUtil typeConversionUtil;

    @PostMapping("/savaAnnou")
    public ResponseMessage<Object> savaAnnou(@Validated @RequestBody AnnouncementDto announcementDto) {
        return announcementService.savaAnnou(announcementDto);
    }

    @PostMapping("/getRoleAnnouncementList")
    public ResponseMessage<Object> getRoleAnnouncementLists(@RequestBody Map<String, String> map) {
        return announcementService.getRoleAnnouncementList(map.get("phone"), typeConversionUtil.toInteger(map.get("page")), typeConversionUtil.toInteger(map.get("limit")));
    }

    @PostMapping("/findByIds")
    public ResponseMessage<Object> findByIds(@RequestBody Map<String, Integer> map) {
        return announcementService.findById(map.get("id"));
    }
}
