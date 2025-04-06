package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.dto.UnitUserDto;
import com.practice.practicemanage.response.ResponseMessage;

public interface UnitUserService {
    ResponseMessage<Object> saveUnitInfo(UnitUserDto unitDto);

    ResponseMessage<Object> getUnitTeacher();
}
