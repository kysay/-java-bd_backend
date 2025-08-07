package com.example.app_bd_backend.data.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * FIXME
 * 하나의 테이블에는 그에 맞는 목적 컬럼들을 최소화하는것을 권장
 * 테이블에 컬럼이 너무 많으면 데이터 관리도 힘들고 의존성이 너무 강해져서 덕지덕지 붙게되는 상황들이 많이 발생되고
 * row 데이터 자체가 방대해지면 쿼리 시간에도 영향이 있고 Join 이라도 걸게되면? 그냥 스노우볼 현상임.. 나비효과
 *
 * 사용자 정보를 관리하는 테이블이라면 사용자 정보에 해당되는 최소한의 컬럼만 둘것.
 * 성격, 취미, 사진 등 USER_PROFILE 처럼 사용자 정보 단위를 쪼갤 수 있을듯.
 * 중요한건 상황에 따라서 어쩔 수 없다면 이것도 최선의 방법이 될 수 있음
 */

/**
 * 사용자 정보를 관리하는 JPA 엔티티 클래스
 * 데이터베이스의 'users' 테이블과 매핑됩니다.
 * 이메일 로그인과 소셜 로그인(애플, 카카오, 라인)을 모두 지원합니다.
 *
 * id - 사용자 고유 ID, 자동증가 Primary Key [필수]
 * loginType - 로그인 방식 (EMAIL/APPLE/KAKAO/LINE), 기본값 EMAIL [필수]
 * email - 이메일 주소, 유니크, 최대 100자 [필수]
 * password - 해시화된 비밀번호, 최대 255자, 소셜로그인시 null [선택]
 * socialId - 소셜 로그인 고유 ID, 이메일로그인시 null [선택]
 * username - 사용자 실명, 최대 50자 [필수]
 * name - 사용자 실명, 최대 50자 [필수]
 * gender - 성별 (MALE/FEMALE) [필수]
 * birthDate - 생년월일 [필수]
 * nationality - 국적 (KOREAN/FOREIGN) [필수]
 * region - 지역명, 최대 100자 [필수]
 * isDomestic - 국내/해외 구분 (true: 국내, false: 해외) [필수]
 * personalities - 성격 태그들, JSON 배열 형태 [선택]
 * hobbies - 취미 태그들, JSON 배열 형태 [선택]
 * frontPhoto - 정면 사진 URL, 최대 500자 [선택]
 * sidePhoto - 측면 사진 URL, 최대 500자 [선택]
 * photo1 - 기타 사진1 URL, 최대 500자 [선택]
 * photo2 - 기타 사진2 URL, 최대 500자 [선택]
 * photo3 - 기타 사진3 URL, 최대 500자 [선택]
 * excludeAcquaintances - 지인 제외 여부, 기본값 false [필수]
 * role - 사용자 권한 (USER/ADMIN), 기본값 USER [필수]
 * status - 계정 상태 (ACTIVE/INACTIVE/SUSPENDED), 기본값 ACTIVE [필수]
 * createdAt - 계정 생성 시간, 자동 설정 [자동]
 * updatedAt - 최종 수정 시간, 자동 갱신 [자동]
 *
 */
@Entity // JPA 엔티티임을 명시
@Table(name = "users") // 실제 데이터베이스 테이블명 지정
public class UserEtt {

    /**
     * 사용자 고유 ID (Primary Key)
     * 자동 증가되는 값으로 설정
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 설정
    private Long id;

    // ========== 로그인 정보 ==========

    /**
     * 로그인 타입 (EMAIL, APPLE, KAKAO, LINE)
     * - ENUM 타입을 STRING으로 데이터베이스에 저장
     * - 기본값: EMAIL
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoginType loginType = LoginType.EMAIL;

    /**
     * 로그인 방식 열거형
     * EMAIL: 이메일 로그인, APPLE: 애플 로그인, KAKAO: 카카오 로그인, LINE: 라인 로그인
     */
    public enum LoginType {
        EMAIL, APPLE, KAKAO, LINE
    }

