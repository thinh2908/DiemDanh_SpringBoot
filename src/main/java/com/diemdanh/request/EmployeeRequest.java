package com.diemdanh.request;

import java.time.LocalDateTime;

public class EmployeeRequest {
    private Long id;
    private String name;
    private String team;
    private String joiningDay;

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
}
