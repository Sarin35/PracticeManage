package com.practice.practicemanage.service.impl;

import com.practice.practicemanage.pojo.Announcement;
import com.practice.practicemanage.pojo.dto.AnnouncementDto;
import com.practice.practicemanage.repository.AnnouncementRepository;
import com.practice.practicemanage.response.PaginatedResponse;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.AnnouncementService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private LogUtil logUtil;

    @Override
    public ResponseMessage<Object> savaAnnou(AnnouncementDto announcementDto) {
        try {
            Announcement announcement = new Announcement();
            System.out.println("announcementDto->>>>>>>>>>"+announcementDto);
            BeanUtils.copyProperties(announcementDto, announcement);
            announcementRepository.save(announcement);
            if (announcementDto.getId() == null) {
                return ResponseMessage.success("保存公告成功");
            } else {
                return ResponseMessage.success("修改公告成功");
            }
        } catch (Exception e) {
            logUtil.error(AnnouncementServiceImpl.class, "公告操作失败", e);
            return ResponseMessage.error("公告操作失败");
        }
    }

    @Override
    public ResponseMessage<Object> getRoleAnnouncementList(String phone, Integer page, Integer limit) {
        try {
            Pageable pageable = PageRequest.of(page - 1, limit);
            Page<Announcement> all = announcementRepository.findByPublisherAndStatusNot(phone, 0,  pageable);
            if (all.isEmpty()){
                return ResponseMessage.error("获取公告失败");
            }
            return returnPage(all);
        } catch (Exception e) {
            logUtil.error(AnnouncementServiceImpl.class, "获取公告失败", e);
            return ResponseMessage.error("获取公告失败");
        }
    }

    @Override
    public ResponseMessage<Object> findById(Integer id) {
        try {
            Optional<Announcement> announcement = announcementRepository.findById(Long.valueOf(id));
            if (announcement.isEmpty()) {
                return ResponseMessage.error("获取公告失败");
            }
            return ResponseMessage.success("获取公告成功", announcement);
        } catch (Exception e) {
            logUtil.error(AnnouncementServiceImpl.class, "获取公告失败", e);
            return ResponseMessage.error("获取公告失败");
        }
    }

    public ResponseMessage<Object> returnPage (Page<Announcement> announcements) {
        List<Announcement> announcements1 = announcements.getContent();

        // 将查询结果中的时间戳转换
        for (Announcement weeklyReport : announcements1) {
            if (weeklyReport.getCreateTime() != null) {
                weeklyReport.setCreateTime(Instant.ofEpochSecond(weeklyReport.getCreateTime().toEpochMilli()));  // 转换为时间戳（毫秒）
            }
            if (weeklyReport.getUpdateTime() != null) {
                weeklyReport.setUpdateTime(Instant.ofEpochSecond(weeklyReport.getUpdateTime().toEpochMilli()));  // 转换为时间戳（毫秒）
            }
        }

        // 获取总记录数和总页数
        long totalElements = announcements.getTotalElements();  // 总记录数
        int totalPages = announcements.getTotalPages();  // 总页数

        // 返回分页结果和相关信息
        return ResponseMessage.success("公告获取成功",
                new PaginatedResponse<>(announcements1, totalElements, totalPages));
    }
}
