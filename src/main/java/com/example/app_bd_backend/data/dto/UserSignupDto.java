package com.example.app_bd_backend.data.dto;

/**
 * FIXME
 * 도메인으로 dto 나누는게 관리에 용이함
 * UserDto class 생성 후 sub class 로 관리해도되고
 * dto 패키지 밑에 user 패키지를 추가해서 class 를 각각 만들어도 되고 상황에 맞게 선택, 맞고 틀린 방법은 없음!
 */
public class UserSignupDto {

    private String username;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;

    // 기본 생성자
    public UserSignupDto() {}

    // 생성자
    public UserSignupDto(String username, String email, String password, String name, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // Getter, Setter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}