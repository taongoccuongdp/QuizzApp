package com.example.quizz.model;

public class User {
    private String id;
    private String email;
    private String password;
    private String studentId;
    private String phone;
    private String avatar;
    private String fullname;

    public User(String id, String email, String password, String studentId, String phone, String avatar, String fullname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.studentId = studentId;
        this.phone = phone;
        this.avatar = avatar;
        this.fullname = fullname;
    }

    public User(String email, String password, String studentId, String phone, String fullname) {
        this.id = "";
        this.avatar = "";
        this.email = email;
        this.password = password;
        this.studentId = studentId;
        this.phone = phone;
        this.fullname = fullname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
