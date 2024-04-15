package com.diemdanh.service;

import com.diemdanh.model.Vacation;

import java.util.List;

public interface IVacation {
    public List<Vacation> getAllVacation();
    public Vacation addVacation(Vacation vacation);
    public Vacation getVacationById();
}
