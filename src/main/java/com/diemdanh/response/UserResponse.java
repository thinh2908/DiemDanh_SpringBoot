package com.diemdanh.response;


import com.diemdanh.model.Users;

public class UserResponse {
    private Long id;
    private String username;
    private Long managerId;
    private Long employeeId;
    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public UserResponse(Users user) {
        this.id= user.getId();
        this.username = user.getUsername();
        this.employeeId = (user.getEmployee() != null) ? user.getEmployee().getId() : null ;
        this.managerId = (user.getManager() != null) ? user.getManager().getId() : null;
        this.roleId = user.getRole().getId();
    }
}
