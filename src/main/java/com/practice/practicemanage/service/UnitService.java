package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.Unit;
import com.practice.practicemanage.repository.UnitRepository;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.IUnitService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService implements IUnitService {

    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private LogUtil logUtil;

    @Override
    public ResponseMessage<Object> getUnit() {
        try {
            List<Unit> unitList = unitRepository.findAll();
            if (unitList.isEmpty()) {
                return ResponseMessage.error("获取公司列表失败");
            }
            return ResponseMessage.success("获取公司信息成功", unitList);
        } catch (Exception e) {
            logUtil.error(UnitService.class, "获取公司列表失败");
            return ResponseMessage.error("获取公司列表失败");
        }
    }

    @Override
    public ResponseMessage<Object> getUnitDelete(Integer id) {
        try {
            unitRepository.updateStatusToZeroById(id);
            return ResponseMessage.success("删除公司成功");
        } catch (Exception e) {
            logUtil.error(UserService.class, "删除公司失败", e);
            return ResponseMessage.error("删除公司失败");
        }
    }
}
