package com.practice.practicemanage.controller;

import com.practice.practicemanage.pojo.Assignment;
import com.practice.practicemanage.pojo.StudentInfo;
import com.practice.practicemanage.pojo.TeacherInfo;
import com.practice.practicemanage.pojo.dto.AssignmentDto;
import com.practice.practicemanage.pojo.dto.PhoneDto;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.AssignmentService;
import com.practice.practicemanage.service.StudentInfoService;
import com.practice.practicemanage.service.TeacherInfoService;
import com.practice.practicemanage.utils.LogUtil;
import com.practice.practicemanage.utils.TypeConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class AssignmentController {

//    有一个缓存问题，已经修改了的文件，但是不会重新查询

    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private StudentInfoService studentInfoService;
    @Autowired
    private TeacherInfoService teacherInfoService;
    @Autowired
    private LogUtil logUtil;

    @PostMapping("/listMyAssignment")
    public ResponseMessage<Object> listMyAssignments(@Validated @RequestBody PhoneDto assignment){
        return getAssignmentList(assignment, (byte) 1); // 老师发布的作业
    }

    @PostMapping("/listMyAssignmentSp")
    public ResponseMessage<Object> listMyAssignmentSp(@Validated @RequestBody PhoneDto assignment){
        return getAssignmentList(assignment, (byte) 3); // 已完成的作业
    }

    @PostMapping("/updaateAssignment")
    public ResponseMessage<Object> updateAssignment(@Validated @RequestBody AssignmentDto assignmentDto) {
        return assignmentService.savaAssignment(assignmentDto);
    }

    @PostMapping("/deleteAssignment")
    public ResponseMessage<Object> deleteAssignment(@RequestBody Map<String, Integer> idMap) {
        return assignmentService.deleteAssignmentById(idMap.get("id"));
    }

    private ResponseMessage<Object> getAssignmentList(@RequestBody @Validated PhoneDto assignment, byte status) {
        try {
            StudentInfo teacherPhone = (StudentInfo) studentInfoService.getTeacherPhoneByStudentPhone(assignment.getPhone());
            TeacherInfo teacherInfo = (TeacherInfo) teacherInfoService.getTeacherListByPhone(teacherPhone.getTeacherPhone());
            List<Assignment> assByPhone = assignmentService.findAssByPhone(teacherInfo.getName(), teacherPhone.getTeacherPhone(), assignment.getPhone(), status);
            // 将 puttimes 转换为时间戳（毫秒）
            for (Assignment assignments : assByPhone) {
                if (assignments.getPuttimes() != null) {
                    assignments.setPuttimes(Instant.ofEpochSecond(assignments.getPuttimes().toEpochMilli()));  // 转换为时间戳（毫秒）
                }
            }
            if (assByPhone == null || assByPhone.isEmpty()){
                return ResponseMessage.success("无作业",assByPhone);
            }
            return ResponseMessage.success("已获取作业表", assByPhone);
        } catch (Exception e) {
            logUtil.error(AssignmentController.class, "查询我的作业失败", e);
            return ResponseMessage.error("查询我的作业失败");
        }
    }

}
