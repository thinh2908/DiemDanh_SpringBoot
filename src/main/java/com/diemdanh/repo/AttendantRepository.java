package com.diemdanh.repo;

import com.diemdanh.model.Attendant;
import com.diemdanh.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendantRepository extends JpaRepository<Attendant, Long> {
    List<Attendant> findByCheckInTimeBetween(LocalDateTime start,LocalDateTime end);

    @Query(value = "SELECT * FROM ATTENDANT WHERE USER_ID = ?1 AND CHECK_IN_TIME BETWEEN ?2 AND ?3", nativeQuery = true)
    List<Attendant> findByCheckInTimeBetweenForUser(Users user, LocalDateTime start, LocalDateTime end);

}
