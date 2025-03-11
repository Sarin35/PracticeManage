package com.practice.practicemanage.service.impl;

import com.practice.practicemanage.pojo.dto.SchoolDto;
import com.practice.practicemanage.response.ResponseMessage;

public interface ISchoolService {
    ResponseMessage<Object> getSchool();

    ResponseMessage<Object> getSchoolDelete(Integer id);

    ResponseMessage<Object> addSchool(SchoolDto schoolDto);
}
