package com.example.gochillge.domain.report.repo;

import com.example.gochillge.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
