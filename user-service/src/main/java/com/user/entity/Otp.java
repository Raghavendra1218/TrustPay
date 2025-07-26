package com.user.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String otp;

}
