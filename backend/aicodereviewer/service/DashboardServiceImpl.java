package com.aicodereviewer.service;

import com.aicodereviewer.model.AnalysisReport;
import com.aicodereviewer.repository.AnalysisReportRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl
        implements DashboardService {

    private final AnalysisReportRepository repository;

    @Override
    public Map<String, Object> getDashboard() {

        List<AnalysisReport> reports =
                repository.findAll();

        int totalReports =
                reports.size();

        double averageQuality =
                reports.stream()
                        .mapToInt(
                                AnalysisReport::getQualityScore
                        )
                        .average()
                        .orElse(0);

        double averageComplexity =
                reports.stream()
                        .mapToInt(
                                AnalysisReport::getComplexityScore
                        )
                        .average()
                        .orElse(0);

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "totalReports",
                totalReports
        );

        response.put(
                "averageQuality",
                averageQuality
        );

        response.put(
                "averageComplexity",
                averageComplexity
        );

        return response;
    }
}