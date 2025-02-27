package com.practice.practicemanage.service.impl;

import com.practice.practicemanage.response.ResponseMessage;

public interface IInternshipGradeService {
    ResponseMessage<Object> getAllInfo(String phone);
}
