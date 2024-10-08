package com.assignment.general;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.assignment.exceptions.CsvProcessingException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(CsvProcessingException.class)
    public ResponseEntity<Map<String, String>> handleCsvProcessingException(final CsvProcessingException ex, HttpServletRequest request) {
        final Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Return 400 Bad Request
    }
}
