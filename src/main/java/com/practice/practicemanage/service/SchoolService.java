package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.School;
import com.practice.practicemanage.repository.SchoolRepository;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.ISchoolService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService implements ISchoolService {

    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private LogUtil logUtil;

    @Override
    public ResponseMessage<Object> getSchool() {
        try {
            List<School> schools = schoolRepository.findAll();
            if (schools.isEmpty()) {
                return ResponseMessage.error("获取学校列表失败");
            }
            return ResponseMessage.success("获取学校列表成功",schools);
        } catch (Exception e) {
            logUtil.error(SchoolService.class, "获取学校列表失败", e);
            return ResponseMessage.error("获取学校列表失败");
        }
    }

    @Override
    public ResponseMessage<Object> getSchoolDelete(Integer id) {
        try {
            schoolRepository.updateStatusToZeroById(id);
            return ResponseMessage.success("删除学校成功");
        } catch (Exception e) {
            logUtil.error(UserService.class, "删除学校失败", e);
            return ResponseMessage.error("删除学校失败");
        }
    }
}
