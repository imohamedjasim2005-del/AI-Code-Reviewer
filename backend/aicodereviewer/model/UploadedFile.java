package com.aicodereviewer.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "uploaded_files")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String language;

    private Long fileSize;

    private LocalDateTime uploadTime;

    @OneToOne(
            mappedBy = "uploadedFile",
            cascade = CascadeType.ALL
    )
    private AnalysisReport analysisReport;
}