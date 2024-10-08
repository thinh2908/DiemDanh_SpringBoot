package com.diemdanh.model;


import com.diemdanh.request.LeavingRequest;

import javax.persistence.*;

@Entity
@Table(name = "leaving")
public class Leaving {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Users user;
    private float days;
    private String startDay;
    private String endDay;
    private String reason;
    private Long leavingType;
    private Long status;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Users manager;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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

    public Users getManager() {
        return manager;
    }

    public void setManager(Users manager) {
        this.manager = manager;
    }

    public Leaving(LeavingRequest leavingRequest) {
        this.days = leavingRequest.getDays();
        this.startDay = leavingRequest.getStartDay();
        this.endDay = leavingRequest.getEndDay();
        this.reason = leavingRequest.getReason();
        this.status = leavingRequest.getStatus();
        this.leavingType = leavingRequest.getLeavingType();
    }

    public Leaving() {
    }
}
