package com.aicodereviewer.service;

import com.aicodereviewer.dto.AnalysisResponseDTO;

public interface AnalysisService {

    AnalysisResponseDTO analyze(Long fileId);
}