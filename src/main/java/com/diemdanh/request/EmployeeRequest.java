package com.diemdanh.request;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class EmployeeRequest {
    private Long id;
    private String name;
    private String team;
    private String joiningDay;
    private MultipartFile avatar;
    private String position;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getJoiningDay() {
        return joiningDay;
    }

    public void setJoiningDay(String joiningDay) {
        this.joiningDay = joiningDay;
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
