package com.aicodereviewer.controller;

import com.aicodereviewer.dto.AnalysisResponseDTO;
import com.aicodereviewer.service.AnalysisService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AnalysisController {

    private final AnalysisService analysisService;

    @PostMapping("/{fileId}")
    public ResponseEntity<AnalysisResponseDTO> analyzeFile(
            @PathVariable Long fileId) {

        AnalysisResponseDTO response =
                analysisService.analyze(fileId);

        return ResponseEntity.ok(response);
    }
}