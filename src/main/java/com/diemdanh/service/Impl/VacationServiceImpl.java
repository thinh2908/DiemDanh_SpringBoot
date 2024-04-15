package com.diemdanh.service.Impl;

import com.diemdanh.model.Vacation;
import com.diemdanh.repo.VacationRepository;
import com.diemdanh.service.IVacation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Vacation addVacation(Vacation vacation) {
        Vacation vacationObj = vacationRepository.save(vacation);
        return vacationObj;

    }

    @Override
    public Vacation getVacationById() {
        return null;
    }
}
