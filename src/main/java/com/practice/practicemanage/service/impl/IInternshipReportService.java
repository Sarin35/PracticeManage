package com.practice.practicemanage.service.impl;

import com.practice.practicemanage.pojo.dto.InternshipReportDto;
import com.practice.practicemanage.pojo.dto.InternshipReportIdDto;
import com.practice.practicemanage.response.ResponseMessage;

public interface IInternshipReportService {
    ResponseMessage<Object> saveReport(InternshipReportDto internshipReportDto);

    ResponseMessage<Object> findListByStudentPhone(String phone, String role, Integer page, Integer limit);

    ResponseMessage<Object> getListById(Integer id);

    ResponseMessage<Object> saveReportById(InternshipReportIdDto internshipReportDto);
}
