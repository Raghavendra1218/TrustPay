package com.transaction.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Entity
@Data
public class Transaction {
	@Id
    @GeneratedValue
    private int transactionId;
;
    private int senderId;
    private int receiverId;
    private float amount;

    private TransactionStatus status;
    @Column(nullable = false)
    private LocalDateTime timestamp;
    private String remarks;
    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();
        
        
}}

