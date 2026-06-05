package com.aicodereviewer.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {

    private Boolean success;

    private String message;

    private LocalDateTime timestamp;
}