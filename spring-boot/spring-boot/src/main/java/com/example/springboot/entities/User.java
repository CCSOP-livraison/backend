package com.example.springboot.entities;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String lastname;
    private String firstname;
    private String address;
    private String zipcode;
    private String locate;
    private String email;
    private String phoneNumber;
    private String password;
}
