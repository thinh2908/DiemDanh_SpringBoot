package com.diemdanh.request;

import com.diemdanh.base.Constants;
import com.diemdanh.model.Employee;
import com.diemdanh.model.Roles;
import com.diemdanh.model.Users;



import javax.persistence.*;
import javax.validation.constraints.NotNull;

public class UserRequest {

    @NotNull(message = Constants.MESSAGE_NAME_VALIDATE_NOTNULL)
    private String username;
    @NotNull(message = Constants.MESSAGE_PASSWORD_VALIDATE_NOTNULL)
    private String password;
    private Long managerId;
    @NotNull(message = Constants.MESSAGE_EMPLOYEE_VALIDATE_NOTNULL)
    private Long employeeId;
    @NotNull(message = Constants.MESSAGE_ROLE_VALIDATE_NOTNULL)
    private Long roleId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
