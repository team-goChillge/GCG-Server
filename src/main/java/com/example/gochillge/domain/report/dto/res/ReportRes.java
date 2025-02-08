package com.example.gochillge.domain.report.dto.res;

import com.example.gochillge.domain.post.dto.res.PostRes;
import com.example.gochillge.domain.post.entity.Post;
import com.example.gochillge.domain.report.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReportRes {
    private Long id;
    private String content;

    public static ReportRes fromEntity(Report report) {
        return new ReportRes(
                report.getId(),
                report.getContent()
        );
    }

    public static List<ReportRes> of(List<Report> req) {
        return req.parallelStream()
                .map(ReportRes::of)
                .toList();
    }

    public static ReportRes of(Report report) {
        return new ReportRes(
                report.getId(),
                report.getContent()
        );
    }
}
