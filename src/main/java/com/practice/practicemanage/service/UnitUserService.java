package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.UnitUser;
import com.practice.practicemanage.pojo.dto.UnitUserDto;
import com.practice.practicemanage.repository.UnitUserRepository;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.IUnitUserService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitUserService implements IUnitUserService {

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
            logUtil.error(UnitUserService.class, "保存单位信息失败", e);
            return ResponseMessage.error("保存单位信息失败");
        }
    }
}
