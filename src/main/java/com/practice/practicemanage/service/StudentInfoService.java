package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.StudentInfo;
import com.practice.practicemanage.repository.StudentInfoRepository;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.IStudentInfoService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseMessage<Object> getStudent() {
        try {
            List<StudentInfo> all = studentInfoRepository.findAll();
            if (all.isEmpty()){
                return ResponseMessage.error("获取学生信息失败");
            }
            return ResponseMessage.success("获取学生信息", all);
        } catch (Exception e) {
            logUtil.error(StudentInfoService.class, "获取学生信息失败", e);
            return ResponseMessage.error("获取学生信息失败");
        }
    }
}
