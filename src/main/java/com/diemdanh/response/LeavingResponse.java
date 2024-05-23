package com.diemdanh.response;

import com.diemdanh.model.Leaving;

public class LeavingResponse {
    private Long id;
    private Long userId;
    private float days;
    private String startDay;
    private String endDay;
    private String reason;
    private Long leavingType;
    private Long status;
    private Long managerId;

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

    public float getDays() {
        return days;
    }

    public void setDays(float days) {
        this.days = days;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getLeavingType() {
        return leavingType;
    }

    public void setLeavingType(Long leavingType) {
        this.leavingType = leavingType;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public LeavingResponse(Leaving leaving) {
        this.days = leaving.getDays();
        this.startDay = leaving.getStartDay();
        this.endDay = leaving.getEndDay();
        this.reason = leaving.getReason();
        this.leavingType = leaving.getLeavingType();
        this.status = leaving.getStatus();
        this.id = leaving.getId();
        this.userId = leaving.getUser().getId();
        this.managerId = leaving.getManager().getId();
    }
}
