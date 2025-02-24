package com.practice.practicemanage.service;

import com.practice.practicemanage.repository.StudentInfoRepository;
import com.practice.practicemanage.service.impl.IStudentInfoService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
