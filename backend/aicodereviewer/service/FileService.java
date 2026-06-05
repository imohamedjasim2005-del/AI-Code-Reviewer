package com.aicodereviewer.service;

import com.aicodereviewer.model.UploadedFile;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    UploadedFile uploadFile(MultipartFile file);

    UploadedFile getFile(Long id);
}