package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.Assignment;
import com.practice.practicemanage.pojo.StudentInfo;
import com.practice.practicemanage.pojo.TeacherInfo;
import com.practice.practicemanage.pojo.WeeklyReport;
import com.practice.practicemanage.pojo.dto.AssignmentIdDto;
import com.practice.practicemanage.repository.AssignmentRepository;
import com.practice.practicemanage.repository.StudentInfoRepository;
import com.practice.practicemanage.repository.TeacherInfoRepository;
import com.practice.practicemanage.response.PaginatedResponse;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.IAssignmentService;
import com.practice.practicemanage.utils.LogUtil;
import com.practice.practicemanage.utils.TypeConversionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AssignmentService implements IAssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private TeacherInfoRepository teacherInfoRepository;
    @Autowired
    private StudentInfoRepository studentInfoRepository;
    @Autowired
    private TypeConversionUtil typeConversionUtil;
    @Autowired
    private LogUtil logUtil;

    @Override
    public List<Assignment> findAssByPhone(String teacher, String phone, String studentPhone, byte status) {
        try {
            if (status == 1) {
// 1. 获取状态为1（未完成）且不在学生已完成作业的标题列表中的作业
                List<Assignment> byTeacherPhoneAndStatus1 = assignmentRepository.findByTeacherPhoneAndStatus(phone, (byte) 1); // 获取老师发布的所有作业
                List<Assignment> byStudentPhoneAndStatus3 = assignmentRepository.findByStudentPhoneAndStatus(studentPhone, (byte) 3); // 获取学生已完成的所有作业

                System.out.println("byTeacherPhoneAndStatus1:"+byTeacherPhoneAndStatus1);
                System.out.println("byStudentPhoneAndStatus3:"+byStudentPhoneAndStatus3);
// 2. 获取 byStudentPhoneAndStatus3 中所有的 title 并存入 Set 中
                Set<String> completedTitles = byStudentPhoneAndStatus3.stream()
                        .map(Assignment::getTitle) // 获取作业的 title
                        .collect(Collectors.toSet()); // 存入 Set 去重

// 3. 筛选出教师发布且标题不在已完成作业列表中的作业
                List<Assignment> filteredAssignments = byTeacherPhoneAndStatus1.stream()
                        .filter(assignment -> !completedTitles.contains(assignment.getTitle())) // 排除已完成的作业
                        .peek(assignment -> assignment.setTeacger(teacher)) // 设置教师姓名
                        .collect(Collectors.toList()); // 将结果收集到新的列表
                System.out.println("filteredAssignments:"+filteredAssignments);

// 4. 返回去除已完成作业标题且状态未完成的作业列表
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
    public ResponseMessage<Object> savaAssignment(AssignmentIdDto assignmentDto) {
        try {
            if (assignmentDto.getId() == null) {
                Assignment assignment = new Assignment();
                BeanUtils.copyProperties(assignmentDto, assignment);
                assignment.setId(null);
                assignmentRepository.save(assignment);
                return ResponseMessage.success("已完成作业/修改作业");
            } else {
                Assignment assignment = new Assignment();
                BeanUtils.copyProperties(assignmentDto, assignment);
                assignmentRepository.save(assignment);
                return ResponseMessage.success("已批改作业/发布作业");
            }

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

    @Override
    public List<Assignment> findByTeacherList(String phone) {
        try {
//            List<Byte> status = new ArrayList<>();
//            status.add((byte) 0);
//            status.add((byte) 1);
            List<Assignment> assignmentList0 = assignmentRepository.findByTeacherPhoneAndStatus(phone, (byte) 0); // 获取作业表
            List<Assignment> assignmentList1 = assignmentRepository.findByTeacherPhoneAndStatus(phone, (byte) 1); // 获取作业表
            List<Assignment> assignmentList = Stream.concat(assignmentList0.stream(), assignmentList1.stream())
                                                    .collect(Collectors.toList());
            TeacherInfo teacherInfo = teacherInfoRepository.findByPhone(phone);// 获取教师个人信息
            for (Assignment assignment : assignmentList){
                assignment.setTeacger(teacherInfo.getName());
            }
            return assignmentList;
        } catch (Exception e) {
            logUtil.error(AssignmentService.class, "获取老师发布的作业失败", e);
            return null;
        }
    }

    @Override
    public ResponseMessage<Object> getFinishListMyAssignment(Integer id) {
        try {
            Optional<Assignment> assList = assignmentRepository.findById(id);
            if (assList.isPresent()) {
                Assignment assignment = assList.get();
                List<Assignment> finsAssignment = assignmentRepository.findByTitleAndStudentPhoneNotNullAndStatus(assignment.getTitle(), (byte) 3);
                for (Assignment assignments : finsAssignment) {
                    StudentInfo studentInfo = (StudentInfo) studentInfoRepository.findByPhone(assignments.getStudentPhone());
                    assignments.setStudentNumber(studentInfo.getStudentNumber());
                    assignments.setName(studentInfo.getName());
                    if (assignments.getPuttimes() != null) {
                        assignments.setPuttimes(Instant.ofEpochSecond(assignments.getPuttimes().toEpochMilli()));  // 转换为时间戳（毫秒）
                    }
                }
                return ResponseMessage.success("获取指定作业完成人员名单", finsAssignment);
            } else {
                return ResponseMessage.success("获取指定作业完成人员名单失败");
            }
        } catch (Exception e) {
            logUtil.error(AssignmentService.class, "获取老师发布的作业失败", e);
            return ResponseMessage.success("获取指定作业完成人员名单失败");
        }

    }

    @Override
    public ResponseMessage<Object> listReleaseAssignment(String phone, String role, Integer page, Integer limit, Byte status) {
        try {
            // 创建分页对象
            Pageable pageable = PageRequest.of(page - 1, limit);

            // 获取状态为1和0的数据
            Page<Assignment> combinedPage = assignmentRepository.findByTeacherPhoneAndStatus(phone, status, pageable);
//            Page<Assignment> byTeacherPhoneAndStatus0 = assignmentRepository.findByTeacherPhoneAndStatus(phone, (byte) 0, pageable);
//
//            // 合并两页数据
//            List<Assignment> combinedAssignments = new ArrayList<>();
//            combinedAssignments.addAll(byTeacherPhoneAndStatus1.getContent());
//            combinedAssignments.addAll(byTeacherPhoneAndStatus0.getContent());
//
//            // 创建新的分页对象
//            long totalElements = byTeacherPhoneAndStatus1.getTotalElements() + byTeacherPhoneAndStatus0.getTotalElements();
//            Page<Assignment> combinedPage = new PageImpl<>(combinedAssignments, pageable, totalElements);

            // 返回结果
            return returnPage(combinedPage, phone);
        } catch (Exception e) {
            // 捕获异常并记录日志
            logUtil.error(WeeklyReport.class, "查询失败", e);
            return ResponseMessage.error("查询失败");  // 使用 error 而不是 success
        }
    }

    @Override
    public ResponseMessage<Object> updateAssignmentStatus(Integer id, Byte status) {
        try {
            assignmentRepository.updateStatusById(id, status);
            return ResponseMessage.success("修改状态成功");
        } catch (Exception e) {
            logUtil.error(WeeklyReport.class, "修改状态失败", e);
            return ResponseMessage.success("修改状态失败");
        }
    }

    public ResponseMessage<Object> returnPage(Page<Assignment> assignmentPage, String phone) {
        List<Assignment> assignmentList = assignmentPage.getContent();
        TeacherInfo teacherInfo = teacherInfoRepository.findByPhone(phone);

        // 如果没有找到教师信息，抛出异常或返回错误
        if (teacherInfo == null) {
            return ResponseMessage.error("教师信息未找到");
        }

        // 将查询结果中的时间戳转换为 Instant 类型
        for (Assignment assignment : assignmentList) {
            if (assignment.getPuttimes() != null) {
                assignment.setPuttimes(Instant.ofEpochMilli(assignment.getPuttimes().toEpochMilli()));  // 正确的时间戳转换
            }
            assignment.setName(teacherInfo.getName());
        }

        // 获取总记录数和总页数
        long totalElements = assignmentPage.getTotalElements();  // 总记录数
        int totalPages = assignmentPage.getTotalPages();  // 总页数

        // 返回分页结果和相关信息
        return ResponseMessage.success("周志获取成功",
                new PaginatedResponse<>(assignmentList, totalElements, totalPages));
    }
}
