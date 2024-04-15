package com.diemdanh.response;

import com.diemdanh.model.Vacation;

public class VacationResponse {
    private Long id;
    private String reason;
    private Long days;
    private String startDay;
    private String endDays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getDays() {
        return days;
    }

    public void setDays(Long days) {
        this.days = days;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDays() {
        return endDays;
    }

    public void setEndDays(String endDays) {
        this.endDays = endDays;
    }

    public VacationResponse(Vacation vacation) {
        this.id = vacation.getId();
        this.reason = vacation.getReason();
        this.days = vacation.getDays();
        this.startDay = vacation.getStartDay();
        this.endDays = vacation.getEndDays();
    }
}
