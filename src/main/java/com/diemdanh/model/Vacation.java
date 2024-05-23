package com.diemdanh.model;

import com.diemdanh.request.VacationRequest;

import javax.persistence.*;

@Entity
@Table(name = "vacation")
public class Vacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Vacation(VacationRequest vacationRequest) {
        this.reason = vacationRequest.getReason();
        this.days = vacationRequest.getDays();
        this.startDay = vacationRequest.getStartDay();
        this.endDays = vacationRequest.getEndDays();
    }

    public Vacation() {
    }
}
