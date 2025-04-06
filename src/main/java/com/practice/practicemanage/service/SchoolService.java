package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.dto.SchoolDto;
import com.practice.practicemanage.response.ResponseMessage;

public interface SchoolService {
    ResponseMessage<Object> getSchool();

    ResponseMessage<Object> getSchoolDelete(Integer id);

    ResponseMessage<Object> addSchool(SchoolDto schoolDto);
}
