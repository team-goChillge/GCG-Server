package com.example.gochillge.domain.report.dto.req;

import com.example.gochillge.domain.post.dto.req.PostReq;
import com.example.gochillge.domain.post.entity.Post;
import com.example.gochillge.domain.report.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportReq {

    private String content;

    public static List<ReportReq> of(List<Report> req) {
        return req.parallelStream()
                .map(ReportReq::of)
                .toList();
    }

    public static ReportReq of(Report req) {
        return new ReportReq(
                req.getContent()
        );
    }

    public Report toEntity() {
        return Report.builder()
                .content(getContent())
                .build();
    }
}
