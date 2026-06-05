package com.aicodereviewer.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponseDTO {

    private Long totalFiles;

    private Long totalReports;

    private Double averageQuality;

    private Double averageComplexity;

    private Long totalIssues;
}