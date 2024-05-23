package com.diemdanh.controller;

import com.diemdanh.base.BaseFunction;
import com.diemdanh.base.CoverStringToTime;
import com.diemdanh.model.Employee;
import com.diemdanh.model.Users;
import com.diemdanh.model.Vacation;
import com.diemdanh.request.VacationRequest;
import com.diemdanh.response.EmployeeResponse;
import com.diemdanh.response.UserResponse;
import com.diemdanh.response.VacationResponse;
import com.diemdanh.service.Impl.VacationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vacation")
public class VacationController {
    @Autowired
    private VacationServiceImpl vacationService;

    @GetMapping("")
    public ResponseEntity<?> listAllVacation(@RequestParam(required = false,value = "sort") String sort,
                                             @RequestParam(required = false,value = "range") String range){

        Pageable paging = null;
        Sort sortObject = Sort.by(Sort.Direction.DESC,"id");

        List<Integer> rangeList = BaseFunction.stringToListInteger(range);
        List<String> sortList = BaseFunction.stringToListString(sort);

        //Xu ly body
        if (sort != null && !sort.isEmpty()) {
            sortObject = Sort.by(sortList.get(1).equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC,sortList.get(0));
        }


        if (rangeList != null && rangeList.size() == 2) {
            paging = PageRequest.of(Math.round(rangeList.get(0)/(rangeList.get(1) - rangeList.get(0) + 1)),
                    rangeList.get(1) - rangeList.get(0) + 1,
                    sortObject);
        }
        Page<Vacation> pageVacation;
        pageVacation = vacationService.findAll(paging);

        List<Vacation> vacationList;
        vacationList = pageVacation.getContent();

        List<VacationResponse> vacationResponses = vacationList.stream()
                .map(VacationResponse::new).collect(Collectors.toList());


        //Xu ly header
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Range",
                "vacation "+ rangeList.get(0) + "-" + rangeList.get(1) + "/" + vacationService.getAllVacation().size());
        return  ResponseEntity.ok().headers(responseHeaders).body(vacationResponses);
    }
    @GetMapping("/day")
    public ResponseEntity<?> listAllVacationBetweenDay(@RequestParam String startDay, @RequestParam String endDay){
        LocalDateTime start = CoverStringToTime.cover2(startDay);
        LocalDateTime end = CoverStringToTime.cover2(endDay);
        List<Vacation> vacation = vacationService.getBetweenDay(start,end);
        List<VacationResponse> vacationResponseList = vacation.stream()
                .map(VacationResponse::new)
                .collect(Collectors.toList());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Range",
                "vacation 0-1/"+vacationResponseList.size());

        return ResponseEntity.ok().headers(responseHeaders).body(vacationResponseList);
    }

    @PostMapping("")
    public ResponseEntity<?> createVacation(@RequestBody VacationRequest vacationRequest){
        Vacation vacation = new Vacation(vacationRequest);
        Vacation vacationObj = vacationService.createVacation(vacation);
        return ResponseEntity.ok(new VacationResponse(vacationObj));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVacation(@PathVariable Long id, @RequestBody VacationRequest vacationRequest){
        Vacation vacation = new Vacation(vacationRequest);
        vacation.setId(id);
        Vacation vacationObj = vacationService.updateVacation(vacation);
        return ResponseEntity.ok(vacationObj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVacationById(@PathVariable Long id){
        Vacation vacation = vacationService.deleteVacationById(id);
        return ResponseEntity.ok(new VacationResponse(vacation));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVacationById(@PathVariable Long id){
        Vacation vacation = vacationService.getVacationById(id);
        return ResponseEntity.ok(new VacationResponse(vacation));
    }
}
