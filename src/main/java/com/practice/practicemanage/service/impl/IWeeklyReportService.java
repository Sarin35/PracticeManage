package com.practice.practicemanage.service.impl;

import com.practice.practicemanage.pojo.dto.WeeklyReportDto;
import com.practice.practicemanage.response.ResponseMessage;

public interface IWeeklyReportService {
    public ResponseMessage<Object> getMyWeekReport(String studentPhone, String teacherPhone, int page, int limit, byte status);

    ResponseMessage<Object> updateStatus(Integer id, Integer status);

    ResponseMessage<Object> findByFilter(String studentPhone, String teacherPhone, Integer page, Integer limit, String title);

    ResponseMessage<Object> weekSava(WeeklyReportDto weeklyReportDto);

}
