package com.transaction.service;

import com.transaction.client.WalletClient;
import com.transaction.entity.Transaction;
import com.transaction.entity.TransactionStatus;
import com.transaction.exception.ApplicationException;
import com.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private WalletClient walletClient;

    @Autowired
    private TransactionRepository transactionRepository;

    public ResponseEntity<?> fundTransfer(Integer senderId, Integer receiverWalletId, Float amount,String passcode) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
       
       Integer senderWalletId=walletClient.getWalletId(senderId);
       
       Transaction transaction = new Transaction();
       transaction.setSenderId(senderWalletId);
       transaction.setReceiverId(receiverWalletId);
       transaction.setAmount(amount);
       transaction.setStatus(TransactionStatus.SUCCESS);
        try {
        	
        	if(!walletClient.verifyPassCode(passcode, senderWalletId))
        	{ 
        		transaction.setStatus(TransactionStatus.FAILED);
        		 transaction.setRemarks("Wrong passcode");
        		 transaction=transactionRepository.save(transaction);
                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transaction);
        	}
        }
        catch(Exception e) {
        	System.out.println(e);
        	transaction.setStatus(TransactionStatus.FAILED);
        	transaction.setRemarks("Unable to do transaction please try later");
        	transaction=transactionRepository.save(transaction);
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(transaction);
        }
        if(receiverWalletId.equals(senderWalletId))
           	return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Reciver and sender can't be same");
        try {
        Double senderBalance = walletClient.getBalance(senderWalletId);
        
        if (senderBalance == null || senderBalance < amount) {
        	System.out.println("hi");
        	 transaction.setStatus(TransactionStatus.FAILED);
        	 transaction.setRemarks("Insufficient funds");
        	 transaction=transactionRepository.save(transaction);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transaction);
        }
        }
        catch(Exception e) {
        	System.out.println(e);
        	transaction.setStatus(TransactionStatus.FAILED);
        	transaction.setRemarks("Unable to do transaction please try later");
        	transaction=transactionRepository.save(transaction);
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(transaction);
        }
        
        try {
            walletClient.getBalance(receiverWalletId);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Receiver wallet with ID " + receiverWalletId + " not found.");
        }
        
        boolean debitSuccess = walletClient.updateBalance(-amount, senderWalletId);
        boolean creditSuccess=false;
        if(debitSuccess)
         creditSuccess = walletClient.updateBalance(amount, receiverWalletId);

        if (!debitSuccess && !creditSuccess) {
        	transaction.setStatus(TransactionStatus.FAILED);
        	transaction.setRemarks("Unable to do transaction please try later");
        	transaction=transactionRepository.save(transaction);
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(transaction);
        }
        if(!creditSuccess) {

        	transaction.setStatus(TransactionStatus.PENDING);
        	transaction.setRemarks("Money is debited but Transaction pending! We are trying to update transaction please be patient");
        	transaction=transactionRepository.save(transaction);
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(transaction);
        }

        
        transaction.setRemarks("Success");
        transaction=transactionRepository.save(transaction);

        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }
}
