package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.dto.InternshipGradeDto;
import com.practice.practicemanage.pojo.dto.StudentInfoDto;
import com.practice.practicemanage.response.ResponseMessage;

public interface InternshipGradeService {
    ResponseMessage<Object> getAllInfo(String phone);

    ResponseMessage<Object> getTeacherinfo(String phone);

    ResponseMessage<Object> savaGrade(InternshipGradeDto internshipGradeDto);

    ResponseMessage<Object> getUnitInfo(String phone);

    void savaStudent(StudentInfoDto studentDto);
}
