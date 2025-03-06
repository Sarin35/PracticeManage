package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.Unit;
import com.practice.practicemanage.repository.StudentInfoRepository;
import com.practice.practicemanage.repository.UnitRepository;
import com.practice.practicemanage.repository.UnitUserRepository;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.IUnitService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UnitService implements IUnitService {

    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private LogUtil logUtil;
    @Autowired
    private StudentInfoRepository studentInfoRepository;
    @Autowired
    private UnitUserRepository unitUserRepository;

    @Override
    public ResponseMessage<Object> getUnit() {
        try {
            List<Unit> unitList = unitRepository.findByStatus((byte) 1);
            if (unitList.isEmpty()) {
                return ResponseMessage.error("获取公司列表失败");
            }
            List<Map<String, String>> list = new ArrayList<>();
            for (Unit unit: unitList) {
                Map<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(unit.getId()));
                map.put("unitName", unit.getUnitName());

                // 统计学生和教师数量
                long studentCount = studentInfoRepository.countByUnitName(unit.getUnitName());
                long unitCount = unitUserRepository.countByName(unit.getUnitName());

                map.put("studentNumber", String.valueOf(studentCount));
                map.put("unitNumber", String.valueOf(unitCount));

                list.add(map);
            }
            return ResponseMessage.success("获取公司信息成功", list);
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
