package com.diemdanh.service.Impl;

import com.diemdanh.model.Roles;
import com.diemdanh.repo.RolesRepository;
import com.diemdanh.service.IRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesServiceImpl implements IRoles {

    @Autowired
    private RolesRepository rolesRepository ;
    @Override
    public Roles createRole(Roles role) {
        Roles roleObj = rolesRepository.save(role);
        return roleObj;
    }

    @Override
    public Roles getRoleById(Long id) {
        Roles roleObj = rolesRepository.findById(id).get();
        return roleObj;
    }
}
