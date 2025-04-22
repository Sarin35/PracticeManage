package com.practice.practicemanage.controller;


import com.practice.practicemanage.pojo.dto.WeeklyReportDto;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.StudentInfoServiceImpl;
import com.practice.practicemanage.service.impl.WeeklyReportServiceImpl;
import com.practice.practicemanage.utils.LogUtil;
import com.practice.practicemanage.utils.TypeConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping
public class WeekReportController {

    @Autowired
    private WeeklyReportServiceImpl weeklyReportService;
    @Autowired
    private TypeConversionUtil typeConversionUtil;
    @Autowired
    private StudentInfoServiceImpl studentInfoService;
    @Autowired
    private LogUtil logUtil;

    @PostMapping("/getWeekLists")
    public ResponseMessage<Object> getMyWeekReport(@RequestBody Map<String, String> phone) {
        return weeklyReportService.getMyWeekReport(phone.get("studentPhone"),
                                                   phone.get("teacherPhone"),
                                                   typeConversionUtil.toInteger(phone.get("page")),  // 当前页数
                                                   typeConversionUtil.toInteger(phone.get("limit")),// 每页数据条数
                                                   typeConversionUtil.toByte(phone.get("status")));
    }

    @PostMapping("/getWeekListsTeacher") // 老师查找学生周志
    public ResponseMessage<Object> getMyWeekReports(@RequestBody Map<String, String> phone) {
        return weeklyReportService.getMyWeekReports(phone.get("teacherPhone"),
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
        byte status = Objects.equals(titles.get("status"), "0") ? (byte)0 : Objects.equals(titles.get("status"), "1" ) ? (byte)1 : (byte)2;
        return weeklyReportService.findByFilter(titles.get("studentPhone"),
                                                titles.get("teacherPhone"),
                                                typeConversionUtil.toInteger(titles.get("page")),  // 当前页数
                                                typeConversionUtil.toInteger(titles.get("limit")),// 每页数据条数
                                                titles.get("title"),
                                                status);
    }

    @PostMapping("/selectFilterSp") // 老师执行查询
    public ResponseMessage<Object> selectFilterSp(@Validated @RequestBody Map<String, String> titles) {
        try {
            byte status = Objects.equals(titles.get("status"), "0") ? (byte)0 : Objects.equals(titles.get("status"), "1" ) ? (byte)1 : (byte)2;
            return weeklyReportService.findByFilters(titles.get("teacherPhone"),
                    typeConversionUtil.toInteger(titles.get("page")),  // 当前页数
                    typeConversionUtil.toInteger(titles.get("limit")),// 每页数据条数
                    titles.get("title"),
                    status);
        } catch (Exception e) {
            logUtil.error(WeeklyReportDto.class, "查询失败", e);
            return ResponseMessage.error("查询失败");
        }
    }

    @PostMapping("/selectNames")
    public ResponseMessage<Object> selectNames(@Validated @RequestBody Map<String, String> names) {
        try {
            List<String> studentInfos = studentInfoService.findByName(names.get("studentName"));
            if (studentInfos.isEmpty()) {
                return ResponseMessage.error("无该学生信息");
            }
            byte status = Objects.equals(names.get("status"), "0") ? (byte)0 : Objects.equals(names.get("status"), "1" ) ? (byte)1 : (byte)2;
            return weeklyReportService.findByStudentName(names.get("teacherPhone"),
                    typeConversionUtil.toInteger(names.get("page")),  // 当前页数
                    typeConversionUtil.toInteger(names.get("limit")),// 每页数据条数
                    studentInfos,
                    status);
        } catch (Exception e) {
            logUtil.error(WeeklyReportDto.class, "查询失败", e);
            return ResponseMessage.error("查询失败");
        }
    }

    @PostMapping("/selectTitleAndName")
    public ResponseMessage<Object> selectTitleAndName(@Validated @RequestBody Map<String, String> selects) {
        try {
            List<String> studentInfos = studentInfoService.findByName(selects.get("studentName"));
            if (studentInfos.isEmpty()) {
                return ResponseMessage.error("无该学生信息");
            }
            byte status = Objects.equals(selects.get("status"), "0") ? (byte)0 : Objects.equals(selects.get("status"), "1" ) ? (byte)1 : (byte)2;
            return weeklyReportService.findByTitleAndName(selects.get("teacherPhone"),
                    typeConversionUtil.toInteger(selects.get("page")),  // 当前页数
                    typeConversionUtil.toInteger(selects.get("limit")),// 每页数据条数
                    selects.get("title"), // 标题
                    studentInfos, // 手机号（学生）列表
                    status); // 选择的状态
        } catch (Exception e) {
            logUtil.error(WeeklyReportDto.class, "查询失败", e);
            return ResponseMessage.error("查询失败");
        }
    }

    @PostMapping("/createWeeks")
    public ResponseMessage<Object> createWeek(@Validated @RequestBody WeeklyReportDto weeklyReportDto) {
        return weeklyReportService.weekSava(weeklyReportDto);
    }
}
