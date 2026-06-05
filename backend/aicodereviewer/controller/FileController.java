package com.aicodereviewer.controller;

import com.aicodereviewer.model.UploadedFile;
import com.aicodereviewer.service.FileService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<UploadedFile> uploadFile(
            @RequestParam("file")
            MultipartFile file) {

        UploadedFile uploadedFile =
                fileService.uploadFile(file);

        return ResponseEntity.ok(uploadedFile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UploadedFile> getFile(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                fileService.getFile(id)
        );
    }
}