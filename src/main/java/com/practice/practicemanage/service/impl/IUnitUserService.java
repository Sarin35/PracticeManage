package com.practice.practicemanage.service.impl;

import com.practice.practicemanage.pojo.dto.UnitUserDto;
import com.practice.practicemanage.response.ResponseMessage;

public interface IUnitUserService {
    ResponseMessage<Object> saveUnitInfo(UnitUserDto unitDto);
}
