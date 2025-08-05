package com.user.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.user.entity.Otp;

import jakarta.transaction.Transactional;

public interface OtpRepository extends JpaRepository<Otp, Integer> {
    Optional<Otp> findByEmail(String email);
    @Modifying
    @Transactional
    @Query("DELETE FROM Otp o WHERE o.createdAt <= :expiryTime")
    void deleteExpiredOtps(LocalDateTime expiryTime);
}
