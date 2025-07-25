package com.example.app_bd_backend.service.impl;

import com.example.app_bd_backend.dao.UserDao;
import com.example.app_bd_backend.data.dto.UserResponseDto;
import com.example.app_bd_backend.data.dto.UserSignupDto;
import com.example.app_bd_backend.data.dto.ApiResponse;
import com.example.app_bd_backend.data.entity.UserEtt;
import com.example.app_bd_backend.service.UserService;
import com.example.app_bd_backend.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;  // Repository 대신 DAO 사용

    @Override
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getAllUsers() {
        try {
            logger.info("모든 사용자 조회");
            List<UserEtt> users = userDao.findAll();

            // Entity → DTO 변환
            List<UserResponseDto> userDtos = users.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());

            return ResponseUtil.success("사용자 목록 조회 성공", userDtos);
        } catch (Exception e) {
            logger.error("사용자 목록 조회 실패: ", e);
            return ResponseUtil.internalError("사용자 목록 조회에 실패했습니다.");
        }
    }

    @Override
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(Long id) {
        try {
            logger.info("사용자 조회: ID = {}", id);
            Optional<UserEtt> user = userDao.findById(id);

            if (user.isEmpty()) {
                return ResponseUtil.notFound("사용자를 찾을 수 없습니다.");
            }

            UserResponseDto userDto = convertToDto(user.get());
            return ResponseUtil.success("사용자 조회 성공", userDto);
        } catch (Exception e) {
            logger.error("사용자 조회 실패: ", e);
            return ResponseUtil.internalError("사용자 조회에 실패했습니다.");
        }
    }

    @Override
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(UserSignupDto userSignupDto) {
        try {
            logger.info("사용자 생성 시작: {}", userSignupDto.getUsername());

            // 중복 검사
            if (userDao.existsByUsername(userSignupDto.getUsername())) {
                return ResponseUtil.badRequest("이미 존재하는 사용자명입니다.");
            }

            if (userDao.existsByEmail(userSignupDto.getEmail())) {
                return ResponseUtil.badRequest("이미 존재하는 이메일입니다.");
            }

            // DTO → Entity 변환
            UserEtt user = convertToEntity(userSignupDto);

            // 저장
            UserEtt savedUser = userDao.save(user);
            logger.info("사용자 생성 완료: ID = {}", savedUser.getId());

            // Entity → DTO 변환
            UserResponseDto responseDto = convertToDto(savedUser);
            return ResponseUtil.success("사용자 생성 성공", responseDto);

        } catch (Exception e) {
            logger.error("사용자 생성 실패: ", e);
            return ResponseUtil.internalError("사용자 생성에 실패했습니다.");
        }
    }

    @Override
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(Long id, UserSignupDto userSignupDto) {
        // 구현 생략 (비슷한 패턴)
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> deleteUser(Long id) {
        // 구현 생략 (비슷한 패턴)
        return null;
    }

    // DTO ↔ Entity 변환 메서드들
    private UserResponseDto convertToDto(UserEtt user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }

    private UserEtt convertToEntity(UserSignupDto dto) {
        UserEtt user = new UserEtt();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // 실제로는 암호화 필요
        user.setName(dto.getName());
        user.setPhoneNumber(dto.getPhoneNumber());
        return user;
    }
}