package com.example.app_bd_backend.data.repository;

import com.example.app_bd_backend.data.entity.UserEtt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEtt, Long> {  // ← UserEtt로 변경
    Optional<UserEtt> findByUsername(String username);
    Optional<UserEtt> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}