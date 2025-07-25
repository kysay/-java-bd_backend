package com.example.app_bd_backend.service;

import com.example.app_bd_backend.data.dto.UserResponseDto;
import com.example.app_bd_backend.data.dto.UserSignupDto;
import com.example.app_bd_backend.data.dto.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<ApiResponse<List<UserResponseDto>>> getAllUsers();

    ResponseEntity<ApiResponse<UserResponseDto>> getUserById(Long id);

    ResponseEntity<ApiResponse<UserResponseDto>> createUser(UserSignupDto userSignupDto);

    ResponseEntity<ApiResponse<UserResponseDto>> updateUser(Long id, UserSignupDto userSignupDto);

    ResponseEntity<ApiResponse<Void>> deleteUser(Long id);
}