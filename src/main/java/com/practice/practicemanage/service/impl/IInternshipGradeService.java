package com.practice.practicemanage.service.impl;

import com.practice.practicemanage.pojo.dto.InternshipGradeDto;
import com.practice.practicemanage.response.ResponseMessage;

public interface IInternshipGradeService {
    ResponseMessage<Object> getAllInfo(String phone);

    ResponseMessage<Object> getTeacherinfo(String phone);

    ResponseMessage<Object> savaGrade(InternshipGradeDto internshipGradeDto);
}
