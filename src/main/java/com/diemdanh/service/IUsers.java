package com.diemdanh.service;

import com.diemdanh.model.Employee;
import com.diemdanh.model.Users;

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
}
