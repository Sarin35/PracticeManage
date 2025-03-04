package com.practice.practicemanage.service.impl;

import com.practice.practicemanage.pojo.dto.InternshipGuidanceDto;
import com.practice.practicemanage.pojo.dto.InternshipGuidanceIdDto;
import com.practice.practicemanage.response.ResponseMessage;

public interface IInternshipGuidanceService {
    ResponseMessage<Object> getGuidanceList(String phone);

    ResponseMessage<Object> findTeacherByStudent(String phone);

    ResponseMessage<Object> putList(InternshipGuidanceDto internshipGuidanceDto);

    ResponseMessage<Object> getGuidanceLists(String phone, Integer page, Integer limit, Integer status);

    ResponseMessage<Object> updateStatusPut(Integer id, Integer status);

    ResponseMessage<Object> savaCreatequidances(InternshipGuidanceIdDto internshipGuidanceDto);
}
