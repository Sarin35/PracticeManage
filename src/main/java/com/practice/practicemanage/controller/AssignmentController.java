package com.practice.practicemanage.controller;

import com.practice.practicemanage.pojo.Assignment;
import com.practice.practicemanage.pojo.StudentInfo;
import com.practice.practicemanage.pojo.TeacherInfo;
import com.practice.practicemanage.pojo.dto.AssignmentDto;
import com.practice.practicemanage.pojo.dto.AssignmentIdDto;
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
import java.util.Objects;

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
    @Autowired
    private TypeConversionUtil typeConversionUtil;

    @PostMapping("/listMyAssignment")
    public ResponseMessage<Object> listMyAssignments(@Validated @RequestBody Map<String, String> assignment){
        return getAssignmentList(assignment, (byte) 1); // 老师发布的作业 查询老师身份发布的作业
    }

    @PostMapping("/listMyAssignmentSp")
    public ResponseMessage<Object> listMyAssignmentSp(@Validated @RequestBody Map<String, String> assignment){
        return getAssignmentList(assignment, (byte) 3); // 已完成的作业
    }

    @PostMapping("/updaateAssignment") // 修改作业 发布作业
    public ResponseMessage<Object> updateAssignment(@Validated @RequestBody AssignmentIdDto assignmentDto) {
        return assignmentService.savaAssignment(assignmentDto);
    }

    @PostMapping("/deleteAssignment")
    public ResponseMessage<Object> deleteAssignment(@RequestBody Map<String, Integer> idMap) {
        return assignmentService.deleteAssignmentById(idMap.get("id"));
    }

    @PostMapping("/finishListMyAssignmentSp")
    public ResponseMessage<Object> finishListMyAssignment(@RequestBody Map<String, Integer> assignment) {
        return assignmentService.getFinishListMyAssignment(assignment.get("id"));
    }

    @PostMapping("/listReleaseAssignment")
    public ResponseMessage<Object> listReleaseAssignments(@RequestBody Map<String, String> lists) {
        return assignmentService.listReleaseAssignment(lists.get("phone"),
                                                       lists.get("role"),
                                                       typeConversionUtil.toInteger(lists.get("page")),  // 当前页数
                                                       typeConversionUtil.toInteger(lists.get("limit")),// 每页数据条数
                                                       typeConversionUtil.toByte(lists.get("status")));
    }

    @PostMapping("/updataStatusAssign")
    public ResponseMessage<Object> updateAssignment(@RequestBody Map<String, String> statusList) {
        return assignmentService.updateAssignmentStatus(typeConversionUtil.toInteger(statusList.get("id")), typeConversionUtil.toByte(statusList.get("status")));
    }

//    @PostMapping("/createAss")
//    public ResponseMessage<Object> createAss(@Validated @RequestBody AssignmentIdDto assignmentDto) {
//        return assignmentService.savaAssignment(assignmentDto);
//    }

    private ResponseMessage<Object> getAssignmentList(@RequestBody @Validated Map<String, String> assignment, byte status) {
        try {
            if (Objects.equals(assignment.get("role"), "STUDENT")) {

                StudentInfo studentInfo = (StudentInfo) studentInfoService.getTeacherPhoneByStudentPhone(assignment.get("phone"));
                TeacherInfo teacherInfo = (TeacherInfo) teacherInfoService.getTeacherListByPhone(studentInfo.getTeacherPhone());
                List<Assignment> assByPhone = assignmentService.findAssByPhone(teacherInfo.getName(), studentInfo.getTeacherPhone(), assignment.get("phone"), status); // 获取老师已发布但还未完成的作业
                if (assByPhone.isEmpty()){
                    return ResponseMessage.error("无作业");
                }
                // 将 puttimes 转换为时间戳（毫秒）
                for (Assignment assignments : assByPhone) {
                    if (assignments.getPuttimes() != null) {
                        assignments.setPuttimes(Instant.ofEpochSecond(assignments.getPuttimes().toEpochMilli()));  // 转换为时间戳（毫秒）
                    }
                }
                return ResponseMessage.success("已获取作业表", assByPhone);

            } else if (Objects.equals(assignment.get("role"), "TEACHER")) {

                List<Assignment> assignmentList = assignmentService.findByTeacherList(assignment.get("phone"));// 通过教师手机号 查询作业表
                if (assignmentList.isEmpty()){
                    return ResponseMessage.error("无作业");
                }
                // 将 puttimes 转换为时间戳（毫秒）
                for (Assignment assignments : assignmentList) {
                    if (assignments.getPuttimes() != null) {
                        assignments.setPuttimes(Instant.ofEpochSecond(assignments.getPuttimes().toEpochMilli()));  // 转换为时间戳（毫秒）
                    }
                }
                return ResponseMessage.success("已获取老师发布作业表", assignmentList);

            }

            return null;
        } catch (Exception e) {
            logUtil.error(AssignmentController.class, "查询我的作业失败", e);
            return ResponseMessage.error("查询我的作业失败");
        }
    }

}
