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
/**
 * FIXME
 * CORS 설정 방법에는 컨트롤러 레벨, 전역 설정으로 나눌 수 있고 CrossOrigin 어노테이션을 사용하면 컨트롤러 레벨에서의 설정이 됨
 * 전역 설정을 원할 경우 Filter 를 상속받는 CrossDomainFilter Component 추가
 */
@CrossOrigin(origins = "*")
// @RequiredArgsConstructor
// @Slf4j
public class UserController {

    /**
     * FIXME
     * Lombok 사용으로 객체 생성 생략 가능 => Slf4j
     * Slf4j 사용의 추가 장점으로는 class 가 선언되어있어 다른 컨트롤러 생성 시 복붙한다면 매번 컨트롤러를 바꿔줘야하는데 누락 이슈 발생 가능성이 매우 높음;
     */
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * FIXME
     * 스프링의 개념 중 DI(의존성 주입)에 해당
     * 의존성 주입 방법에는 필드 주입, 수정자(setter) 주입, 생성자 주입이 있고 스프링에서는 생성자 주입을 권장
     * 생성자 주입 : 인스턴스가 생성될 때 1회 호출 보장 및 필드에 final 키워드 사용 가능
     *
     * 현재 컨트롤러의 의존성 주입 방법은 필드 주입에 해당
     * 생성자 주입 방법으로 변경 + 생성자 주입의 단점을 해결하는 Lombok 사용(생성자 코드 생략 가능) => RequiredArgsConstructor
     *
     * 생성자 주입의 단점 예시)
     * UserService, TestService, OtherService 로 서비스가 3개인 경우 생성자 구현 코드. 서비스가 늘어나는만큼 생성자 코드가 늘어남.
     *
     * private final UserService userService;
     * private final TestService testService;
     * private final OtherService otherService;
     *
     * public UserController(UserService userService, TestService testService, OtherService otherService) {
     *     this.userService = userService;
     *     this.testService = testService;
     *     this.otherService = otherService;
     * }
     */
    @Autowired
    private UserService userService;
    // private final UserService userService;

    @GetMapping("/users")
    /**
     * FIXME
     * swagger 붙이실 예정이라면 모두 동일하게 ApiResponse<List<UserResponseDto>> 로 반환하도록 하는건 지양해야함
     * 지양해야하는 이유 : swagger 응답부 예시 데이터로 response.data 부분을 UserResponseDto 로 세팅해야하는데 커스텀 필수 (기본 제공되는 swagger 어노테이션으로는 제한된게 많음)
     * swagger 를 사용하지 않을거라면 일관된 데이터 구조이므로 좋은 예시일 수 있음
     */
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getAllUsers() {  // ← 타입 수정
        logger.info("사용자 목록 조회 API 호출");

        /**
         * FIXME
         * 응답 구조가 공통으로 잡혀있지 않다면 보통 아래 형식으로 반환함
         * 프로젝트 공통 세팅에 따라 try/catch 구문을 넣어 각 API 에 맞는 예외를 발생시키도록 유도할 수 있음
         *
         * ApiResponse<List<UserResponseDto>> response = userService.getAllUsers();
         * return ResponseEntity.status(HttpStatus.OK).body(response);
         */
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