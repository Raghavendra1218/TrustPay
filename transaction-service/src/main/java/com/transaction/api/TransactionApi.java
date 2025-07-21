package com.transaction.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.dto.TransactionDto;
import com.transaction.dto.WalletDto;

@RestController
public class TransactionApi {
	@PostMapping("/transfer")
	public WalletDto fundTransfer(@RequestBody TransactionDto transactionDto) {
		
	}
}
