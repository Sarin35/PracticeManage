package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.dto.InternshipGuidanceDto;
import com.practice.practicemanage.pojo.dto.InternshipGuidanceIdDto;
import com.practice.practicemanage.response.ResponseMessage;

public interface InternshipGuidanceService {
    ResponseMessage<Object> getGuidanceList(String phone);

    ResponseMessage<Object> findTeacherByStudent(String phone);

    ResponseMessage<Object> putList(InternshipGuidanceDto internshipGuidanceDto);

    ResponseMessage<Object> getGuidanceLists(String phone, Integer page, Integer limit, Integer status);

    ResponseMessage<Object> updateStatusPut(Integer id, Integer status);

    ResponseMessage<Object> savaCreatequidances(InternshipGuidanceIdDto internshipGuidanceDto);

    ResponseMessage<Object> selectFilterSpUnit(String titles, Integer page, Integer limit, String phone);
}
