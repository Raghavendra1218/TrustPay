package com.wallet.service;

import com.wallet.dto.AddMoneyDto;
import com.wallet.entity.Wallet;

public interface WalletService {
	Wallet registerNewWallet(int userId);
	Wallet addMoney(AddMoneyDto  addMoneyDto);
}
