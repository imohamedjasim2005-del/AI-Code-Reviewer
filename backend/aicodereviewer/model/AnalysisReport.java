package com.aicodereviewer.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "analysis_reports")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer complexityScore;

    private Integer qualityScore;

    @Column(length = 5000)
    private String issues;

    @Column(length = 5000)
    private String suggestions;

    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "file_id")
    private UploadedFile uploadedFile;
}