package com.example.app_bd_backend.util;

import com.example.app_bd_backend.data.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static <T> ResponseEntity<ApiResponse<T>> success(String message, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        return new ResponseEntity<>(response, HttpStatus.OK);  // ← 명시적 생성
    }

    // 성공 응답 (데이터 없음)
    public static <T> ResponseEntity<ApiResponse<T>> success(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 에러 응답
    public static <T> ResponseEntity<ApiResponse<T>> error(String error, HttpStatus status) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setError(error);
        return new ResponseEntity<>(response, status);
    }

    // 400 Bad Request
    public static <T> ResponseEntity<ApiResponse<T>> badRequest(String error) {
        return error(error, HttpStatus.BAD_REQUEST);
    }

    // 404 Not Found
    public static <T> ResponseEntity<ApiResponse<T>> notFound(String error) {
        return error(error, HttpStatus.NOT_FOUND);
    }

    // 500 Internal Server Error
    public static <T> ResponseEntity<ApiResponse<T>> internalError(String error) {
        return error(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}