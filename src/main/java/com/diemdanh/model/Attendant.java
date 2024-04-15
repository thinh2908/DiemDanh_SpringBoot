package com.diemdanh.model;


import javax.persistence.*;

@Entity
@Table(name = "attendant")
public class Attendant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String checkIn;
    private String checkOut;
    private Long month;
    private Long count;

}
