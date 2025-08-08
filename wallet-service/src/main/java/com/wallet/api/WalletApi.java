package com.wallet.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.wallet.dto.WalletRegisterRequest;
import com.wallet.entity.Wallet;
import com.wallet.security.JwtUtil;
import com.wallet.service.WalletService;



@RestController
@RequestMapping("/wallet")
public class WalletApi {
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private WalletService walletService;
	@PostMapping("/register/verify")
	public ResponseEntity<String> registerNewWallet(
	    @RequestHeader("Authorization") String authHeader,@RequestBody WalletRegisterRequest requestBody) {

	    String token = authHeader.substring(7); 
	    int userId = jwtUtil.extractUserId(token);

	    if (!requestBody.getPasscode().equals(requestBody.getConfirmPasscode())) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passcodes do not match");
	    }

	    return walletService.registerNewWallet(userId, requestBody.getPasscode(), requestBody.getOtp(), token);
	}

	@GetMapping("/register")
	public ResponseEntity<String> sendOtp(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7); 
		int userId= jwtUtil.extractUserId(token);
		
	return	walletService.sendOtp(userId,token);
}
	@GetMapping("/{id}")
	public ResponseEntity<Wallet> getWallet(@PathVariable("id") int id) {
		Wallet w=walletService.searchById(id);
		return new ResponseEntity<>(w,HttpStatus.OK);
	}
	@GetMapping("walletId")
	public Integer getWalletId( @RequestParam Integer userId) {
		
		return  walletService.searchByuserId(userId);
	}
	
	@GetMapping("/test")
	public String getWallet() {
		
		return "success";
	}
	@GetMapping("/getbalance")
	public ResponseEntity<Float> checkBalance(@RequestHeader("Authorization") String authHeader) {
	    String token = authHeader.substring(7); 
	    int userId = jwtUtil.extractUserId(token);

	    Wallet wallet = walletService.searchById(userId);
	    if (wallet == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found for user");
	    }

	    return new ResponseEntity<>(wallet.getBalance(), HttpStatus.OK);
	}

	 @GetMapping("/balance")
	   public Float getBalance(@RequestParam int userId) {
		 Wallet wallet = walletService.searchById(userId);
		 return wallet.getBalance();
	 }
	 @PostMapping("/updateBalance")
	 public boolean updateBalance(@RequestParam float amount, @RequestParam int walletId) {
	     try {
	         walletService.updateBalance(amount, walletId);
	         return true;
	     } catch (Exception e) {
	         return false;
	     }
	 }
	 @GetMapping("/verify-passcode")
	   public boolean verifyPassCode(@RequestParam String passcode,@RequestParam int userId) {
		 Wallet wallet = walletService.searchById(userId);
		 System.out.println(passcode+" "+wallet.getPasscode());
		 return passcode.equals(wallet.getPasscode());
	 }
}
