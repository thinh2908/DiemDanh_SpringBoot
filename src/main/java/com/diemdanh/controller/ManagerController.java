package com.diemdanh.controller;

import com.diemdanh.Utils.SessionHelper;
import com.diemdanh.base.BaseFunction;
import com.diemdanh.model.Employee;
import com.diemdanh.model.Leaving;
import com.diemdanh.model.Roles;
import com.diemdanh.model.Users;
import com.diemdanh.response.LeavingResponse;
import com.diemdanh.response.UserResponse;
import com.diemdanh.service.Impl.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private UserServiceImpl userService ;

    @Autowired
    private RolesServiceImpl rolesService ;

    @Autowired
    private EmployeeServiceImpl employeeService ;

    @Autowired
    private LeavingServiceImpl leavingService ;

    @Autowired
    private MessageServiceImpl messageService;

    @GetMapping("")
    public ResponseEntity<?> listAllManager() {

        Roles role = rolesService.getRoleById(Long.parseLong("2"));

        List<Users> users = userService.listAllByRole(role);
        List<UserResponse> userResponseList = users.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Range",
                "users 0-1/"+users.size());

        return ResponseEntity.ok().headers(responseHeaders).body(userResponseList);
    }

    @GetMapping("/all-user")
    public ResponseEntity<?> listAllUserForCurrentManager() {

        Users user = SessionHelper.getCurrentUser();

        List<Users> users = userService.listAllByManager(user);
        List<UserResponse> userResponseList =
                users.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());

        HttpHeaders responseHeaders = new HttpHeaders();


        return ResponseEntity.ok().headers(responseHeaders).body(userResponseList);
    }

    @GetMapping("/count-day")
    public ResponseEntity<?> dayLeftForUser() throws JsonProcessingException {

        Users user = SessionHelper.getCurrentUser();
        Employee employee = employeeService.getEmployeeById(user.getEmployee().getId());

        LocalDate today = LocalDateTime.now().toLocalDate();
        LocalDate joiningDay = employee.getJoiningDay().toLocalDate();

        today = today.withDayOfMonth(1);
        joiningDay = joiningDay.withDayOfMonth(1);
        Period period = Period.between(joiningDay, today);


        int months = (int) (period.toTotalMonths() + 1);

        List<Leaving> leavingListType1 = leavingService.listLeavingByUserAndLeavingType(user,Long.parseLong("1"));
        List<Leaving> leavingListType2 = leavingService.listLeaving12Month(user.getId(),Long.parseLong("2"));
        List<Leaving> leavingListType3 = leavingService.listLeaving12Month(user.getId(),Long.parseLong("3"));
        List<Leaving> leavingListType4 = leavingService.listLeaving12Month(user.getId(),Long.parseLong("4"));

        float type1Total = (float) leavingListType1.stream().mapToDouble(Leaving::getDays).sum();
        float type2Total = (float) leavingListType2.stream().mapToDouble(Leaving::getDays).sum();
        float type3Total = (float) leavingListType3.stream().mapToDouble(Leaving::getDays).sum();
        float type4Total = (float) leavingListType4.stream().mapToDouble(Leaving::getDays).sum();

        float type1Left = months - type1Total;
        float type2Left = 3 - type2Total;
        float type3Left = 90 - type3Total;
        float type4Left = 3 - type4Total;

        ObjectMapper mapper = new ObjectMapper();

        String content = String.format("{\"type1\": %f ,\"type2\": %f ,\"type3\": %f ,\"type4\": %f }",
                type1Left, type2Left, type3Left, type4Left);


        JsonNode json  = mapper.readTree(content);

        return ResponseEntity.ok().body(json);
    }

    @GetMapping("/count-info")
    public ResponseEntity<?> countInfo() throws JsonProcessingException {
        Roles role= rolesService.getRoleById(Long.valueOf(2));

        Long employeeTotal = employeeService.countAll();
        Long userTotal = userService.countAll();
        Long managerTotal = userService.countByRoles(role);
        Long messageTotal = messageService.countAll();

        ObjectMapper mapper = new ObjectMapper();
        String content = String.format("{\"employees\": %d ,\"users\": %d ,\"managers\": %d ,\"messages\": %d }",
                employeeTotal, userTotal, managerTotal, messageTotal);
        JsonNode json  = mapper.readTree(content);

        return ResponseEntity.ok(json);
    }

    @GetMapping("/getManyById")
    public ResponseEntity<?> listAllUserById(@RequestParam(required = false,value = "id") String ids) {
        List<Users> users = userService.ListUserById(BaseFunction.convertStringToListLong(ids));
        List<UserResponse> userResponseList = users.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(userResponseList);
//        return ResponseEntity.ok(users);
    }
}
