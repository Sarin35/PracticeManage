package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.School;
import com.practice.practicemanage.repository.SchoolRepository;
import com.practice.practicemanage.repository.StudentInfoRepository;
import com.practice.practicemanage.repository.TeacherInfoRepository;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.ISchoolService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchoolService implements ISchoolService {

    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private LogUtil logUtil;
    @Autowired
    private StudentInfoRepository studentInfoRepository;
    @Autowired
    private TeacherInfoRepository teacherInfoRepository;

    @Override
    public ResponseMessage<Object> getSchool() {
        try {
            List<School> schools = schoolRepository.findByStatus((byte) 1);
            if (schools.isEmpty()) {
                return ResponseMessage.error("获取学校列表失败");
            }

            List<Map<String, String>> schoolList = new ArrayList<>();

            for (School school : schools) {
                Map<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(school.getId()));
                map.put("schoolName", school.getSchoolName());

                // 统计学生和教师数量
                long studentCount = studentInfoRepository.countBySchool(school.getSchoolName());
                long teacherCount = teacherInfoRepository.countBySchool(school.getSchoolName());

                map.put("studentNumber", String.valueOf(studentCount));
                map.put("teacherNumber", String.valueOf(teacherCount));

                schoolList.add(map);
            }
            return ResponseMessage.success("获取学校列表成功", schoolList);
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
