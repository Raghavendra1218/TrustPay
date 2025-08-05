package com.wallet.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wallet.entity.Otp;
public interface OtpRepository extends JpaRepository<Otp, Integer> {
    Optional<Otp> findByEmail(String email);
}
