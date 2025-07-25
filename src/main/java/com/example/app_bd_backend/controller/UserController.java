package com.example.app_bd_backend.controller;

import com.example.app_bd_backend.constants.ApiConstants;
import com.example.app_bd_backend.data.dto.UserResponseDto;
import com.example.app_bd_backend.data.dto.UserSignupDto;
import com.example.app_bd_backend.data.dto.ApiResponse;
import com.example.app_bd_backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.API_BASE)
@CrossOrigin(origins = "*")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getAllUsers() {  // ← 타입 수정
        logger.info("사용자 목록 조회 API 호출");
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(@PathVariable Long id) {  // ← 타입 수정
        logger.info("사용자 상세 조회 API 호출: {}", id);
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(@RequestBody UserSignupDto userSignupDto) {  // ← 타입 수정
        logger.info("사용자 생성 API 호출: {}", userSignupDto.getUsername());
        return userService.createUser(userSignupDto);
    }
}