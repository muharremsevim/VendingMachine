package com.aselsan.VendingMachine.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(VendingMachineOutOfOrderException.class)
    public ResponseEntity<ErrorResponseDto> handleMachineNotOperational(
            VendingMachineOutOfOrderException ex, WebRequest request) {
        log.error("Vending Machine is out of order: {}", ex.getMessage());

        ErrorResponseDto error = ErrorResponseDto.builder()
                .message(ex.getMessage())
                .error("Vending Machine Out Of Order")
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(BalanceNotEnoughException.class)
    public ResponseEntity<ErrorResponseDto> handleInsufficientBalance(
            BalanceNotEnoughException ex, WebRequest request) {
        log.error("Insufficient balance: {}", ex.getMessage());

        ErrorResponseDto error = ErrorResponseDto.builder()
                .message(ex.getMessage())
                .error("Insufficient Balance")
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.PAYMENT_REQUIRED);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleProductNotAvailable(
            ProductNotFoundException ex, WebRequest request) {
        log.error("Product not available: {}", ex.getMessage());

        ErrorResponseDto error = ErrorResponseDto.builder()
                .message(ex.getMessage())
                .error("Product Not Available")
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductInstallationException.class)
    public ResponseEntity<ErrorResponseDto> handleProductInstallationError(
            ProductInstallationException ex, WebRequest request) {
        log.error("Product has errors: {}", ex.getMessage());

        ErrorResponseDto error = ErrorResponseDto.builder()
                .message(ex.getMessage())
                .error("Product Has Error")
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VendingMachineNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleMachineNotFound(
            VendingMachineNotFoundException ex, WebRequest request) {
        log.error("Vending Machine not found: {}", ex.getMessage());

        ErrorResponseDto error = ErrorResponseDto.builder()
                .message(ex.getMessage())
                .error("Vending Machine Not Found")
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAllUncaughtException(
            Exception ex, WebRequest request) {
        log.error("Unexpected error occurred", ex);

        ErrorResponseDto error = ErrorResponseDto.builder()
                .message("An unexpected error occurred")
                .error("Internal Server Error")
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}