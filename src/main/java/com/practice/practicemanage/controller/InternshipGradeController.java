package com.practice.practicemanage.controller;

import com.practice.practicemanage.pojo.dto.InternshipGradeDto;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.InternshipGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class InternshipGradeController {

    @Autowired
    private InternshipGradeService internshipGradeService;

    @PostMapping("/getInternshipGradeList")
    public ResponseMessage<Object> InternshipGrade(@Validated @RequestBody Map<String, String> lists) {
        if (lists.get("role").equals("STUDENT")) {
            return internshipGradeService.getAllInfo(lists.get("phone"));
        } else if (lists.get("role").equals("TEACHER")) {
            return internshipGradeService.getTeacherinfo(lists.get("phone"));
        }
        return ResponseMessage.error("非合法身份");
    }

    @PostMapping("/updateGrade")
    public ResponseMessage<Object> updateGrades(@Validated @RequestBody InternshipGradeDto internshipGradeDto) {
        return internshipGradeService.savaGrade(internshipGradeDto);
    }
}
