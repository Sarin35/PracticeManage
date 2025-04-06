package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.dto.TeacherInfoDto;
import com.practice.practicemanage.response.ResponseMessage;

public interface TeacherInfoService {
    Object getTeacherListByPhone(String phone);

    ResponseMessage<Object> getTeacher();

    ResponseMessage<Object> saveTeacherInfo(TeacherInfoDto teacherDto);
}
