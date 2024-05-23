package com.diemdanh.repo;

import com.diemdanh.model.Employee;
import com.diemdanh.model.Users;
import com.diemdanh.model.Vacation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
    @Query(value = "SELECT * FROM VACATION WHERE START_DAY >= ?1 AND  END_DAYS <=  ?2", nativeQuery = true)
    List<Vacation> findByVacationBetweenDay(LocalDateTime start, LocalDateTime end);
    Page<Vacation> findAll(Pageable pageable);
}
