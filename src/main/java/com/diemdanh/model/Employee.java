package com.diemdanh.model;

import com.diemdanh.base.CoverStringToTime;
import com.diemdanh.request.EmployeeRequest;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String team;
    private String position;
    private LocalDateTime joiningDay;
    private String avatar;

    @OneToOne(mappedBy = "employee")
    private Users user;

    @CreationTimestamp
    private LocalDateTime createdAt;

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

    public LocalDateTime getJoiningDay() {
        return joiningDay;
    }

    public void setJoiningDay(LocalDateTime joiningDay) {
        this.joiningDay = joiningDay;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Employee() {
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

    public Employee(EmployeeRequest employeeRequest){
        this.name = employeeRequest.getName();
        this.joiningDay = CoverStringToTime.cover(employeeRequest.getJoiningDay());
        this.team = employeeRequest.getTeam();
        this.position = employeeRequest.getPosition();
    }
}
