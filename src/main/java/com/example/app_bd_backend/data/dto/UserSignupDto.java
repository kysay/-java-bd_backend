package com.example.app_bd_backend.data.dto;

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