    /**
     * 이메일 주소 (이메일 로그인 또는 소셜 로그인에서 제공)
     * - 유니크 제약조건: 중복 불가
     * - NULL 불가
     * - 최대 100자
     */
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    /**
     * 비밀번호 (이메일 로그인용, 해시화되어 저장)
     * - 소셜 로그인의 경우 null
     * - 최대 255자 (해시값 저장을 위해 충분한 길이)
     */
    @Column(length = 255)
    private String password;

    /**
     * 사용자명 (닉네임)
     * - 앱에서 표시되는 이름
     * - 유니크 제약조건: 중복 불가
     * - 최대 50자
     */
    @Column(unique = true, nullable = false, length = 50)
    private String username;

    /**
     * 소셜 로그인 고유 ID
     * - 각 소셜 플랫폼에서 제공하는 사용자 고유 식별자
     * - 이메일 로그인의 경우 null
     * - 유니크 제약조건: 중복 불가
     */
    @Column(name = "social_id", unique = true)
    private String socialId;

    // ========== 기본 정보 ==========

    /**
     * 사용자 실명
     * - NULL 불가
     * - 최대 50자
     */
    @Column(nullable = false, length = 50)
    private String name;

    /**
     * 휴대폰 번호 (선택사항)
     * - NULL 가능
     * - 최대 15자
     */
    @Column(length = 15)
    private String phoneNumber;

    /**
     * 성별
     * - ENUM 타입을 STRING으로 데이터베이스에 저장
     * - NULL 불가
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    /**
     * 성별 열거형
     * MALE: 남성, FEMALE: 여성
     */
    public enum Gender {
        MALE, FEMALE
    }

    /**
     * 생년월일
     * - NULL 불가
     * - LocalDate 타입으로 날짜만 저장
     */
    @Column(nullable = false)
    private LocalDate birthDate;

    /**
     * 국적
     * - ENUM 타입을 STRING으로 데이터베이스에 저장
     * - NULL 불가
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Nationality nationality;

    /**
     * 국적 열거형
     * KOREAN: 한국인, FOREIGN: 외국인
     */
    public enum Nationality {
        KOREAN, FOREIGN
    }

    // ========== 지역 정보 ==========

    /**
     * 지역명
     * - NULL 불가
     * - 최대 100자
     * - 국내/해외 구분하여 저장
     */
    @Column(nullable = false, length = 100)
    private String region;

    /**
     * 국내/해외 구분
     * - NULL 불가
     * - true: 국내, false: 해외
     */
    @Column(nullable = false)
    private Boolean isDomestic;

    // ========== 성격 정보 ==========

    /**
     * 성격 태그들을 JSON 배열로 저장
     * - TEXT 타입으로 긴 문자열 저장 가능
     * - 예: ["활발한", "내향적", "유머러스"]
     * - NULL 가능 (선택사항)
     */
    @Column(columnDefinition = "TEXT")
    private String personalities;

    // ========== 취미 정보 ==========

    /**
     * 취미 태그들을 JSON 배열로 저장
     * - TEXT 타입으로 긴 문자열 저장 가능
     * - 예: ["독서", "영화감상", "운동"]
     * - NULL 가능 (선택사항)
     */
    @Column(columnDefinition = "TEXT")
    private String hobbies;

    // ========== 사진 정보 ==========

    /**
     * 정면 사진 URL
     * - NULL 가능 (선택사항)
     * - 최대 500자
     * - S3 등 스토리지 링크 저장
     */
    @Column(length = 500)
    private String frontPhoto;

    /**
     * 측면 사진 URL
     * - NULL 가능 (선택사항)
     * - 최대 500자
     * - S3 등 스토리지 링크 저장
     */
    @Column(length = 500)
    private String sidePhoto;

    /**
     * 기타 사진 1 URL
     * - NULL 가능 (선택사항)
     * - 최대 500자
     * - S3 등 스토리지 링크 저장
     */
    @Column(length = 500)
    private String photo1;

    /**
     * 기타 사진 2 URL
     * - NULL 가능 (선택사항)
     * - 최대 500자
     * - S3 등 스토리지 링크 저장
     */
    @Column(length = 500)
    private String photo2;

    /**
     * 기타 사진 3 URL
     * - NULL 가능 (선택사항)
     * - 최대 500자
     * - S3 등 스토리지 링크 저장
     */
    @Column(length = 500)
    private String photo3;

