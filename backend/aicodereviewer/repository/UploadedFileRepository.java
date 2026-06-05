package com.aicodereviewer.repository;

import com.aicodereviewer.model.UploadedFile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UploadedFileRepository
        extends JpaRepository<UploadedFile, Long> {

    Optional<UploadedFile> findByFileName(
            String fileName
    );

    boolean existsByFileName(
            String fileName
    );

}