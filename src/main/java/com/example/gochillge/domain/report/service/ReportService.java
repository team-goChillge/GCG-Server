package com.example.gochillge.domain.report.service;

import com.example.gochillge.domain.report.dto.req.ReportReq;
import com.example.gochillge.domain.report.dto.res.ReportRes;
import com.example.gochillge.domain.report.entity.Report;
import com.example.gochillge.domain.report.repo.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public List<ReportRes> get() {
        return reportRepository.findAll().stream()
                .map(ReportRes:: fromEntity)
                .collect(Collectors.toList());
    }

    public void create(ReportReq reportReq) {
        Report report = reportReq.toEntity();
        reportRepository.save(report);
    }
}
