package com.aicodereviewer.repository;

import com.aicodereviewer.model.AnalysisReport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisReportRepository
        extends JpaRepository<AnalysisReport, Long> {

    @Query("""
           SELECT AVG(a.qualityScore)
           FROM AnalysisReport a
           """)
    Double getAverageQualityScore();

    @Query("""
           SELECT AVG(a.complexityScore)
           FROM AnalysisReport a
           """)
    Double getAverageComplexityScore();

    @Query("""
           SELECT COUNT(a)
           FROM AnalysisReport a
           """)
    Long getTotalReports();
}