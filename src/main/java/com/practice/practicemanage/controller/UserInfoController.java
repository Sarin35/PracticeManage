package com.practice.practicemanage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.practicemanage.pojo.dto.StudentInfoDto;
import com.practice.practicemanage.pojo.dto.TeacherInfoDto;
import com.practice.practicemanage.pojo.dto.UnitUserDto;
import com.practice.practicemanage.pojo.dto.UserIdDto;
import com.practice.practicemanage.response.ResponseMessage;
//import com.practice.practicemanage.service.InternshipGradeService;
import com.practice.practicemanage.service.UnitService;
import com.practice.practicemanage.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class UserInfoController {

    @Autowired
    private StudentInfoServiceImpl studentInfoService;
    @Autowired
    private TeacherInfoServiceImpl teacherInfoService;
    @Autowired
    private UnitUserServiceImpl unitUserService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private InternshipGradeServiceImpl internshipGradeService;

    @PostMapping("/getLoginIndex")
    public ResponseMessage<Object> getLoginIndex(@RequestBody Map<String, String> map) {
        return userService.getLoginIndex(map.get("phone"), map.get("role"));
    }

    @PostMapping("/getTeacherInfo")
    public ResponseMessage<Object> getTeacherInfo() {
        return teacherInfoService.getTeacher();
    }

    @PostMapping("/getUnitTeacherInfo")
    public ResponseMessage<Object> getUnitTeacherInfo() {
        return unitUserService.getUnitTeacher();
    }

    @PostMapping("/getUnits")
    public ResponseMessage<Object> getUnit() {
        return unitService.getUnitList();
    }

    @PostMapping("/getUnitTeachers")
    public ResponseMessage<Object> getUnitTeacher(@RequestBody Map<String, String> map) {
        return unitUserService.getunitTeacher(map.get("unitName"));
    }

    @PostMapping("/saveLoginIndexSave")
    public ResponseMessage<Object> saveLoginIndexSave(@RequestBody Map<String, Object> map) {
        String role = (String) map.get("role");
        Object data = map.get("data");

        ObjectMapper mapper = new ObjectMapper();

        return switch (role) {
            case "STUDENT" -> {
                StudentInfoDto studentDto = mapper.convertValue(data, StudentInfoDto.class);
                internshipGradeService.savaStudent(studentDto);
                yield studentInfoService.saveStudentInfo(studentDto);
            }
            case "TEACHER" -> {
                TeacherInfoDto teacherDto = mapper.convertValue(data, TeacherInfoDto.class);
                yield teacherInfoService.saveTeacherInfo(teacherDto);
            }
            case "UNIT" -> {
                UnitUserDto unitDto = mapper.convertValue(data, UnitUserDto.class);
                yield unitUserService.saveUnitInfo(unitDto);
            }
            case "ADMINISTRATOR" -> {
                UserIdDto userIdDto = mapper.convertValue(data, UserIdDto.class);
                yield userService.saveLoginIndexSave(userIdDto);
            }
            default -> ResponseMessage.error("保存失败");
        };
    }

    @PostMapping("/findNotices")
    public ResponseMessage<Object> findNotices(@RequestBody Map<String, String> map) {
        return userService.findNotices(map.get("phone"));
    }

    @PostMapping("/findNoticesTN")
    public ResponseMessage<Object> findNoticesTN(@RequestBody Map<String, String> map) {
        return userService.findNoticesTN(map.get("phone"));
    }

    @PostMapping("/findNoticesA")
    public ResponseMessage<Object> findNoticesA() {
        return userService.findNoticesA();
    }

    @PostMapping("/findNoticesSys")
    public ResponseMessage<Object> findNoticesSys() {
        return userService.findNoticesSys();
    }

}
