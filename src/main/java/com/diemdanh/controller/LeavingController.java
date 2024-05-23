package com.diemdanh.controller;

import com.diemdanh.Utils.SessionHelper;
import com.diemdanh.base.CoverStringToTime;
import com.diemdanh.model.Employee;
import com.diemdanh.model.Leaving;
import com.diemdanh.model.Users;
import com.diemdanh.request.LeavingRequest;
import com.diemdanh.response.LeavingResponse;
import com.diemdanh.service.Impl.EmployeeServiceImpl;
import com.diemdanh.service.Impl.LeavingServiceImpl;
import com.diemdanh.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/leaving")
public class LeavingController {
    @Autowired
    private LeavingServiceImpl leavingService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @GetMapping("")
    public ResponseEntity<?> listLeavingAll(){
        Users currentUser = SessionHelper.getCurrentUser();
        List<Leaving> leavingList = leavingService.listLeavingByUser(currentUser);
        List<LeavingResponse> leavingResponseList = leavingList.stream().map(LeavingResponse::new)
                                                    .collect(Collectors.toList());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Range",
                "users 0-1/"+leavingList.size());

        return ResponseEntity.ok().headers(responseHeaders).body(leavingResponseList);
    }

    @GetMapping("/day")
    public ResponseEntity<?> listLeavingAllByDay(@RequestParam String startDay, @RequestParam String endDay){
        Users currentUser = SessionHelper.getCurrentUser();
        LocalDateTime start = CoverStringToTime.cover2(startDay);
        LocalDateTime end = CoverStringToTime.cover2(endDay);
        List<Leaving> leavingList = leavingService.listLeavingByDayForUser(currentUser,start,end);
        List<LeavingResponse> leavingResponseList = leavingList.stream().map(LeavingResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(leavingResponseList);
    }
    @GetMapping("/day/{userId}")
    public ResponseEntity<?> listLeavingAllByDayByManager
                (@RequestParam String startDay, @RequestParam String endDay, @PathVariable Long userId ){
        Users user = userService.getUserById(userId);
        LocalDateTime start = CoverStringToTime.cover2(startDay);
        LocalDateTime end = CoverStringToTime.cover2(endDay);
        List<Leaving> leavingList = leavingService.listLeavingByDayForUser(user,start,end);
        List<LeavingResponse> leavingResponseList = leavingList.stream().map(LeavingResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(leavingResponseList);
    }

    @PostMapping("")
    public ResponseEntity<?> createLeaving(@RequestBody LeavingRequest leavingRequest){
        Leaving leaving = new Leaving(leavingRequest);
        Users currentUser = SessionHelper.getCurrentUser();
        leaving.setUser(currentUser);
        leaving.setManager(currentUser.getManager());

        Employee employee = employeeService.getEmployeeById(currentUser.getEmployee().getId());

        LocalDate today = LocalDateTime.now().toLocalDate();
        LocalDate joiningDay = employee.getJoiningDay().toLocalDate();

        today = today.withDayOfMonth(1);
        joiningDay = joiningDay.withDayOfMonth(1);
        Period period = Period.between(joiningDay, today);


        int months = (int) (period.toTotalMonths() + 1);

        List<Leaving> leavingListType1 = leavingService.listLeavingByUserAndLeavingType(currentUser,Long.parseLong("1"));
        List<Leaving> leavingListType2 = leavingService.listLeaving12Month(currentUser.getId(),Long.parseLong("2"));
        List<Leaving> leavingListType3 = leavingService.listLeaving12Month(currentUser.getId(),Long.parseLong("3"));
        List<Leaving> leavingListType4 = leavingService.listLeaving12Month(currentUser.getId(),Long.parseLong("4"));

        float type1Total = (float) leavingListType1.stream().mapToDouble(Leaving::getDays).sum();
        float type2Total = (float) leavingListType2.stream().mapToDouble(Leaving::getDays).sum();
        float type3Total = (float) leavingListType3.stream().mapToDouble(Leaving::getDays).sum();
        float type4Total = (float) leavingListType4.stream().mapToDouble(Leaving::getDays).sum();

        float type1Left = months - type1Total;
        float type2Left = 3 - type2Total;
        float type3Left = 90 - type3Total;
        float type4Left = 3 - type4Total;

        int validNumber=-1;
        if(leaving.getLeavingType() == 1 )
            validNumber = (int) (type1Left - leaving.getDays());
        else if (leaving.getLeavingType() == 2)
            validNumber = (int) (type2Left - leaving.getDays());
        else if (leaving.getLeavingType() == 3)
            validNumber = (int) (type3Left - leaving.getDays());
        else if (leaving.getLeavingType() == 4)
            validNumber = (int) (type4Left - leaving.getDays());

        if(validNumber<0)
            return ResponseEntity.badRequest().build();

        Leaving createdLeaving = leavingService.createLeaving(leaving);
        return  ResponseEntity.ok(new LeavingResponse(createdLeaving));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLeaving(@RequestBody LeavingRequest leavingRequest, @PathVariable Long id){
        Leaving leaving = new Leaving(leavingRequest);
        leaving.setId(id);
        leaving.setUser(userService.getUserById(leavingRequest.getUserId()));

        Leaving updateLeaving = leavingService.updateLeaving(leaving);
        return  ResponseEntity.ok(new LeavingResponse(updateLeaving));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLeaving(@PathVariable Long id){
        Leaving leaving = leavingService.deleteLeavingById(id);
        return  ResponseEntity.ok(new LeavingResponse(leaving));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLeavingById(@PathVariable Long id){
        Leaving leaving = leavingService.getLeavingById(id);
        return ResponseEntity.ok(new LeavingResponse(leaving));
    }
}
