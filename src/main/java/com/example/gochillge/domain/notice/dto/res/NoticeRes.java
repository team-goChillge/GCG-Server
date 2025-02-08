package com.example.gochillge.domain.notice.dto.res;

import com.example.gochillge.domain.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class NoticeRes {
    private Long id;
    private String title;
    private String content;

    public static NoticeRes fromEntity(Notice notice) {
        return new NoticeRes(
                notice.getId(),
                notice.getTitle(),
                notice.getContent()
        );
    }

    public static List<NoticeRes> of(List<Notice> req) {
        return req.parallelStream()
                .map(NoticeRes::of)
                .toList();
    }

    public static NoticeRes of(Notice notice) {
        return new NoticeRes(
                notice.getId(),
                notice.getTitle(),
                notice.getContent()
        );
    }
}
