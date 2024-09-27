package com.diemdanh.controller;

import com.diemdanh.Utils.SessionHelper;
import com.diemdanh.base.BaseFunction;
import com.diemdanh.base.MessageString;
import com.diemdanh.model.Leaving;
import com.diemdanh.model.Message;
import com.diemdanh.model.Users;
import com.diemdanh.request.LeavingRequest;
import com.diemdanh.response.LeavingResponse;
import com.diemdanh.service.Impl.LeavingServiceImpl;
import com.diemdanh.service.Impl.MessageServiceImpl;
import com.diemdanh.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private MessageServiceImpl messageService;
    @GetMapping("")
    public ResponseEntity<?> listLeavingAll(@RequestParam(required = false,value = "sort") String sort,
                                            @RequestParam(required = false,value = "range") String range){
        Pageable paging = null;
        Sort sortObject = Sort.by(Sort.Direction.DESC,"id");

        List<Integer> rangeList = BaseFunction.stringToListInteger(range);
        List<String> sortList = BaseFunction.stringToListString(sort);

        Users currentUser = SessionHelper.getCurrentUser();
        //Xu ly body
        if (sort != null && !sort.isEmpty()) {
            sortObject = Sort.by(sortList.get(1).equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC,sortList.get(0));
        }


        if (rangeList != null && rangeList.size() == 2) {
            paging = PageRequest.of(Math.round(rangeList.get(0)/(rangeList.get(1) - rangeList.get(0) + 1)),
                    rangeList.get(1) - rangeList.get(0) + 1,
                    sortObject);
        }
        Page<Leaving> pageLeaving;
        pageLeaving= leavingService.findByManager(currentUser,paging);

        List<Leaving> leavingList;
        leavingList = pageLeaving.getContent();

        List<LeavingResponse> vacationResponses = leavingList.stream()
                .map(LeavingResponse::new).collect(Collectors.toList());


        //Xu ly header

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Range",
                "leaving-manager "+ rangeList.get(0) + "-" + rangeList.get(1) + "/" + pageLeaving.getTotalElements());
        return  ResponseEntity.ok().headers(responseHeaders).body(vacationResponses);
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
        if(status==1) {
            Message message1 = new Message(MessageString.SUCCESS_APPROVAL_LEAVING() +
                    SessionHelper.getCurrentUser().getUsername(), "success", SessionHelper.getCurrentUser());
            Message message2 = new Message(MessageString.APPROVAL_LEAVING() +
                    SessionHelper.getCurrentUser().getUsername(), "info", leaving.getUser());
            messageService.createMessage(message1);
            messageService.createMessage(message2);
        } else if (status == 2){
            Message message1 = new Message(MessageString.SUCCESS_REJECT_LEAVING() +
                    SessionHelper.getCurrentUser().getUsername(), "success", SessionHelper.getCurrentUser());
            Message message2 = new Message(MessageString.REJECT_LEAVING() +
                    SessionHelper.getCurrentUser().getUsername(), "error", leaving.getUser());
            messageService.createMessage(message1);
            messageService.createMessage(message2);
        }
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
