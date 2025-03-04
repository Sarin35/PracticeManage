package com.practice.practicemanage.controller;

import com.practice.practicemanage.pojo.dto.InternshipGuidanceDto;
import com.practice.practicemanage.pojo.dto.InternshipGuidanceIdDto;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.InternshipGuidanceService;
import com.practice.practicemanage.utils.TypeConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class InternshipGuidanceController {

    @Autowired
    private InternshipGuidanceService internshipGuidanceService;
    @Autowired
    private TypeConversionUtil typeConversionUtil;

    @PostMapping("/getGuidanceList")
    public ResponseMessage<Object> getGuidanceLists(@RequestBody Map<String, String> guidanceList) {
//        根据公司人员手机号，查找该公司公司名，通过公司名查找该公司的所有指导人和所有实习人员
//        选择负责人员后，通过手机号查找该指导人员负责的所有实习人员
//        选择实习人员后，查找她的负责人员和负责教师以及单位和学校 （单位可默认）
        return internshipGuidanceService.getGuidanceList(guidanceList.get("phone"));
    }

    @PostMapping("/findTeacherByStudent")
    public ResponseMessage<Object> findTeacherByStudents(@RequestBody Map<String, String> guidanceList) {
        return internshipGuidanceService.findTeacherByStudent(guidanceList.get("phone"));
    }

    @PostMapping("/putList")
    public ResponseMessage<Object> putLists(@Validated @RequestBody InternshipGuidanceDto internshipGuidanceDto) {
        return internshipGuidanceService.putList(internshipGuidanceDto);
    }

    @PostMapping("/getGuidanceListSp")
    public ResponseMessage<Object> getGuidanceListSp(@RequestBody Map<String, String> map) {
        return internshipGuidanceService.getGuidanceLists(map.get("phone"),
                typeConversionUtil.toInteger(map.get("page")),  // 当前页数
                typeConversionUtil.toInteger(map.get("limit")),  // 每页数据条数
                typeConversionUtil.toInteger(map.get("status")));  // 每页数据条数
    }

    @PostMapping("/updateStatusPut")
    public ResponseMessage<Object> updateStatusPuts(@RequestBody Map<String, Integer> map) {
        return internshipGuidanceService.updateStatusPut(map.get("id"), map.get("status"));
    }

    @PostMapping("/createquidances")
    public ResponseMessage<Object> createQuidance(@RequestBody InternshipGuidanceIdDto internshipGuidanceDto) {
        return internshipGuidanceService.savaCreatequidances(internshipGuidanceDto);
    }

}
