package com.practice.practicemanage.service.impl;

import com.practice.practicemanage.response.ResponseMessage;

public interface IStudentInfoService {

    Object getTeacherPhoneByStudentPhone(String Phone);

    ResponseMessage<Object> getStudent(Integer page, Integer limit);
}
