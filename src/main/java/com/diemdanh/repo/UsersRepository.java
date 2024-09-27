package com.diemdanh.repo;

import com.diemdanh.model.Employee;
import com.diemdanh.model.Roles;
import com.diemdanh.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Page<Users> findAll(Pageable pageable);
    Page<Users> findByUsernameContaining(String username,Pageable pageable);
    List<Users> findByUsername(String username);
    List<Users> findByEmployee(Employee employeeId);
    List<Users> findByRole(Roles role);
    List<Users> findByManager(Users user);
    @Modifying
    @Query(value = "UPDATE USERS SET MANAGER_ID = NULL where ID = ?1",nativeQuery = true)
    void setManagerNULL(Long Id);
}
