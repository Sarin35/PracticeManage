package com.practice.practicemanage.service.impl;

import com.practice.practicemanage.pojo.Assignment;
import com.practice.practicemanage.pojo.dto.AssignmentDto;
import com.practice.practicemanage.response.ResponseMessage;

import java.util.List;

public interface IAssignmentService {

    List<Assignment> findAssByPhone(String teacher,String phone, String studentPhone, byte status);

    ResponseMessage<Object> savaAssignment(AssignmentDto assignmentDto);

    ResponseMessage<Object> deleteAssignmentById(Integer id);
}
