package com.aselsan.VendingMachine.Exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponseDto {
    private String message;
    private String error;
    private String path;
    private LocalDateTime timestamp;
}
