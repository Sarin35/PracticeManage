package com.practice.practicemanage.service.impl;

import com.practice.practicemanage.response.ResponseMessage;

public interface IUnitService {
    ResponseMessage<Object> getUnit();

    ResponseMessage<Object> getUnitDelete(Integer id);
}
