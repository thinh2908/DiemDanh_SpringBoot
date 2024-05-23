package com.diemdanh.service.Impl;

import com.diemdanh.model.Attendant;
import com.diemdanh.model.Users;
import com.diemdanh.repo.AttendantRepository;
import com.diemdanh.service.IAttendant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendantServiceImpl implements IAttendant {
    @Autowired
    private AttendantRepository attendantRepository;

    @Override
    public List<Attendant> listAttendant() {
        return attendantRepository.findAll() ;
    }

    @Override
    public Attendant createAttendant(Attendant attendant) {
        return attendantRepository.save(attendant);
    }

    @Override
    public Attendant updateAttendant(Attendant attendant) {
        return attendantRepository.save(attendant);
    }

    @Override
    public Attendant getAttendantById(Long id) {
        return attendantRepository.findById(id).get();
    }

    @Override
    public Attendant deleteAttendantById(Long id) {
        Attendant attendant = attendantRepository.findById(id).get();
        attendantRepository.deleteById(id);
        return attendant;
    }

    @Override
    public List<Attendant> listAttendantBetweenDay(LocalDateTime start, LocalDateTime end) {
        List<Attendant> attendantList = attendantRepository.findByCheckInTimeBetween(start,end);
        return  attendantList;
    }

    @Override
    public List<Attendant> listAttendantBetweenDayForUser(Users user, LocalDateTime start, LocalDateTime end) {
        List<Attendant> attendantList = attendantRepository.findByCheckInTimeBetweenForUser(user,start,end);
        return attendantList;
    }
}
