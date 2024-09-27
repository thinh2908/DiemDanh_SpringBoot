package com.diemdanh.response;

import com.diemdanh.model.Message;

public class MessageResponse {
    private Long id;
    private String message;
    private String type;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public MessageResponse(Message message) {
        this.id = message.getId();
        this.message = message.getMessage();
        this.type = message.getType();
        this.userId = message.getUser().getId();
    }
}
