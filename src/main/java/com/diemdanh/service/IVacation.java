package com.diemdanh.service;

import com.diemdanh.model.Employee;
import com.diemdanh.model.Vacation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface IVacation {
    public List<Vacation> getAllVacation();
    public Vacation createVacation(Vacation vacation);
    public Vacation getVacationById(Long id);
    public Vacation updateVacation(Vacation vacation);
    public Vacation deleteVacationById(Long id);
    public List<Vacation> getBetweenDay(LocalDateTime start, LocalDateTime end);
    public Page<Vacation> findAll(Pageable pageable);
}
