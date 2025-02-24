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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class AssignmentController {

    有一个缓存问题，已经修改了的文件，但是不会重新查询

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

    private ResponseMessage<Object> getAssignmentList(@RequestBody @Validated PhoneDto assignment, byte status) {
        try {
            System.out.println("传递的数据："+assignment.getPhone());
            StudentInfo teacherPhone = (StudentInfo) studentInfoService.getTeacherPhoneByStudentPhone(assignment.getPhone());
            System.out.println("学生个人信息表获取老师手机号："+teacherPhone.getTeacherPhone());
            TeacherInfo teacherInfo = (TeacherInfo) teacherInfoService.getTeacherListByPhone(teacherPhone.getTeacherPhone());
            System.out.println("老师个人信息表："+teacherInfo);
            List<Assignment> assByPhone = assignmentService.findAssByPhone(teacherInfo.getName(), teacherPhone.getTeacherPhone(), assignment.getPhone(), status);
            System.out.println("作业表："+assByPhone);
//            Map<String, Object> assignmentList = new HashMap<>();
//            assignmentList.put("teacherInfo", teacherInfo);
//            assignmentList.put("assignment", assByPhone);
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
