package com.aicodereviewer.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisResponseDTO {

    private Integer complexityScore;

    private Integer qualityScore;

    private String issues;

    private String suggestions;
}