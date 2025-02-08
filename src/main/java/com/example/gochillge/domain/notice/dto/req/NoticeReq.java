package com.example.gochillge.domain.notice.dto.req;

import com.example.gochillge.domain.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeReq {
    private String title;
    private String content;

    public static List<NoticeReq> of(List<Notice> req) {
        return req.parallelStream()
                .map(NoticeReq::of)
                .toList();
    }

    public static NoticeReq of(Notice notice) {
        return new NoticeReq(
                notice.getTitle(),
                notice.getContent()
        );
    }

    public Notice toEntity() {
        return Notice.builder()
                .title(getTitle())
                .content(getContent())
                .build();
    }
}
