package com.diemdanh.service;

import com.diemdanh.model.Roles;

public interface IRoles {
    public Roles createRole(Roles role);
    public Roles getRoleById(Long id);
}
