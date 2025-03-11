package com.practice.practicemanage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.practicemanage.pojo.dto.StudentInfoDto;
import com.practice.practicemanage.pojo.dto.TeacherInfoDto;
import com.practice.practicemanage.pojo.dto.UnitUserDto;
import com.practice.practicemanage.pojo.dto.UserIdDto;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.StudentInfoService;
import com.practice.practicemanage.service.TeacherInfoService;
import com.practice.practicemanage.service.UnitUserService;
import com.practice.practicemanage.service.UserService;
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
    private StudentInfoService studentInfoService;
    @Autowired
    private TeacherInfoService teacherInfoService;
    @Autowired
    private UnitUserService unitUserService;
    @Autowired
    private UserService userService;

    @PostMapping("/getLoginIndex")
    public ResponseMessage<Object> getLoginIndex(@RequestBody Map<String, String> map) {
        return userService.getLoginIndex(map.get("phone"), map.get("role"));
    }

    @PostMapping("/saveLoginIndexSave")
    public ResponseMessage<Object> saveLoginIndexSave(@RequestBody Map<String, Object> map) {
        String role = (String) map.get("role");
        Object data = map.get("data");

        ObjectMapper mapper = new ObjectMapper();

        return switch (role) {
            case "STUDENT" -> {
                StudentInfoDto studentDto = mapper.convertValue(data, StudentInfoDto.class);
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
