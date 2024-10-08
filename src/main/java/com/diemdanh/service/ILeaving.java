package com.diemdanh.service;

import com.diemdanh.model.Leaving;
import com.diemdanh.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ILeaving {
    public List<Leaving> listLeaving();
    public Leaving getLeavingById(Long id);
    public Leaving updateLeaving(Leaving leaving);
    public Leaving deleteLeavingById(Long id);
    public Leaving createLeaving(Leaving leaving);
    public List<Leaving> listLeavingByUser(Users user);
    public List<Leaving> listLeavingByManager(Users user);
    public List<Leaving> listLeavingByDayForUser(Users user, LocalDateTime start, LocalDateTime end);
    public List<Leaving> listLeaving12Month(Long userId, Long type);
    public List<Leaving> listLeavingByUserAndLeavingType(Users users,Long leavingType);
    public Page<Leaving> findAll(Pageable pageable);
    public Page<Leaving> findByUser(Users user, Pageable pageable);
    public Page<Leaving> findByManager(Users user, Pageable pageable);
}
