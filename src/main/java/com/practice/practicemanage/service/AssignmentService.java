package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.Assignment;
import com.practice.practicemanage.pojo.dto.AssignmentDto;
import com.practice.practicemanage.pojo.dto.PhoneDto;
import com.practice.practicemanage.repository.AssignmentRepository;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.IAssignmentService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AssignmentService implements IAssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private LogUtil logUtil;

    @Override
    public List<Assignment> findAssByPhone(String teacher, String phone, String studentPhone, byte status) {
        try {
            if (status == 1) {
                // 1. 获取状态为1和3的作业
                List<Assignment> byTeacherPhoneAndStatus1 = assignmentRepository.findByTeacherPhoneAndStatus(phone, (byte) 1);
                List<Assignment> byTeacherPhoneAndStatus3 = assignmentRepository.findByTeacherPhoneAndStatus(phone, (byte) 3);

                // 2. 获取 byTeacherPhoneAndStatus3 中所有的 title 并存入 Set 中
                Set<String> titlesFromStatus3 = new HashSet<>();
                for (Assignment assignment : byTeacherPhoneAndStatus3) {
                    titlesFromStatus3.add(assignment.getTitle());  // 假设 getTitle() 返回的是 String 类型
                }

                // 3. 创建一个新的列表来存储去除重复 title 且 status 不同的作业
                List<Assignment> filteredAssignments = new ArrayList<>();
                for (Assignment assignment : byTeacherPhoneAndStatus1) {
                    // 判断 title 是否在 byTeacherPhoneAndStatus3 中出现且 status 是否不同
                    for (Assignment assignmentStatus3 : byTeacherPhoneAndStatus3) {
                        if (assignment.getTitle().equals(assignmentStatus3.getTitle()) && !assignment.getStatus().equals(assignmentStatus3.getStatus())) {
                            assignment.setTeacger(teacher);  // 这里设置教师手机号
                            filteredAssignments.add(assignment);
                        }
                    }
                }

                // 4. 返回去重后的列表
                return filteredAssignments;


            } else if (status == 3) {
                List<Assignment> assignmentList = assignmentRepository.findByStudentPhoneAndStatus(studentPhone, status);
                for (Assignment assignment : assignmentList) {
                        assignment.setTeacger(teacher);  // 这里设置教师
                }
                return assignmentList;
            } else {
                // 如果 status 既不是 1 也不是 3，输出日志
                logUtil.info(AssignmentService.class, "status既不是1也不是3");
                return null;
            }
        } catch (Exception e) {
            // 捕获异常并记录详细日志
            logUtil.error(AssignmentService.class, "查询作业失败", e); // 记录完整异常信息
            return null;
        }
    }

    @Override
    public ResponseMessage<Object> savaAssignment(AssignmentDto assignmentDto) {
        try {
            Assignment assignment = new Assignment();
            BeanUtils.copyProperties(assignmentDto, assignment);
            assignment.setId(null);
            assignmentRepository.save(assignment);
            return ResponseMessage.success("已完成作业");
        } catch (Exception e) {
            logUtil.error(AssignmentService.class, "保存失败", e);
            return ResponseMessage.error("提交作业失败");
        }
    }

    @Override
    public ResponseMessage<Object> deleteAssignmentById(Integer id) {
        try {
//            System.out.println("传递的id-》》》》》》》》》"+id);
            assignmentRepository.updateStatusById(id, (byte) 4);
//            System.out.println("执行修改-》》》》》》》》》"+id);
            return ResponseMessage.success("删除成功");
        } catch (Exception e) {
            logUtil.error(AssignmentService.class, "删除失败", e);
            return ResponseMessage.error("删除失败");
        }
    }

}
