package com.example.gochillge.domain.notice.service;

import com.example.gochillge.domain.notice.dto.req.NoticeReq;
import com.example.gochillge.domain.notice.dto.res.NoticeRes;
import com.example.gochillge.domain.notice.entity.Notice;
import com.example.gochillge.domain.notice.repo.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<NoticeRes> get() {
        return noticeRepository.findAll().stream()
                .map(NoticeRes :: fromEntity)
                .collect(Collectors.toList());
    }

    public void post(NoticeReq noticeReq) {
        Notice notice = noticeReq.toEntity();
        noticeRepository.save(notice);
    }

    public void update(Long id, NoticeReq noticeReq) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found"));
        notice.patch(noticeReq.toEntity());
    }

    public void delete(Long id) {
        noticeRepository.deleteById(id);
    }

    public NoticeRes getPost(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return NoticeRes.of(notice);
    }
}
