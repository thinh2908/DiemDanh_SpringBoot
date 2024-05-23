package com.diemdanh.controller;

import com.diemdanh.Utils.SessionHelper;
import com.diemdanh.model.Leaving;
import com.diemdanh.model.Users;
import com.diemdanh.request.LeavingRequest;
import com.diemdanh.response.LeavingResponse;
import com.diemdanh.service.Impl.LeavingServiceImpl;
import com.diemdanh.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/leaving-manager")
public class LeavingManagerController {
    @Autowired
    private LeavingServiceImpl leavingService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("")
    public ResponseEntity<?> listLeavingAll(){
        Users currentUser = SessionHelper.getCurrentUser();
        List<Leaving> leavingList = leavingService.listLeavingByManager(currentUser);
        List<LeavingResponse> leavingResponseList = leavingList.stream().map(LeavingResponse::new)
                .collect(Collectors.toList());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Range",
                "users 0-1/"+leavingList.size());

        return ResponseEntity.ok().headers(responseHeaders).body(leavingResponseList);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateLeaving(@RequestBody LeavingRequest leavingRequest, @PathVariable Long id){
        Leaving leaving = new Leaving(leavingRequest);
        leaving.setId(id);
        leaving.setUser(userService.getUserById(leavingRequest.getUserId()));

        Leaving updateLeaving = leavingService.updateLeaving(leaving);
        return  ResponseEntity.ok(new LeavingResponse(updateLeaving));

    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateStatusOfLeaving(@RequestParam Long status, @PathVariable Long id){
        Leaving leaving = leavingService.getLeavingById(id);
        leaving.setStatus(status);

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
