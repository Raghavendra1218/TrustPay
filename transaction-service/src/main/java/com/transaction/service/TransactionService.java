package com.transaction.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.transaction.dto.TransactionDto;
import com.transaction.dto.WalletDto;


@Service
public class TransactionService {
	
	private Logger log=LoggerFactory.getLogger(TransactionService.class);
	@Autowired
	private RestTemplate restTemplate;
	public WalletDto fundTransfer(  TransactionDto transactionDto) {
		log.info("fund tranfering from {} and to {}",transactionDto.getFromWalletId(),transactionDto.getToWalletId());
		String url="http://wallet-service/wallet/"+transactionDto.getFromWalletId();
		//call to user service
		WalletDto walletDto=	restTemplate.getForObject(url, WalletDto.class);
		return walletDto;
	}
	
}