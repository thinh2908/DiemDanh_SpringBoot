package com.diemdanh.response;

import com.diemdanh.model.Attendant;

import java.sql.Timestamp;

public class AttendantResponse {
    private Long id;
    private Long userId;
    private String title;
    private String checkInTime;
    private Boolean type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public AttendantResponse(Attendant attendant){
        this.id = attendant.getId();
        this.title = attendant.getTitle();
        this.checkInTime = attendant.getCheckInTime().toString();
        this.type = attendant.getType();
        this.userId = attendant.getUser().getId();
    }
}