    // ========== 기타 설정 ==========

    /**
     * 지인 제외 여부
     * - NULL 불가
     * - true: 지인에게 프로필 노출 안함, false: 노출함
     * - 기본값: false
     */
    @Column(nullable = false)
    private Boolean excludeAcquaintances = false;

    // ========== 시스템 정보 ==========

    /**
     * 사용자 권한
     * - ENUM 타입을 STRING으로 데이터베이스에 저장
     * - 기본값: USER
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.USER;

    /**
     * 사용자 권한 열거형
     * USER: 일반 사용자, ADMIN: 관리자
     */
    public enum UserRole {
        USER, ADMIN
    }

    /**
     * 사용자 계정 상태
     * - ENUM 타입을 STRING으로 데이터베이스에 저장
     * - 기본값: ACTIVE
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.ACTIVE;

    /**
     * 사용자 계정 상태 열거형
     * ACTIVE: 활성화, INACTIVE: 비활성화, SUSPENDED: 정지
     */
    public enum UserStatus {
        ACTIVE, INACTIVE, SUSPENDED
    }

    /**
     * 계정 생성 시간
     * - Hibernate가 INSERT 시 자동으로 현재 시간 설정
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * 계정 정보 최종 수정 시간
     * - Hibernate가 UPDATE 시 자동으로 현재 시간 갱신
     */
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // ========== 생성자 ==========

    /**
     * 기본 생성자 (JPA 필수)
     * JPA가 엔티티 인스턴스를 생성할 때 사용
     */
    public UserEtt() {}

    /**
     * 이메일 회원가입용 생성자
     * 이메일과 비밀번호를 사용한 회원가입 시 사용
     *
     * @param email 이메일 주소
     * @param password 비밀번호 (해시화 필요)
     */
    public UserEtt(String email, String password) {
        this.loginType = LoginType.EMAIL;
        this.email = email;
        this.password = password;
    }

    /**
     * 소셜 로그인용 생성자
     * 소셜 플랫폼을 통한 로그인 시 사용
     *
     * @param loginType 로그인 타입 (APPLE, KAKAO, LINE)
     * @param socialId 소셜 플랫폼 고유 ID
     * @param email 이메일 주소
     */
    public UserEtt(LoginType loginType, String socialId, String email) {
        this.loginType = loginType;
        this.socialId = socialId;
        this.email = email;
    }

    // ========== Getter & Setter ==========

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LoginType getLoginType() { return loginType; }
    public void setLoginType(LoginType loginType) { this.loginType = loginType; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getSocialId() { return socialId; }
    public void setSocialId(String socialId) { this.socialId = socialId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public Nationality getNationality() { return nationality; }
    public void setNationality(Nationality nationality) { this.nationality = nationality; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public Boolean getIsDomestic() { return isDomestic; }
    public void setIsDomestic(Boolean isDomestic) { this.isDomestic = isDomestic; }

    public String getPersonalities() { return personalities; }
    public void setPersonalities(String personalities) { this.personalities = personalities; }

    public String getHobbies() { return hobbies; }
    public void setHobbies(String hobbies) { this.hobbies = hobbies; }

    public String getFrontPhoto() { return frontPhoto; }
    public void setFrontPhoto(String frontPhoto) { this.frontPhoto = frontPhoto; }

    public String getSidePhoto() { return sidePhoto; }
    public void setSidePhoto(String sidePhoto) { this.sidePhoto = sidePhoto; }

    public String getPhoto1() { return photo1; }
    public void setPhoto1(String photo1) { this.photo1 = photo1; }

    public String getPhoto2() { return photo2; }
    public void setPhoto2(String photo2) { this.photo2 = photo2; }

    public String getPhoto3() { return photo3; }
    public void setPhoto3(String photo3) { this.photo3 = photo3; }

    public Boolean getExcludeAcquaintances() { return excludeAcquaintances; }
    public void setExcludeAcquaintances(Boolean excludeAcquaintances) { this.excludeAcquaintances = excludeAcquaintances; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    // createdAt은 setter 없음 - 자동 관리되므로 수동 변경 불가

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    // updatedAt은 setter 없음 - 자동 관리되므로 수동 변경 불가

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}