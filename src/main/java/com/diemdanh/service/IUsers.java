package com.diemdanh.service;

import com.diemdanh.model.Employee;
import com.diemdanh.model.Roles;
import com.diemdanh.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUsers {
    public Users createUser(Users user);
    public List<Users> listUser(int page);
    public List<Users> listUser();
    public Users getUserById(Long id);
    public List<Users> getListUserByUsername(String username);
    public Users updateUser(Users user);
    public Users deleteUser(Long id);
    public List<Users> getByEmployee(Employee employee);
    public List<Users> listAllByRole(Roles role);
    public List<Users> listAllByManager(Users user);
    public List<Users> ListUserById(List<Long> listId);

    public Page<Users> findAll(Pageable pageable);
    public Page<Users> findAllByUserName(String username, Pageable page);
}
