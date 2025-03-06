package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.StudentInfo;
import com.practice.practicemanage.repository.StudentInfoRepository;
import com.practice.practicemanage.response.PaginatedResponse;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.IStudentInfoService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentInfoService implements IStudentInfoService {

    @Autowired
    private StudentInfoRepository studentInfoRepository;
    @Autowired
    private LogUtil logUtil;

    @Override
    public Object getTeacherPhoneByStudentPhone(String Phone) {
        try {
            return studentInfoRepository.findByPhone(Phone);
        } catch (Exception e) {
            logUtil.error(StudentInfoService.class, "查询老师电话失败", e);
            return null;
        }
    }

    @Override
    public ResponseMessage<Object> getStudent(Integer page, Integer limit) {
        try {
            Pageable pageable = PageRequest.of(page - 1, limit);
            Page<StudentInfo> all = studentInfoRepository.findByStatus((byte) 1, pageable);
            if (all.isEmpty()){
                return ResponseMessage.error("获取学生信息失败");
            }
            return returnPage(all);
        } catch (Exception e) {
            logUtil.error(StudentInfoService.class, "获取学生信息失败", e);
            return ResponseMessage.error("获取学生信息失败");
        }
    }

    public ResponseMessage<Object> returnPage (Page<StudentInfo> studentInfoPage) {
        List<StudentInfo> pageContent = studentInfoPage.getContent();

        // 获取总记录数和总页数
        long totalElements = studentInfoPage.getTotalElements();  // 总记录数
        int totalPages = studentInfoPage.getTotalPages();  // 总页数

        // 返回分页结果和相关信息
        return ResponseMessage.success("周志获取成功",
                new PaginatedResponse<>(pageContent, totalElements, totalPages));
    }
}
