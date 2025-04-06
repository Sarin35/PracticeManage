package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.dto.UnitDto;
import com.practice.practicemanage.response.ResponseMessage;

public interface UnitService {
    ResponseMessage<Object> getUnit();

    ResponseMessage<Object> getUnitDelete(Integer id);

    ResponseMessage<Object> addUnit(UnitDto unitDto);

    ResponseMessage<Object> getUnitList();
}
