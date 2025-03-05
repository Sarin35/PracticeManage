package com.practice.practicemanage.service.impl;

import com.practice.practicemanage.response.ResponseMessage;

public interface ISchoolService {
    ResponseMessage<Object> getSchool();

    ResponseMessage<Object> getSchoolDelete(Integer id);
}
