package com.example.gochillge.domain.report.controller;

import com.example.gochillge.domain.report.dto.req.ReportReq;
import com.example.gochillge.domain.report.dto.res.ReportRes;
import com.example.gochillge.domain.report.service.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@Tag(name = "report", description = "건의하기 기능")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/get")
    public List<ReportRes> getPosts() { return reportService.get(); }

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@RequestBody ReportReq reportReq) {
        reportService.create(reportReq);
    }

}
