package com.aicodereviewer.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDTO {

    private Boolean success;

    private String message;
}