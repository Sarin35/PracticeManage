package com.practice.practicemanage.service.impl;

import com.practice.practicemanage.pojo.UnitUser;
import com.practice.practicemanage.pojo.dto.UnitUserDto;
import com.practice.practicemanage.repository.UnitUserRepository;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.UnitUserService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitUserServiceImpl implements UnitUserService {

    @Autowired
    private UnitUserRepository unitUserRepository;
    @Autowired
    private LogUtil logUtil;

    @Override
    public ResponseMessage<Object> saveUnitInfo(UnitUserDto unitDto) {
        try {
            UnitUser unitUser = new UnitUser();
            BeanUtils.copyProperties(unitDto, unitUser);
            unitUserRepository.save(unitUser);
            return ResponseMessage.success("保存单位信息成功");
        } catch (Exception e) {
            logUtil.error(UnitUserServiceImpl.class, "保存单位信息失败", e);
            return ResponseMessage.error("保存单位信息失败");
        }
    }

    @Override
    public ResponseMessage<Object> getUnitTeacher() {
        try {
            List<UnitUser> unitUsers = unitUserRepository.findByStatus((byte) 1);
            if (unitUsers.isEmpty()) {
                return ResponseMessage.error("获取企业教师信息失败");
            }
            return ResponseMessage.success("获取企业教师信息成功", unitUsers);
        } catch (Exception e) {
            logUtil.error(UnitUserServiceImpl.class, "获取单位信息失败", e);
            return ResponseMessage.error("获取企业教师信息失败");
        }
    }

    @Override
    public ResponseMessage<Object> getunitTeacher(String unitName) {
        try{
            List<UnitUser> unitUsers = unitUserRepository.findByName(unitName);
            if (unitUsers.isEmpty()) {
                return ResponseMessage.error("获取企业教师信息失败");
            }
            return ResponseMessage.success("获取企业教师信息成功", unitUsers);
        } catch (Exception e) {
            logUtil.error(UnitUserServiceImpl.class, "获取单位信息失败", e);
            return ResponseMessage.error("获取企业教师信息失败");
        }
    }
}
