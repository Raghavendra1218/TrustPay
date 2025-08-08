package com.wallet.service;

import org.springframework.http.ResponseEntity;

import com.wallet.dto.AddMoneyDto;
import com.wallet.entity.Wallet;

public interface WalletService {
	ResponseEntity<String> registerNewWallet(int userId,String passcode,String inputOtp,String token);
	Wallet addMoney(AddMoneyDto  addMoneyDto);
	 Wallet searchById(int id);
	  ResponseEntity<String> sendOtp(int userId,String token);
	  void updateBalance(float amount,int userId);
	  Integer searchByuserId(Integer id);
}
