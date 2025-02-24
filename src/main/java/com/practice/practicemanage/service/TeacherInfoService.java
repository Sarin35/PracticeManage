package com.practice.practicemanage.service;

import com.practice.practicemanage.repository.TeacherInfoRepository;
import com.practice.practicemanage.service.impl.ITeacherInfoService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherInfoService implements ITeacherInfoService {

    @Autowired
    private LogUtil logUtil;
    @Autowired
    private TeacherInfoRepository teacherInfoRepository;

    @Override
    public Object getTeacherListByPhone(String phone) {
        try {
            return teacherInfoRepository.findByPhone(phone);
        } catch (Exception e) {
            logUtil.error(TeacherInfoService.class, "查询老师列表失败");
        }
        return null;
    }
}
