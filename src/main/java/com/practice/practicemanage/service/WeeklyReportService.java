package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.dto.WeeklyReportDto;
import com.practice.practicemanage.response.ResponseMessage;

import java.util.List;

public interface WeeklyReportService {
    ResponseMessage<Object> getMyWeekReport(String studentPhone, String teacherPhone, int page, int limit, byte status);

    ResponseMessage<Object> updateStatus(Integer id, Integer status);

    ResponseMessage<Object> findByFilter(String studentPhone, String teacherPhone, Integer page, Integer limit, String title, byte status);

    ResponseMessage<Object> weekSava(WeeklyReportDto weeklyReportDto);

    ResponseMessage<Object> getMyWeekReports(String teacherPhone, int page, int limit, byte status);
    ResponseMessage<Object> findByFilters(String teacherPhone, Integer page, Integer limit, String title, byte status);

    ResponseMessage<Object> findByStudentName(String teacherPhone, Integer page, Integer limit, List<String> studentInfos, byte status);

    ResponseMessage<Object> findByTitleAndName(String teacherPhone, Integer page, Integer limit, String title, List<String> studentInfos, byte status);
}
