package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.Announcement;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    Page<Announcement> findByPublisherAndStatusNot(@Size(max = 20) @NotNull String publisher, Integer status, Pageable pageable);

//    List<Announcement> findByPublisher(@Size(max = 20) @NotNull String publisher);

//    List<Announcement> findByPublisherIn(Collection<String> publishers);

    List<Announcement> findByStatus(Integer status);

    List<Announcement> findByPublisherAndStatus(@Size(max = 20) @NotNull String publisher, Integer status);

//    List<Announcement> findByPublisherInAndStatusNot(Collection<String> publishers, Integer status);

//    List<Announcement> findByPublisherInAndStatusNotIn(Collection<String> publishers, Collection<Integer> statuses);

    List<Announcement> findByPublisherInAndStatus(Collection<String> publishers, Integer status);
}