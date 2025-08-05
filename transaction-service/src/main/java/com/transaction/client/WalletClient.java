package com.transaction.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "wallet-service", url = "http://localhost:8371/wallet-service") 
public interface WalletClient {

    @GetMapping("/wallet/balance")
    Double getBalance(@RequestParam int userId);

    @PostMapping("/wallet/updateBalance")
    boolean updateBalance(@RequestParam float amount,@RequestParam int userId );

	 @GetMapping("/wallet/verify-passcode")
	   public boolean verifyPassCode(@RequestParam String passcode,@RequestParam int userId);
}

