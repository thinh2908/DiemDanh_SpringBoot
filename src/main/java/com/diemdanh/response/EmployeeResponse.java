package com.diemdanh.response;

import com.diemdanh.model.Employee;

import java.time.LocalDateTime;

public class EmployeeResponse {
    private Long id;
    private String name;
    private String team;
    private String joiningDay;
    private String createdAt;
    private String avatar;
    private String position;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public EmployeeResponse(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.team = employee.getTeam();
        this.joiningDay = employee.getJoiningDay().toString();
        this.createdAt = employee.getCreatedAt().toString();
        this.avatar = employee.getAvatar();
        this.position = employee.getPosition();
    }
}
