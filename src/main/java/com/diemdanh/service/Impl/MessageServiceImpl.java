package com.diemdanh.service.Impl;

import com.diemdanh.model.Message;
import com.diemdanh.model.Users;
import com.diemdanh.repo.MessageRepository;
import com.diemdanh.service.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements IMessage {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message createMessage(Message message) {
        messageRepository.save(message);
        return message;
    }

    @Override
    public Page<Message> findByUser(Users user, Pageable pageable) {
        return messageRepository.findByUser(user,pageable);
    }

    @Override
    public Long countAll() {
        return messageRepository.count();
    }
}
