package com.example.app_bd_backend.dao;

import com.example.app_bd_backend.data.entity.UserEtt;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    // 사용자 저장
    UserEtt save(UserEtt user);

    // ID로 사용자 조회
    Optional<UserEtt> findById(Long id);

    // 모든 사용자 조회
    List<UserEtt> findAll();

    // 사용자명으로 조회
    Optional<UserEtt> findByUsername(String username);

    // 이메일로 조회
    Optional<UserEtt> findByEmail(String email);

    // 사용자명 존재 여부 확인
    boolean existsByUsername(String username);

    // 이메일 존재 여부 확인
    boolean existsByEmail(String email);

    // 사용자 삭제
    void deleteById(Long id);

    // ID 존재 여부 확인
    boolean existsById(Long id);
}