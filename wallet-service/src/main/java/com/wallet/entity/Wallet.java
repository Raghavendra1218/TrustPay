package com.wallet.entity;

import java.time.LocalDateTime;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Entity
@Data
public class Wallet {
	@Id
	@GeneratedValue
private int walletId;
private float balance;
@Column(nullable = false, updatable = false)
private LocalDateTime createdDate;

@Column(nullable = false)
private LocalDateTime lastUpdated;
private WalletStatus status;
@Column(unique = true)
private int userId;
@Size(min = 6, message = "Passcode must be at least 6 characters")
private String passcode;
@PrePersist
public void prePersist() {
    LocalDateTime now = LocalDateTime.now();
    this.createdDate = now;
    this.lastUpdated = now;
}

@PreUpdate
public void preUpdate() {
    this.lastUpdated = LocalDateTime.now();
}
}