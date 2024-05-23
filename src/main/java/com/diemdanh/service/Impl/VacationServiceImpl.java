package com.diemdanh.service.Impl;

import com.diemdanh.model.Vacation;
import com.diemdanh.repo.VacationRepository;
import com.diemdanh.service.IVacation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VacationServiceImpl implements IVacation {

    @Autowired
    private VacationRepository vacationRepository;


    @Override
    public List<Vacation> getAllVacation() {
        return vacationRepository.findAll();
    }

    @Override
    public Vacation createVacation(Vacation vacation) {
        Vacation vacationObj = vacationRepository.save(vacation);
        return vacationObj;

    }

    @Override
    public Vacation getVacationById(Long id) {
        Vacation vacationObj = vacationRepository.findById(id).get();
        return vacationObj;
    }
    @Override
    public Vacation updateVacation(Vacation vacation) {
        vacationRepository.save(vacation);
        return vacation;
    }

    @Override
    public Vacation deleteVacationById(Long id) {
        Vacation vacation = vacationRepository.findById(id).get();
        vacationRepository.deleteById(id);
        return vacation;
    }

    @Override
    public List<Vacation> getBetweenDay(LocalDateTime start, LocalDateTime end) {
        List<Vacation> vacationList = vacationRepository.findByVacationBetweenDay(start,end);
        return vacationList;
    }

    @Override
    public Page<Vacation> findAll(Pageable pageable) {
        Page<Vacation> vacationList = vacationRepository.findAll(pageable);
        return vacationList;
    }
}
