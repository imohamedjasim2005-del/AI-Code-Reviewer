package com.aicodereviewer.service;

import com.aicodereviewer.config.ApplicationProperties;
import com.aicodereviewer.exception.AnalysisException;
import com.aicodereviewer.exception.ResourceNotFoundException;
import com.aicodereviewer.model.UploadedFile;
import com.aicodereviewer.repository.UploadedFileRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final UploadedFileRepository repository;
    private final ApplicationProperties properties;

    @Override
    public UploadedFile uploadFile(MultipartFile file) {

        try {

            Path uploadDir =
                    Paths.get(properties.getDirectory());

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String fileName =
                    file.getOriginalFilename();

            Path destination =
                    uploadDir.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );

            UploadedFile uploadedFile =
                    UploadedFile.builder()
                            .fileName(fileName)
                            .filePath(destination.toString())
                            .language("JAVA")
                            .uploadTime(LocalDateTime.now())
                            .build();

            return repository.save(uploadedFile);

        } catch (Exception ex) {

            throw new AnalysisException(
                    "Failed to upload file"
            );
        }
    }

    @Override
    public UploadedFile getFile(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "File not found: " + id
                        ));
    }
}