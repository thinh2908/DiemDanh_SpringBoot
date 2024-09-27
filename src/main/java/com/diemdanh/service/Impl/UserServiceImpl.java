package com.diemdanh.service.Impl;

import com.diemdanh.model.Attendant;
import com.diemdanh.model.Employee;
import com.diemdanh.model.Roles;
import com.diemdanh.model.Users;
import com.diemdanh.repo.AttendantRepository;
import com.diemdanh.repo.LeavingRepository;
import com.diemdanh.repo.MessageRepository;
import com.diemdanh.repo.UsersRepository;
import com.diemdanh.service.IUsers;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class UserServiceImpl implements IUsers {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AttendantRepository attendantRepository;

    @Autowired
    private LeavingRepository leavingRepository;

    @Autowired
    private MessageRepository messageRepository;
    @Override
    @Transactional
    public Users createUser(Users user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    @Override
    public List<Users> listUser() {
        List<Users> users = usersRepository.findAll();
        return users;
    }

    @Override
    public List<Users> listUser(int page) {
        return null;
    }

    @Override
    public Users getUserById(Long id) {
        Users user = usersRepository.findById(id).orElseThrow(()-> null);
        return user;
    }

    @Override
    public List<Users> getListUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public Users updateUser(Users user) {
        usersRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public Users deleteUser(Long id) {
        Users userObj= usersRepository.findById(id).get();
        attendantRepository.deleteInBulkByUserId(id);
        leavingRepository.deleteInBulkByUserId(id);
        messageRepository.deleteInBulkByUserId(id);
        usersRepository.setManagerNULL(id);
        usersRepository.deleteById(id);
        return userObj;
    }

    @Override
    public List<Users> getByEmployee(Employee employee) {
        List<Users> usersList = usersRepository.findByEmployee(employee);
        return usersList ;
    }

    @Override
    public List<Users> listAllByRole(Roles role) {
        List<Users> listUser = usersRepository.findByRole(role);
        return listUser;
    }

    @Override
    public List<Users> ListUserById(List<Long> listId) {
        List<Users> listUser = usersRepository.findAllById(listId);
        return  listUser;
    }

    @Override
    public Page<Users> findAll(Pageable pageable) {
        return usersRepository.findAll(pageable);
    }

    @Override
    public Page<Users> findAllByUserName(String username, Pageable page) {
        return usersRepository.findByUsernameContaining(username,page);
    }

    @Override
    public List<Users> listAllByManager(Users user) {
        return usersRepository.findByManager(user);
    }

    @Override
    public Long countAll() {
        return usersRepository.count();
    }

    @Override
    public Long countByRoles(Roles role) {
        return Long.valueOf(usersRepository.findByRole(role).size());
    }
}
