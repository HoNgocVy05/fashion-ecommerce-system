package com.fashion.ecommerce.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
     //@ExceptionHandler(AccessDeniedException.class)
     //public ResponseEntity<?> handleAccessDenied(
             //AccessDeniedException ex,
             //HttpServletRequest request
     //) {

         //Map<String, Object> res = new HashMap<>();

         //res.put("timestamp", LocalDateTime.now());
         //res.put("status", 403);
         //res.put("error", "Forbidden");
         //res.put("message", "Bạn không có quyền truy cập");
         //res.put("path", request.getRequestURI());

         //return ResponseEntity
                 //.status(HttpStatus.FORBIDDEN)
                 //.body(res);
     //}

    // xử lý lỗi custom
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException ex, HttpServletRequest request) {

        Map<String, Object> res = new HashMap<>();
        res.put("timestamp", LocalDateTime.now());
        res.put("status", ex.getStatus().value());
        res.put("error", ex.getStatus().getReasonPhrase());
        res.put("message", ex.getMessage());
        res.put("path", request.getRequestURI());

        return ResponseEntity
                .status(ex.getStatus())
                .body(res);
    }

    // lỗi validate DTO
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(
            org.springframework.web.bind.MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, Object> res = new HashMap<>();
        res.put("timestamp", LocalDateTime.now());
        res.put("status", 400);
        res.put("error", "Bad Request");
        res.put("path", request.getRequestURI());

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });

        res.put("message", errors);

        return ResponseEntity.badRequest().body(res);
    }

    // lỗi khác
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex, HttpServletRequest request) {

        Map<String, Object> res = new HashMap<>();
        res.put("timestamp", LocalDateTime.now());
        res.put("status", 500);
        res.put("error", "Internal Server Error");
        res.put("message", "Có lỗi xảy ra, vui lòng thử lại sau");
        res.put("path", request.getRequestURI());

        return ResponseEntity.status(500).body(res);
    }
}