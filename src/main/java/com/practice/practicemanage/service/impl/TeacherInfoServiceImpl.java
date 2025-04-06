package com.practice.practicemanage.service.impl;

import com.practice.practicemanage.pojo.TeacherInfo;
import com.practice.practicemanage.pojo.dto.TeacherInfoDto;
import com.practice.practicemanage.repository.TeacherInfoRepository;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.TeacherInfoService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherInfoServiceImpl implements TeacherInfoService {

    @Autowired
    private LogUtil logUtil;
    @Autowired
    private TeacherInfoRepository teacherInfoRepository;

    @Override
    public Object getTeacherListByPhone(String phone) {
        try {
            return teacherInfoRepository.findByPhone(phone);
        } catch (Exception e) {
            logUtil.error(TeacherInfoServiceImpl.class, "查询老师列表失败");
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
            logUtil.error(TeacherInfoServiceImpl.class, "获取老师信息失败", e);
            return ResponseMessage.error("获取老师信息失败");
        }
    }

    @Override
    public ResponseMessage<Object> saveTeacherInfo(TeacherInfoDto teacherDto) {
        try {
            TeacherInfo teacherInfo = new TeacherInfo();
            BeanUtils.copyProperties(teacherDto, teacherInfo);
            teacherInfoRepository.save(teacherInfo);
            return ResponseMessage.success("保存老师信息成功");
        } catch (Exception e) {
            logUtil.error(TeacherInfoServiceImpl.class, "保存老师信息失败", e);
            return ResponseMessage.error("保存老师信息失败");
        }
    }
}
