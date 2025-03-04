package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.*;
import com.practice.practicemanage.pojo.dto.InternshipGuidanceDto;
import com.practice.practicemanage.pojo.dto.InternshipGuidanceIdDto;
import com.practice.practicemanage.repository.InternshipGuidanceRepository;
import com.practice.practicemanage.repository.StudentInfoRepository;
import com.practice.practicemanage.repository.TeacherInfoRepository;
import com.practice.practicemanage.repository.UnitUserRepository;
import com.practice.practicemanage.response.PaginatedResponse;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.IInternshipGuidanceService;
import com.practice.practicemanage.utils.LogUtil;
import com.sun.java.accessibility.util.GUIInitializedListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InternshipGuidanceService implements IInternshipGuidanceService {

    @Autowired
    private InternshipGuidanceRepository internshipGuidanceRepository;
    @Autowired
    private UnitUserRepository unitUserRepository;
    @Autowired
    private StudentInfoRepository studentInfoRepository;
    @Autowired
    private LogUtil logUtil;
    @Autowired
    private TeacherInfoRepository teacherInfoRepository;


    @Override
    public ResponseMessage<Object> getGuidanceList(String phone) {
//        根据公司人员手机号，查找该公司公司名，通过公司名查找该公司的所有指导人和所有实习人员
        try {
            UnitUser unitUser = unitUserRepository.findByPhone(phone);
            if (unitUser == null) {
                return ResponseMessage.error("该手机号无正在使用人员，请检查手机号");
            }
//          获取该公司的所有实习人员
            List<StudentInfo> studentInfos = studentInfoRepository.findByUnitName(unitUser.getName());
//            获取该公司的所有指导人
            List<UnitUser> unit = unitUserRepository.findByName(unitUser.getName());
            if (studentInfos == null || unit == null) {
                return ResponseMessage.error("该手机号无绑定对象，请检查绑定对象");
            }
            Map<String, Object> map = new HashMap<>();
            map.put("studentInfos", studentInfos);
            map.put("unitUser", unit);
            return ResponseMessage.success("获取成功", map);
        } catch (Exception e) {
            logUtil.error(GUIInitializedListener.class, "根据公司人员手机号，查找该公司公司名，通过公司名查找该公司的所有指导人和所有实习人员出错", e);
            return ResponseMessage.error("获取列表失败");
        }
    }

    @Override
    public ResponseMessage<Object> findTeacherByStudent(String phone) {
        try {
            //        选择实习人员后，查找她的负责人员和负责教师以及单位和学校 （单位可默认）
            StudentInfo studentInfo = (StudentInfo) studentInfoRepository.findByPhone(phone);
            if (studentInfo == null) {
                return ResponseMessage.error("该手机号无绑定对象，请检查绑定对象");
            }
            UnitUser unitUser = unitUserRepository.findByPhone(studentInfo.getUnitPhone());
            TeacherInfo teacherInfo = teacherInfoRepository.findByPhone(studentInfo.getTeacherPhone());
            if (unitUser == null || teacherInfo == null) {
                return ResponseMessage.error("该手机号无绑定对象，请检查绑定对象");
            }
            Map<String, Object> map = new HashMap<>();
            map.put("studentInfo", studentInfo);
            map.put("unitUser", unitUser);
            map.put("teacherInfo", teacherInfo);
            return ResponseMessage.success("获取成功", map);
        } catch (Exception e) {
            logUtil.error(GUIInitializedListener.class, "选择实习人员出错", e);
            return ResponseMessage.error("选择实习人员出错");
        }
    }

    @Override
    public ResponseMessage<Object> putList(InternshipGuidanceDto internshipGuidanceDto) {
        try {
            InternshipGuidance internshipGuidance = new InternshipGuidance();
            BeanUtils.copyProperties(internshipGuidanceDto, internshipGuidance);
            internshipGuidanceRepository.save(internshipGuidance);
            return ResponseMessage.success("提交成功");
        } catch (Exception e) {
            logUtil.error(GUIInitializedListener.class, "提交出错", e);
            return ResponseMessage.error("提交出错");
        }
    }

    @Override
    public ResponseMessage<Object> getGuidanceLists(String phone, Integer page, Integer limit, Integer status) {
        try {
            if (status == 0) {
                UnitUser uni = unitUserRepository.findByPhone(phone);
                // 创建 Pageable 对象，PageRequest.of() 用于设置分页参数，page 从0开始，limit是每页大小
                Pageable pageable = PageRequest.of(page - 1, limit);
                Page<InternshipGuidance> guidance = internshipGuidanceRepository.findByUnitNameAndStatusNot(uni.getName(), pageable, status);

                return  returnPage(guidance);
            } else {
                UnitUser uni = unitUserRepository.findByPhone(phone);
                // 创建 Pageable 对象，PageRequest.of() 用于设置分页参数，page 从0开始，limit是每页大小
                Pageable pageable = PageRequest.of(page - 1, limit);
                Page<InternshipGuidance> guidance = internshipGuidanceRepository.findByUnitNameAndStatus(uni.getName(), pageable, status);

                return  returnPage(guidance);
            }
        } catch (Exception e) {
            logUtil.error(GUIInitializedListener.class, "获取实习指导列表失败", e);
            return ResponseMessage.error("获取实习指导列表失败");
        }
    }

    @Override
    public ResponseMessage<Object> updateStatusPut(Integer id, Integer status) {
        try {
            int i = internshipGuidanceRepository.updateStatusById(id, status);
            if (i == 0) {
                return ResponseMessage.error("更改状态失败");
            }
            return ResponseMessage.success("更改状态成功");
        } catch (Exception e) {
            logUtil.error(GUIInitializedListener.class, "更改状态失败", e);
            return ResponseMessage.error("更改状态失败");
        }
    }

    @Override
    public ResponseMessage<Object> savaCreatequidances(InternshipGuidanceIdDto internshipGuidanceDto) {
        try {
            InternshipGuidance internshipGuidance = new InternshipGuidance();
            BeanUtils.copyProperties(internshipGuidanceDto, internshipGuidance);
            internshipGuidanceRepository.save(internshipGuidance);
            return ResponseMessage.success("保存修改成功");
        } catch (Exception e) {
            logUtil.error(GUIInitializedListener.class, "保存修改失败", e);
            return ResponseMessage.error("保存修改失败");
        }
    }

    public ResponseMessage<Object> returnPage (Page<InternshipGuidance> internshipGuidance) {
        List<InternshipGuidance> internshipGuidances = internshipGuidance.getContent();

        // 将查询结果中的时间戳转换
        for (InternshipGuidance internshipGuidance1 : internshipGuidances) {
            if (internshipGuidance1.getGuidanceTime() != null) {
                internshipGuidance1.setGuidanceTime(Instant.ofEpochSecond(internshipGuidance1.getGuidanceTime().toEpochMilli()));  // 转换为时间戳（毫秒）
            }
        }

        // 获取总记录数和总页数
        long totalElements = internshipGuidance.getTotalElements();  // 总记录数
        int totalPages = internshipGuidance.getTotalPages();  // 总页数

        // 返回分页结果和相关信息
        return ResponseMessage.success("获取实习指导列表成功",
                new PaginatedResponse<>(internshipGuidances, totalElements, totalPages));
    }
}
