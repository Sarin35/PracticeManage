package com.practice.practicemanage.controller;

import com.practice.practicemanage.pojo.dto.InternshipReportDto;
import com.practice.practicemanage.pojo.dto.InternshipReportIdDto;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.InternshipReportServiceImpl;
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
public class InternshipReportController {

    @Autowired
    private InternshipReportServiceImpl internshipReportService;
    @Autowired
    private TypeConversionUtil typeConversionUtil;

    @PostMapping("/setRepoteLists")
    public ResponseMessage<Object> setReportList(@Validated @RequestBody InternshipReportDto internshipReportDto) {
        return internshipReportService.saveReport(internshipReportDto);
    }

    @PostMapping("/setintermshi")
    public ResponseMessage<Object> setReportListId(@Validated @RequestBody InternshipReportIdDto internshipReportDto) {
        return internshipReportService.saveReportById(internshipReportDto);
    }

    @PostMapping("/getRelotList")
    public ResponseMessage<Object> getReportList(@RequestBody Map<String, String> report) {
        System.out.println("report->>>>>>>>>>>>"+report);
        return internshipReportService.findListByStudentPhone(report.get("studentPhone"),
                report.get("role"),
                typeConversionUtil.toInteger(report.get("page")),
                typeConversionUtil.toInteger(report.get("limit")));
    }

    @PostMapping("/getInterList")
    public ResponseMessage<Object> getInterList(@RequestBody Map<String, Integer> IdMap) {
        return internshipReportService.getListById(IdMap.get("id"));
    }
}
