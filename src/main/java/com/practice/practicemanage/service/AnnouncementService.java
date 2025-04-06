package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.dto.AnnouncementDto;
import com.practice.practicemanage.response.ResponseMessage;

public interface AnnouncementService {
    ResponseMessage<Object> savaAnnou(AnnouncementDto announcementDto);

    ResponseMessage<Object> getRoleAnnouncementList(String phone, Integer page, Integer limit);

    ResponseMessage<Object> findById(Integer id);
}
