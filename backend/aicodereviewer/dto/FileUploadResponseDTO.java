package com.aicodereviewer.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponseDTO {

    private Long id;

    private String fileName;

    private String language;

    private Long fileSize;
}