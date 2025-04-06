package com.practice.practicemanage.service.impl;

import com.practice.practicemanage.pojo.WeeklyReport;
import com.practice.practicemanage.pojo.dto.WeeklyReportDto;
import com.practice.practicemanage.repository.WeeklyReportRepository;
import com.practice.practicemanage.response.PaginatedResponse;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.WeeklyReportService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class WeeklyReportServiceImpl implements WeeklyReportService {

    @Autowired
    private WeeklyReportRepository weeklyReportRepository;
    @Autowired
    private LogUtil logUtil;

    @Override
    public ResponseMessage<Object> getMyWeekReport(String studentPhone, String teacherPhone, int page, int limit, byte status) {
        if (status == 1 || status == 2) {
            try {
                // 创建 Pageable 对象，PageRequest.of() 用于设置分页参数，page 从0开始，limit是每页大小
                Pageable pageable = PageRequest.of(page - 1, limit);

                // 查询分页数据
                Page<WeeklyReport> weeklyReportPage = weeklyReportRepository.findByStudentPhoneAndTeacherPhoneAndStatus(studentPhone, teacherPhone, status, pageable);

                return returnPage(weeklyReportPage);
            } catch (Exception e) {
                logUtil.error(WeeklyReport.class, "查询用户表失败", e);
                return ResponseMessage.success("周志获取失败");
            }
        } else {
            try {
                // 创建 Pageable 对象，PageRequest.of() 用于设置分页参数，page 从0开始，limit是每页大小
                Pageable pageable = PageRequest.of(page - 1, limit);

                // 查询分页数据
                Page<WeeklyReport> weeklyReportPage = weeklyReportRepository.findByStudentPhoneAndTeacherPhoneAndStatusNot(studentPhone, teacherPhone, 0, pageable);

                return returnPage(weeklyReportPage);
            } catch (Exception e) {
                logUtil.error(WeeklyReport.class, "查询用户表失败", e);
                return ResponseMessage.success("周志获取失败");
            }
        }
    }

    @Override
    public ResponseMessage<Object> getMyWeekReports(String teacherPhone, int page, int limit, byte status) {
        if (status == 1 || status == 2) {
            try {
                // 创建 Pageable 对象，PageRequest.of() 用于设置分页参数，page 从0开始，limit是每页大小
                Pageable pageable = PageRequest.of(page - 1, limit);

                // 查询分页数据
                Page<WeeklyReport> weeklyReportPage = weeklyReportRepository.findByTeacherPhoneAndStatus(teacherPhone, status, pageable);

                return returnPage(weeklyReportPage);
            } catch (Exception e) {
                logUtil.error(WeeklyReport.class, "查询用户表失败", e);
                return ResponseMessage.success("周志获取失败");
            }
        } else {
            try {
                // 创建 Pageable 对象，PageRequest.of() 用于设置分页参数，page 从0开始，limit是每页大小
                Pageable pageable = PageRequest.of(page - 1, limit);

                // 查询分页数据
                Page<WeeklyReport> weeklyReportPage = weeklyReportRepository.findByTeacherPhoneAndStatusNot(teacherPhone, (byte) 0, pageable);

                return returnPage(weeklyReportPage);
            } catch (Exception e) {
                logUtil.error(WeeklyReport.class, "查询用户表失败", e);
                return ResponseMessage.success("周志获取失败");
            }
        }
    }

    @Override
    public ResponseMessage<Object> updateStatus(Integer id, Integer status) {
        try {
            System.out.println("获取的参数：id:->>>>"+id+"status:->>>>"+status);
            int i = weeklyReportRepository.updateStatusById(id, status.byteValue());
            if (i>0) {
                return ResponseMessage.success("更改成功");
            } else {
                return ResponseMessage.success("更改失败");
            }
        } catch (Exception e) {
            logUtil.error(WeeklyReport.class, "sql导致更改失败", e);
            return ResponseMessage.error("更改失败");
        }
    }

    @Override
    public ResponseMessage<Object> findByFilter(String studentPhone, String teacherPhone, Integer page, Integer limit, String title) {
        try {
            // 创建 Pageable 对象，PageRequest.of() 用于设置分页参数，page 从0开始，limit是每页大小
            Pageable pageable = PageRequest.of(page - 1, limit);

            // 查询分页数据
            Page<WeeklyReport> weeklyReportPage = weeklyReportRepository.searchByPhoneAndTitle(studentPhone, teacherPhone, title, pageable);

            return returnPage(weeklyReportPage);
        } catch (Exception e) {
            logUtil.error(WeeklyReport.class, "查询失败", e);
            return ResponseMessage.success("查询失败");
        }
    }

    @Override
    public ResponseMessage<Object> findByFilters(String teacherPhone, Integer page, Integer limit, String title) {
        try {
            // 创建 Pageable 对象，PageRequest.of() 用于设置分页参数，page 从0开始，limit是每页大小
            Pageable pageable = PageRequest.of(page - 1, limit);

            // 查询分页数据
            Page<WeeklyReport> weeklyReportPage = weeklyReportRepository.searchByPhoneAndTitles(teacherPhone, title, pageable);

            return returnPage(weeklyReportPage);
        } catch (Exception e) {
            logUtil.error(WeeklyReport.class, "查询失败", e);
            return ResponseMessage.success("查询失败");
        }
    }

    @Override
    public ResponseMessage<Object> weekSava(WeeklyReportDto weeklyReportDto) {
        try {
            if (weeklyReportDto.getId() == 0) {
                WeeklyReport weeklyReport = new WeeklyReport();
                BeanUtils.copyProperties(weeklyReportDto, weeklyReport);
                weeklyReport.setId(null);
                weeklyReportRepository.save(weeklyReport);
                return ResponseMessage.success("新增成功");
            } else {
                WeeklyReport weeklyReport = new WeeklyReport();
                BeanUtils.copyProperties(weeklyReportDto, weeklyReport);
                weeklyReportRepository.save(weeklyReport);
                return ResponseMessage.success("修改成功");
            }
        } catch (Exception e) {
            logUtil.error(WeeklyReportServiceImpl.class, "新增或修改报错", e);
            return ResponseMessage.error("新增或修改失败");
        }
    }

    public ResponseMessage<Object> returnPage (Page<WeeklyReport> weeklyReportPage) {
        List<WeeklyReport> weeklyReportList = weeklyReportPage.getContent();

        // 将查询结果中的时间戳转换
        for (WeeklyReport weeklyReport : weeklyReportList) {
            if (weeklyReport.getPuttime() != null) {
                weeklyReport.setPuttime(Instant.ofEpochSecond(weeklyReport.getPuttime().toEpochMilli()));  // 转换为时间戳（毫秒）
            }
        }

        // 获取总记录数和总页数
        long totalElements = weeklyReportPage.getTotalElements();  // 总记录数
        int totalPages = weeklyReportPage.getTotalPages();  // 总页数

        // 返回分页结果和相关信息
        return ResponseMessage.success("周志获取成功",
                new PaginatedResponse<>(weeklyReportList, totalElements, totalPages));
    }
}

