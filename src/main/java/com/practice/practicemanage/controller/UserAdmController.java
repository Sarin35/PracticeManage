package com.practice.practicemanage.controller;

import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class UserAdmController {

    /**
     *     ResponseMessage<Object> getSchool();
     *     ResponseMessage<Object> getTeacher();
     *     ResponseMessage<Object> getStudent();
     *     ResponseMessage<Object> getUnit();
     */

    @Autowired
    private UserService userService;
    @Autowired
    private TeacherInfoService teacherInfoService;
    @Autowired
    private StudentInfoService studentInfoService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private SchoolService schoolService;

    @PostMapping("/getRoles")
    public ResponseMessage<Object> getRoles() {
        return userService.getRoles();
    }

    @PostMapping("/getTeacher")
    public ResponseMessage<Object> getTeacher() {
        return teacherInfoService.getTeacher();
    }

    @PostMapping("/getStudent")
    public ResponseMessage<Object> getStudent(@RequestBody Map<String, Integer> map) {
        return studentInfoService.getStudent(map.get("page"), map.get("limit"));
    }

    @PostMapping("/getUnit")
    public ResponseMessage<Object> getUnit() {
        return unitService.getUnit();
    }

    @PostMapping("/getSchool")
    public ResponseMessage<Object> getSchool() {
        return schoolService.getSchool();
    }

    @PostMapping("/getRolesDelete")
    public ResponseMessage<Object> getRolesDelete(@RequestBody Map<String, Integer> id) {
        return userService.getRolesDelete(id.get("id"));
    }

//    学生和教师无法删除
//    @PostMapping("/getTeacherDelete")
//    public ResponseMessage<Object> getTeacherDelete(@RequestBody Integer id) {
//        return teacherInfoService.getTeacherDelete(id);
//    }
//
//    @PostMapping("/getStudentDelete")
//    public ResponseMessage<Object> getStudentDelete(@RequestBody Integer id) {
//        return studentInfoService.getStudentDelete(id);
//    }

    @PostMapping("/getUnitDelete")
    public ResponseMessage<Object> getUnitDelete(@RequestBody Map<String, Integer> id) {
        return unitService.getUnitDelete(id.get("id"));
    }

    @PostMapping("/getSchoolDelete")
    public ResponseMessage<Object> getSchoolDelete(@RequestBody Map<String, Integer> id) {
        return schoolService.getSchoolDelete(id.get("id"));
    }
}
