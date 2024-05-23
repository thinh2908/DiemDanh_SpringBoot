package com.diemdanh.service.Impl;

import com.diemdanh.model.Leaving;
import com.diemdanh.model.Users;
import com.diemdanh.repo.LeavingRepository;
import com.diemdanh.service.ILeaving;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class LeavingServiceImpl implements ILeaving {

    @Autowired
    private LeavingRepository leavingRepository;
    @Override
    public List<Leaving> listLeaving() {
        List<Leaving> leavingList = leavingRepository.findAll();
        return leavingList;
    }

    @Override
    public Leaving getLeavingById(Long id) {
        return leavingRepository.findById(id).get();
    }

    @Override
    public Leaving updateLeaving(Leaving leaving) {
        leavingRepository.save(leaving);
        return leaving;
    }

    @Override
    public Leaving deleteLeavingById(Long id) {
        Leaving leaving = leavingRepository.findById(id).get();
        leavingRepository.deleteById(id);
        return leaving;
    }

    @Override
    public Leaving createLeaving(Leaving leaving) {
        Leaving leavingObj = leavingRepository.save(leaving);
        return leavingObj;
    }

    @Override
    public List<Leaving> listLeavingByUser(Users user) {
        List<Leaving> leavingList = leavingRepository.findByUser(user);
        return leavingList;
    }

    @Override
    public List<Leaving> listLeavingByManager(Users user) {
        List<Leaving> leavingList = leavingRepository.findByManager(user);
        return leavingList;
    }

    @Override
    public List<Leaving> listLeavingByDayForUser(Users user, LocalDateTime start, LocalDateTime end) {
        List<Leaving> leavingList = leavingRepository.listLeavingByVacationBetweenDay(user,start,end);
        return leavingList;
    }

    @Override
    public List<Leaving> listLeaving12Month(Long userId, Long type) {
        return leavingRepository.listLeaving12Month(userId,type);
    }

    @Override
    public List<Leaving> listLeavingByUserAndLeavingType(Users users, Long leavingType) {
        return leavingRepository.findByUserAndLeavingType(users,leavingType);
    }
}
