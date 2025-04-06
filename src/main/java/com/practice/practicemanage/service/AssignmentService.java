package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.Assignment;
import com.practice.practicemanage.pojo.dto.AssignmentIdDto;
import com.practice.practicemanage.response.ResponseMessage;

import java.util.List;

public interface AssignmentService {

    List<Assignment> findAssByPhone(String teacher,String phone, String studentPhone, byte status);

    ResponseMessage<Object> savaAssignment(AssignmentIdDto assignmentDto);

    ResponseMessage<Object> deleteAssignmentById(Integer id);

    List<Assignment> findByTeacherList(String phone);

    ResponseMessage<Object> getFinishListMyAssignment(Integer id);

    ResponseMessage<Object> listReleaseAssignment(String phone, String role, Integer page, Integer limit, Byte status);

    ResponseMessage<Object> updateAssignmentStatus(Integer id, Byte status);
}
