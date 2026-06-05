package com.aicodereviewer.service;

import com.aicodereviewer.analyzer.*;
import com.aicodereviewer.dto.AnalysisResponseDTO;
import com.aicodereviewer.exception.ResourceNotFoundException;
import com.aicodereviewer.model.AnalysisReport;
import com.aicodereviewer.model.UploadedFile;
import com.aicodereviewer.repository.AnalysisReportRepository;
import com.aicodereviewer.repository.UploadedFileRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnalysisServiceImpl
        implements AnalysisService {

    private final UploadedFileRepository fileRepository;

    private final AnalysisReportRepository reportRepository;

    @Override
    public AnalysisResponseDTO analyze(Long fileId) {

        try {

            UploadedFile file =
                    fileRepository.findById(fileId)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "File not found"
                                    ));

            String code =
                    Files.readString(
                            Path.of(file.getFilePath())
                    );

            String complexityResult =
                    AnalyzerFactory
                            .getAnalyzer("complexity")
                            .analyze(code);

            String namingResult =
                    AnalyzerFactory
                            .getAnalyzer("naming")
                            .analyze(code);

            String duplicateResult =
                    AnalyzerFactory
                            .getAnalyzer("duplicate")
                            .analyze(code);

            String longMethodResult =
                    AnalyzerFactory
                            .getAnalyzer("longmethod")
                            .analyze(code);

            String documentationResult =
                    AnalyzerFactory
                            .getAnalyzer("documentation")
                            .analyze(code);

            int complexity =
                    Integer.parseInt(complexityResult);

            int duplicateLines =
                    extractDuplicateCount(
                            duplicateResult
                    );

            boolean namingIssue =
                    !namingResult.isEmpty();

            boolean longMethodIssue =
                    !longMethodResult.isEmpty();

            int qualityScore =
                    new QualityScoreCalculator()
                            .calculate(
                                    complexity,
                                    duplicateLines,
                                    namingIssue,
                                    longMethodIssue
                            );

            String issues =
                    namingResult
                            + longMethodResult
                            + duplicateResult;

            String suggestions =
                    generateSuggestions(
                            namingIssue,
                            longMethodIssue,
                            documentationResult
                    );

            AnalysisReport report =
                    AnalysisReport.builder()
                            .complexityScore(
                                    complexity
                            )
                            .qualityScore(
                                    qualityScore
                            )
                            .issues(
                                    issues
                            )
                            .suggestions(
                                    suggestions
                            )
                            .uploadedFile(
                                    file
                            )
                            .createdAt(
                                    LocalDateTime.now()
                            )
                            .build();

            reportRepository.save(report);

            return AnalysisResponseDTO.builder()
                    .complexityScore(
                            complexity
                    )
                    .qualityScore(
                            qualityScore
                    )
                    .issues(
                            issues
                    )
                    .suggestions(
                            suggestions
                    )
                    .build();

        } catch (Exception ex) {

            throw new RuntimeException(
                    ex.getMessage()
            );
        }
    }

    private int extractDuplicateCount(
            String result) {

        String value =
                result.replace(
                        "Duplicate Lines:",
                        ""
                ).trim();

        return Integer.parseInt(value);
    }

    private String generateSuggestions(
            boolean namingIssue,
            boolean longMethodIssue,
            String documentationResult) {

        StringBuilder suggestions =
                new StringBuilder();

        if (namingIssue) {

            suggestions.append(
                    "Follow Java naming conventions.\n"
            );
        }

        if (longMethodIssue) {

            suggestions.append(
                    "Break long methods into smaller methods.\n"
            );
        }

        if (documentationResult.contains(
                "No documentation"
        )) {

            suggestions.append(
                    "Add comments and JavaDocs.\n"
            );
        }

        return suggestions.toString();
    }
}