package com.wallet.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallet.entity.Wallet;
import com.wallet.entity.WalletStatus;

public interface WalletRepository extends JpaRepository<Wallet,Integer>{
	Optional<Wallet> findByUserId(int userId); 
	List<Wallet> findByLastUpdatedBeforeAndStatus(LocalDateTime cutoffDate, WalletStatus status);
}
