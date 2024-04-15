package com.diemdanh.controller;

import com.diemdanh.model.Users;
import com.diemdanh.model.Vacation;
import com.diemdanh.response.UserResponse;
import com.diemdanh.response.VacationResponse;
import com.diemdanh.service.Impl.VacationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vacation")
public class VacationController {
    @Autowired
    private VacationServiceImpl vacationService;

    @GetMapping("")
    public ResponseEntity<?> listAllVacation(){
        List<Vacation> vacation = vacationService.getAllVacation();
        List<VacationResponse> vacationResponseList = vacation.stream()
                .map(VacationResponse::new)
                .collect(Collectors.toList());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Range",
                "vacation 0-1/"+vacationResponseList.size());

        return ResponseEntity.ok().headers(responseHeaders).body(vacationResponseList);
    }
}
