package com.diemdanh.controller;

import com.diemdanh.Utils.SessionHelper;
import com.diemdanh.base.BaseFunction;
import com.diemdanh.model.Leaving;
import com.diemdanh.model.Message;
import com.diemdanh.model.Users;
import com.diemdanh.response.LeavingResponse;
import com.diemdanh.response.MessageResponse;
import com.diemdanh.service.Impl.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageServiceImpl messageService;

    @GetMapping("")
    public ResponseEntity<?> listAllMessage(@RequestParam(required = false,value = "sort") String sort,
                                         @RequestParam(required = false,value = "range") String range
    ){
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
        Page<Message> pageMessage;
        pageMessage= messageService.findByUser(currentUser,paging);

        List<Message> messageList;
        messageList = pageMessage.getContent();

        List<MessageResponse> messageResponses = messageList.stream()
                .map(MessageResponse::new).collect(Collectors.toList());


        //Xu ly header

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Range",
                "message "+ rangeList.get(0) + "-" + rangeList.get(1) + "/" + pageMessage.getTotalElements());
        return  ResponseEntity.ok().headers(responseHeaders).body(messageResponses);
    }
}
