package com.practice.practicemanage.controller;


import com.practice.practicemanage.pojo.dto.WeeklyReportDto;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.WeeklyReportService;
import com.practice.practicemanage.utils.TypeConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class WeekReportController {

    @Autowired
    private WeeklyReportService weeklyReportService;
    @Autowired
    private TypeConversionUtil typeConversionUtil;

    @PostMapping("/getWeekLists")
    public ResponseMessage<Object> getMyWeekReport(@RequestBody Map<String, String> phone) {
        return weeklyReportService.getMyWeekReport(phone.get("studentPhone"),
                                                   phone.get("teacherPhone"),
                                                   typeConversionUtil.toInteger(phone.get("page")),  // 当前页数
                                                   typeConversionUtil.toInteger(phone.get("limit")),// 每页数据条数
                                                   typeConversionUtil.toByte(phone.get("status")));
    }

    @PostMapping("/updataStatus")
    public ResponseMessage<Object> updateStatusSp(@Validated @RequestBody Map<String, Integer> idAndStatus) {
        System.out.println("获取的参数："+idAndStatus);
        return weeklyReportService.updateStatus(idAndStatus.get("id"), idAndStatus.get("status"));
    }

    @PostMapping("/selectFilters")
    public ResponseMessage<Object> selectFiltersPs(@Validated @RequestBody Map<String, String> titles) {
        return weeklyReportService.findByFilter(titles.get("studentPhone"),
                                                titles.get("teacherPhone"),
                                                typeConversionUtil.toInteger(titles.get("page")),  // 当前页数
                                                typeConversionUtil.toInteger(titles.get("limit")),// 每页数据条数
                                                titles.get("title"));
    }

    @PostMapping("/createWeeks")
    public ResponseMessage<Object> createWeek(@Validated @RequestBody WeeklyReportDto weeklyReportDto) {
        return weeklyReportService.weekSava(weeklyReportDto);
    }
}
