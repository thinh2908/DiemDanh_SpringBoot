package com.diemdanh.service;

import com.diemdanh.model.Attendant;
import com.diemdanh.model.Users;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface IAttendant {
    public List<Attendant> listAttendant();
    public Attendant createAttendant(Attendant attendant);
    public Attendant updateAttendant(Attendant attendant);
    public Attendant getAttendantById(Long id);
    public Attendant deleteAttendantById(Long id);
    public List<Attendant> listAttendantBetweenDay(LocalDateTime start,LocalDateTime end);
    public List<Attendant> listAttendantBetweenDayForUser(Users user, LocalDateTime start,LocalDateTime end);
}
