package com.diemdanh.repo;

import com.diemdanh.model.Leaving;
import com.diemdanh.model.Users;
import com.diemdanh.model.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface LeavingRepository extends JpaRepository<Leaving,Long> {
    List<Leaving> findByUser(Users user);
    List<Leaving> findByManager(Users user);
    List<Leaving> findByUserAndLeavingType(Users user, Long leavingType);

    @Query(value = "SELECT * FROM LEAVING WHERE USER_ID = ?1 AND START_DAY >= ?2 AND  END_DAY <=  ?3 AND STATUS = 1", nativeQuery = true)
    List<Leaving> listLeavingByVacationBetweenDay(Users user,LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT * FROM LEAVING " +
            "WHERE EXTRACT(YEAR FROM TO_TIMESTAMP(start_day, 'YYYY-MM-DD HH24:MI:SS')) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND USER_ID = ?1 " +
            "AND LEAVING_TYPE = ?2", nativeQuery = true)
    List<Leaving> listLeaving12Month(Long userId, Long type);
}
