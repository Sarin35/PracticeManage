package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.InternshipGrade;
import com.practice.practicemanage.pojo.StudentInfo;
import com.practice.practicemanage.pojo.TeacherInfo;
import com.practice.practicemanage.pojo.UnitUser;
import com.practice.practicemanage.pojo.dto.InternshipGradeDto;
import com.practice.practicemanage.repository.InternshipGradeRepository;
import com.practice.practicemanage.repository.StudentInfoRepository;
import com.practice.practicemanage.repository.TeacherInfoRepository;
import com.practice.practicemanage.repository.UnitUserRepository;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.IInternshipGradeService;
import com.practice.practicemanage.utils.LogUtil;
import com.practice.practicemanage.utils.TypeConversionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    private TypeConversionUtil typeConversionUtil;

    @Override
    public ResponseMessage<Object> getAllInfo(String phone) {
        try {
//            查询学生个人信息表
            StudentInfo studentInfo = (StudentInfo) studentInfoRepository.findByPhone(phone);
//            用老师手机号查询老师信息表
            TeacherInfo teacherInfo = teacherInfoRepository.findByPhone(studentInfo.getTeacherPhone());
//            用学生手机号查询学生成绩
            InternshipGrade internshipGrade = (InternshipGrade) internshipGradeRepository.findByStudentPhone(phone);
//            用公司名查询指导老师个人信息
            UnitUser unitUsers = (UnitUser) unitUserRepository.findByPhone(studentInfo.getUnitPhone());

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

    @Override
    public ResponseMessage<Object> getTeacherinfo(String phone) {
        try {
            // 查询老师信息
            TeacherInfo teacherInfo = teacherInfoRepository.findByPhone(phone);
            if (teacherInfo == null) {
                return ResponseMessage.error("未找到该老师信息");
            }

            // 查询该老师的所有学生
            List<StudentInfo> studentInfoList = studentInfoRepository.findByTeacherPhone(phone);
            if (studentInfoList.isEmpty()) {
                return ResponseMessage.error("该老师没有对应的学生");
            }

            // 存储学生信息及相关数据
            List<Map<String, Object>> studentDataList = new ArrayList<>();

            // 遍历学生列表
            for (StudentInfo studentInfo : studentInfoList) {
                Map<String, Object> studentData = new HashMap<>();
                studentData.put("studentInfo", studentInfo);

                // 查询该学生的成绩
                InternshipGrade internshipGrade = (InternshipGrade) internshipGradeRepository.findByStudentPhone(studentInfo.getPhone());
                studentData.put("internshipGrade", internshipGrade);

                // 查询该学生对应的指导老师
                UnitUser unitUser = (UnitUser) unitUserRepository.findByPhone(studentInfo.getUnitPhone());
                studentData.put("unitUser", unitUser);

                // 加入到列表
                studentDataList.add(studentData);
            }

            // 组装返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("teacherInfo", teacherInfo);
            result.put("students", studentDataList);

            return ResponseMessage.success("查询成功", result);
        } catch (Exception e) {
            logUtil.error(InternshipGrade.class, "查询失败", e);
            return ResponseMessage.error("查询失败");
        }
    }

    @Override
    public ResponseMessage<Object> savaGrade(InternshipGradeDto internshipGradeDto) {
        try {
            InternshipGrade internshipGrade = new InternshipGrade();
            BeanUtils.copyProperties(internshipGradeDto, internshipGrade);
            internshipGradeRepository.save(internshipGrade);
            return ResponseMessage.success("保存成绩成功");
        } catch (Exception e) {
            logUtil.error(InternshipGradeService.class, "保存成绩失败", e);
            return ResponseMessage.error("保存成绩失败");
        }
    }

    @Override
    public ResponseMessage<Object> getUnitInfo(String phone) {
        try {
            UnitUser unitUser = unitUserRepository.findByPhone(phone);// 通过公司用户手机号获取公司名称

            if (unitUser == null) {
                return ResponseMessage.error("获取公司实习人员名单失败(查询不到公司信息)");
            }
//            通过公司用户手机号查找公司用户（学生）
            List<StudentInfo> studentInfoList = studentInfoRepository.findByUnitPhone((unitUser.getPhone()));
//            查找学生老师信息

            if (studentInfoList == null) {
                return ResponseMessage.error("获取公司实习人员名单失败(查询不到公司学生)");
            }

            // 存储学生信息及相关数据
            List<Map<String, Object>> studentDataList = new ArrayList<>();

            System.out.println("公司"+unitUser);
            System.out.println("学生"+studentInfoList);
            // 遍历学生列表
            for (StudentInfo studentInfo : studentInfoList) {
                Map<String, Object> studentData = new HashMap<>();
                studentData.put("studentInfo", studentInfo);

                // 查询该学生的成绩
                InternshipGrade internshipGrade = (InternshipGrade) internshipGradeRepository.findByStudentPhone(studentInfo.getPhone());
                studentData.put("internshipGrade", internshipGrade);

//                查询该学生老师信息
                TeacherInfo teacherInfo = teacherInfoRepository.findByPhone(studentInfo.getTeacherPhone());
                studentData.put("teacherInfo", teacherInfo);

                // 查询该学生对应的指导老师
//                UnitUser unitUser = (UnitUser) unitUserRepository.findByName(studentInfo.getUnitName());
                studentData.put("unitUser", unitUser);

                System.out.println("该学生信息：" + studentData);

                // 加入到列表
                studentDataList.add(studentData);
            }

            return ResponseMessage.success("获取公司实习人员名单", studentDataList);

        } catch (Exception e) {
            logUtil.error(InternshipGrade.class, "获取公司实习人员名单失败", e);
            return ResponseMessage.error("获取公司实习人员名单失败");
        }
    }
}
