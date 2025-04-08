package com.xische.demo.controller.exceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.xische.demo.exception.CurrencyExchangeException;

/**
 * The type Global exception handler.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handle currency exchange exception response entity.
   *
   * @param ex the ex
   * @return the response entity
   */
  @ExceptionHandler(CurrencyExchangeException.class)
  public ResponseEntity<Map<String, Object>> handleCurrencyExchangeException(CurrencyExchangeException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Map.of("timestamp",
            LocalDateTime.now(),
            "status",
            HttpStatus.BAD_REQUEST.value(),
            "error",
            "Currency Exchange Error",
            "message",
            ex.getMessage()));
  }

  /**
   * Handle general exception response entity.
   *
   * @param ex the ex
   * @return the response entity
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Map.of("timestamp",
            LocalDateTime.now(),
            "status",
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "error",
            "Internal Server Error",
            "message",
            ex.getMessage()));
  }

  /**
   * Handle validation exception response entity.
   *
   * @param ex the ex
   * @return the response entity
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
    Map<String, String> fieldErrors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .collect(java.util.stream.Collectors.toMap(FieldError::getField,
            FieldError::getDefaultMessage,
            (existing, replacement) -> existing));

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Map.of("timestamp",
            LocalDateTime.now(),
            "status",
            HttpStatus.BAD_REQUEST.value(),
            "error",
            "Validation Failed",
            "messages",
            fieldErrors));
  }
}

