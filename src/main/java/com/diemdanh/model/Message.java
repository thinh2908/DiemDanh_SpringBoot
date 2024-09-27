package com.diemdanh.model;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String message;
    public String type;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    public Users user;

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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Message(String message, String type, Users user) {
        this.message = message;
        this.type = type;
        this.user = user;
    }

    public Message(Message message) {
        this.message = message.getMessage();
        this.type = message.getType();
        this.user = message.getUser();
    }

    public Message() {
    }
}
