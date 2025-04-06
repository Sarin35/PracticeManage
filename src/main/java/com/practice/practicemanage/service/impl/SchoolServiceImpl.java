package com.practice.practicemanage.service.impl;

import com.practice.practicemanage.pojo.School;
import com.practice.practicemanage.pojo.StudentInfo;
import com.practice.practicemanage.pojo.TeacherInfo;
import com.practice.practicemanage.pojo.dto.SchoolDto;
import com.practice.practicemanage.repository.SchoolRepository;
import com.practice.practicemanage.repository.StudentInfoRepository;
import com.practice.practicemanage.repository.TeacherInfoRepository;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.SchoolService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchoolServiceImpl implements SchoolService {

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
            logUtil.error(SchoolServiceImpl.class, "获取学校列表失败", e);
            return ResponseMessage.error("获取学校列表失败");
        }
    }


    @Override
    public ResponseMessage<Object> getSchoolDelete(Integer id) {
        try {
            School school = schoolRepository.findById(id).orElseThrow(() -> new RuntimeException("学校不存在"));
            List<TeacherInfo> teacherInfoList = teacherInfoRepository.findBySchoolAndStatus(school.getSchoolName(), (byte) 1);
            if (!teacherInfoList.isEmpty()) {
                return ResponseMessage.error("该学校下有在职教师，无法删除");
            }
            List<StudentInfo> studentInfoList = studentInfoRepository.findBySchoolAndStatus(school.getSchoolName(), (byte) 1);
            if (!studentInfoList.isEmpty()) {
                return ResponseMessage.error("该学校下有在校学生，无法删除");
            }
            schoolRepository.updateStatusToZeroById(id);
            return ResponseMessage.success("删除学校成功");
        } catch (Exception e) {
            logUtil.error(UserServiceImpl.class, "删除学校失败", e);
            return ResponseMessage.error("删除学校失败");
        }
    }

    @Override
    public ResponseMessage<Object> addSchool(SchoolDto schoolDto) {
        try {
            School school = new School();
            BeanUtils.copyProperties(schoolDto, school);
            schoolRepository.save(school);
            return ResponseMessage.success("添加学校成功");
        } catch (Exception e) {
            logUtil.error(SchoolServiceImpl.class, "添加学校失败", e);
            return ResponseMessage.error("添加学校失败");
        }
    }
}
