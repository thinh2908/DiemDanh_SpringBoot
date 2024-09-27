package com.diemdanh.repo;

import com.diemdanh.model.Message;
import com.diemdanh.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message,Long> {
    Page<Message> findByUser(Users user, Pageable page);
    @Modifying
    @Query(value = "DELETE FROM MESSAGE WHERE USER_ID = ?1",nativeQuery = true)
    void deleteInBulkByUserId(Long userId);
}
