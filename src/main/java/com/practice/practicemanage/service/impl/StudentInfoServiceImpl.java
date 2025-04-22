package com.practice.practicemanage.service.impl;

import com.practice.practicemanage.pojo.StudentInfo;
import com.practice.practicemanage.pojo.dto.StudentInfoDto;
import com.practice.practicemanage.repository.StudentInfoRepository;
import com.practice.practicemanage.response.PaginatedResponse;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.StudentInfoService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentInfoServiceImpl implements StudentInfoService {

    @Autowired
    private StudentInfoRepository studentInfoRepository;
    @Autowired
    private LogUtil logUtil;

    @Override
    public Object getTeacherPhoneByStudentPhone(String Phone) {
        try {
            return studentInfoRepository.findByPhone(Phone);
        } catch (Exception e) {
            logUtil.error(StudentInfoServiceImpl.class, "查询老师电话失败", e);
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
            logUtil.error(StudentInfoServiceImpl.class, "获取学生信息失败", e);
            return ResponseMessage.error("获取学生信息失败");
        }
    }

    @Override
    public ResponseMessage<Object> saveStudentInfo(StudentInfoDto studentDto) {
        try {
            StudentInfo studentInfo = new StudentInfo();
            BeanUtils.copyProperties(studentDto, studentInfo);
            studentInfoRepository.save(studentInfo);
            return ResponseMessage.success("保存学生信息成功");
        } catch (Exception e) {
            logUtil.error(StudentInfoServiceImpl.class, "保存学生信息失败", e);
            return ResponseMessage.error("保存学生信息失败");
        }
    }

    @Override
    public List<String> findByName(String studentName) {
        try {
//            模糊查询
            List<StudentInfo> studentInfos = studentInfoRepository.findByNameContaining(studentName);
            if (studentInfos.isEmpty()) {
                return List.of();
            }
            return studentInfos.stream()
                    .map(StudentInfo::getPhone)
                    .toList();
        } catch (Exception e) {
            logUtil.error(StudentInfoServiceImpl.class, "根据姓名查询学生信息失败", e);
            return List.of();
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
