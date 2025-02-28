package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.InternshipReport;
import com.practice.practicemanage.pojo.dto.InternshipReportDto;
import com.practice.practicemanage.pojo.dto.InternshipReportIdDto;
import com.practice.practicemanage.repository.InternshipReportRepository;
import com.practice.practicemanage.response.PaginatedResponse;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.IInternshipReportService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class InternshipReportService implements IInternshipReportService {

    @Autowired
    private InternshipReportRepository internshipReportRepository;
    @Autowired
    private LogUtil logUtil;

    @Override
    public ResponseMessage<Object> saveReport(InternshipReportDto internshipReportDto) {
        try {
            InternshipReport internshipReport = new InternshipReport();
            BeanUtils.copyProperties(internshipReportDto, internshipReport);
            internshipReport.setId(null);
            internshipReportRepository.save(internshipReport);
            return ResponseMessage.success("保存成功");
        } catch (Exception e) {
            logUtil.error(InternshipReportService.class, "保存失败", e);
            return ResponseMessage.error("保存失败");
        }
    }

    @Override
    public ResponseMessage<Object> findListByStudentPhone(String phone, String role, Integer page, Integer limit) {
        try {
            if (role.equals("STUDENT")) {

//                logUtil.info(InternshipReportService.class, "page:"+page+";limit:"+limit+";phone:"+ phone);
                Pageable reportPage = PageRequest.of(page -1, limit);

                Page<InternshipReport> internshipReports = internshipReportRepository.findByStudentPhoneAndStatusNot(phone, (byte) 0, reportPage);

                return returnPage(internshipReports);

            } else if (role.equals("TEACHER")) {

                Pageable reportPage = PageRequest.of(page -1, limit);

                Page<InternshipReport> internshipReports = internshipReportRepository.findByTeacherPhoneAndStatusNot(phone, (byte) 0, reportPage);

                return returnPage(internshipReports);

            }

            return ResponseMessage.error("权限外角色");

        } catch (Exception e) {
            logUtil.error(InternshipReportService.class, "查询报告失败", e);
            return ResponseMessage.error("查询报告失败");
        }
    }

    @Override
    public ResponseMessage<Object> getListById(Integer id) {
        try {
            Optional<InternshipReport> byId = internshipReportRepository.findById(id);
            if (byId.isPresent()){
                return ResponseMessage.success("获取修改报告信息成功", byId);
            }else {
                return ResponseMessage.error("获取修改报告信息失败");
            }
        } catch (Exception e) {
            logUtil.error(InternshipReportService.class, "获取修改报告信息失败", e);
            return ResponseMessage.error("获取修改报告信息失败");
        }
    }

    @Override
    public ResponseMessage<Object> saveReportById(InternshipReportIdDto internshipReportDto) {
        try {
            InternshipReport internshipReport = new InternshipReport();
            BeanUtils.copyProperties(internshipReportDto, internshipReport);
            internshipReportRepository.save(internshipReport);
            return ResponseMessage.success("修改成功");
        } catch (Exception e) {
            logUtil.error(InternshipReportService.class, "修改报告信息失败", e);
            return ResponseMessage.error("修改报告信息失败");
        }
    }

    public ResponseMessage<Object> returnPage (Page<InternshipReport> internshipReports) {
        List<InternshipReport> internshipReports1 = internshipReports.getContent();

        // 将查询结果中的时间戳转换
        for (InternshipReport internshipReport : internshipReports1) {
            if (internshipReport.getPuttime() != null) {
                internshipReport.setPuttime(Instant.ofEpochSecond(internshipReport.getPuttime().toEpochMilli()));  // 转换为时间戳（毫秒）
            }
        }

        // 获取总记录数和总页数
        long totalElements = internshipReports.getTotalElements();  // 总记录数
        int totalPages = internshipReports.getTotalPages();  // 总页数

        // 返回分页结果和相关信息
        return ResponseMessage.success("报告获取成功",
                new PaginatedResponse<>(internshipReports1, totalElements, totalPages));
    }
}
