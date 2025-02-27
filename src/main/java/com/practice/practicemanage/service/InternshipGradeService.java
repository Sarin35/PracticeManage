package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.InternshipGrade;
import com.practice.practicemanage.pojo.StudentInfo;
import com.practice.practicemanage.pojo.TeacherInfo;
import com.practice.practicemanage.pojo.UnitUser;
import com.practice.practicemanage.repository.InternshipGradeRepository;
import com.practice.practicemanage.repository.StudentInfoRepository;
import com.practice.practicemanage.repository.TeacherInfoRepository;
import com.practice.practicemanage.repository.UnitUserRepository;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.IInternshipGradeService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InternshipGradeService implements IInternshipGradeService {

    @Autowired
    private InternshipGradeRepository internshipGradeRepository;
    @Autowired
    private StudentInfoRepository studentInfoRepository;
    @Autowired
    private TeacherInfoRepository teacherInfoRepository;
    @Autowired
    private UnitUserRepository unitUserRepository;
    @Autowired
    private LogUtil logUtil;

    @Override
    public ResponseMessage<Object> getAllInfo(String phone) {
        try {
//            查询学生个人信息表
            StudentInfo studentInfo = (StudentInfo) studentInfoRepository.findByPhone(phone);
//            用老师手机号查询老师信息表
            TeacherInfo teacherInfo = (TeacherInfo) teacherInfoRepository.findByPhone(studentInfo.getTeacherPhone());
//            用学生手机号查询学生成绩
            InternshipGrade internshipGrade = (InternshipGrade) internshipGradeRepository.findByStudentPhone(phone);
//            用公司名查询指导老师个人信息
            UnitUser unitUsers = (UnitUser) unitUserRepository.findByName(studentInfo.getUnitName());

            Map<String, Object> map = new HashMap<>();
            map.put("studentInfo", studentInfo);
            map.put("teacherInfo", teacherInfo);
            map.put("internshipGrade", internshipGrade);
            map.put("unitUsers", unitUsers);
            return ResponseMessage.success("查询实习成绩成功", map);
        } catch (Exception e) {
            logUtil.error(InternshipGrade.class, "查询实习成绩失败", e);
            return ResponseMessage.error("查询实习成绩失败");
        }
    }
}
