package com.transaction.dto;

import lombok.Data;

@Data

public class TransactionDto {
	private int fromWalletId;
	private int toWalletId;
	private float transferAmount;
	
}
