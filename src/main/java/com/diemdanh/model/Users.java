package com.diemdanh.model;

import com.diemdanh.request.UserRequest;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    @ManyToOne()
    @JoinColumn(name = "manager_id", referencedColumnName="id")
    private Users manager;

    @OneToOne
    @JoinColumn(referencedColumnName = "id",name="employee_id")
    private Employee employee;

    @OneToOne()
    @JoinColumn(referencedColumnName = "id")
    private Roles role;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Users getManager() {
        return manager;
    }

    public void setManager(Users manager) {
        this.manager = manager;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Users() {
    }

    public Users(String username, String password, Users manager, Employee employee, Roles role) {
        this.username = username;
        this.password = password;
        this.manager = manager;
        this.employee = employee;
        this.role = role;
    }

    public Users(UserRequest userRequest,Employee employee, Users manager, Roles role) {
        this.username = userRequest.getUsername();
        this.password = userRequest.getPassword();
        this.employee = employee;
        this.manager = manager;
        this.role = role;
    }

}
