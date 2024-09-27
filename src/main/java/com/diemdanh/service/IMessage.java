package com.diemdanh.service;

import com.diemdanh.model.Message;
import com.diemdanh.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMessage {
    public Page<Message> findByUser(Users user, Pageable pageable);
    public Message createMessage(Message message);
    public Long countAll();
}
