package com.diemdanh.model;


import com.diemdanh.base.CoverStringToTime;
import com.diemdanh.request.AttendantRequest;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendant")
public class Attendant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn
    private Users user;
    private String title;
    @CreationTimestamp
    private LocalDateTime checkInTime;
    private Boolean type;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Attendant() {
    }
    public Attendant(AttendantRequest attendantRequest) {
        this.title = attendantRequest.getTitle();
        this.type = attendantRequest.getType();
    }
}
