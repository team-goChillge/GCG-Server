package com.example.gochillge.domain.notice.repo;

import com.example.gochillge.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
