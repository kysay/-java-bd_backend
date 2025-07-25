package com.example.app_bd_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        System.out.println("Hello API 호출됨!");
        return ResponseEntity.ok().body("Hello from Spring Boot!");
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test() {
        logger.info("Test API 호출됨!");
        return ResponseEntity.ok(
                java.util.Map.of(
                        "message", "API 연결 성공!",
                        "timestamp", java.time.LocalDateTime.now()
                )
        );
    }
}