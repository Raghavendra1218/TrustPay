package com.transaction.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	@Id
    @GeneratedValue
    private Integer transactionId;
;
    private Integer senderId;
    private Integer receiverId;
    private Float amount;

    private TransactionStatus status;
    @Column(nullable = false)
    private LocalDateTime timestamp;
    private String remarks;
    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();
        
        
}}

