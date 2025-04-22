package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.dto.StudentInfoDto;
import com.practice.practicemanage.response.ResponseMessage;

import java.util.List;

public interface StudentInfoService {

    Object getTeacherPhoneByStudentPhone(String Phone);

    ResponseMessage<Object> getStudent(Integer page, Integer limit);

    ResponseMessage<Object> saveStudentInfo(StudentInfoDto studentDto);

    List<String> findByName(String studentName);
}
