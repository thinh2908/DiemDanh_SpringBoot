package com.diemdanh.repo;

import com.diemdanh.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Page<Employee> findAll(Pageable pageable);
    Page<Employee> findByNameContaining(String name,Pageable pageable);
}
