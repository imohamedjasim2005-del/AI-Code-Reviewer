package com.aicodereviewer.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStats {

    private Long totalFiles;

    private Long totalReports;

    private Double averageComplexity;

    private Double averageQuality;

    private Long totalIssues;
}