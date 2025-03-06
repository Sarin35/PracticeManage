package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.TeacherInfo;
import com.practice.practicemanage.repository.TeacherInfoRepository;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.ITeacherInfoService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public ResponseMessage<Object> getTeacher() {
        try {
            List<TeacherInfo> teacherInfos = teacherInfoRepository.findByStatus((byte) 1);
            if (teacherInfos.isEmpty()) {
                return ResponseMessage.error("获取老师信息失败");
            }
            return ResponseMessage.success("获取信息成功", teacherInfos);
        } catch (Exception e) {
            logUtil.error(TeacherInfoService.class, "获取老师信息失败", e);
            return ResponseMessage.error("获取老师信息失败");
        }
    }
}
