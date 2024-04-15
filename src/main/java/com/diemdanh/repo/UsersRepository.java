package com.diemdanh.repo;

import com.diemdanh.model.Employee;
import com.diemdanh.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {
    List<Users> findByUsername(String username);
    List<Users> findByEmployee(Employee employeeId);
}
