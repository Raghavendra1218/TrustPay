package com.wallet.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.entity.Wallet;
import com.wallet.service.WalletService;



@RestController
@RequestMapping("/wallet")
public class WalletApi {
	@Autowired
	private WalletService walletService;
	@PostMapping("/{userId}")
	public Wallet registerNewWallet(@PathVariable("userId")  int userId) {
	return	walletService.registerNewWallet(userId);
}
	@GetMapping("/{id}")
	public ResponseEntity<Wallet> getWallet(@PathVariable("id") int id) {
		Wallet w=walletService.searchById(id);
		return new ResponseEntity<>(w,HttpStatus.OK);
	}
}
