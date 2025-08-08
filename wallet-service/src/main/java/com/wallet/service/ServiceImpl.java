package com.wallet.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.wallet.repository.OtpRepository;
import com.wallet.service.MessageSender;


import com.wallet.dto.AddMoneyDto;
import com.wallet.dto.UserDto;
import com.wallet.entity.Wallet;
import com.wallet.entity.WalletStatus;
import com.wallet.exception.ApplicationException;
import com.wallet.repository.WalletRepository;
import com.wallet.entity.Otp;
@Service
public class ServiceImpl implements WalletService {
	@Autowired
	private MessageSender msgSender;
	@Autowired
	private WalletRepository walletRepo;
	 @Autowired
	    private OtpRepository otpRepository;
	  public ResponseEntity<String> sendOtp(int userId,String token) {
		  boolean userChk = verifyUser(userId,token);

		    if (userChk) {
		        if (walletRepo.findByUserId(userId).isEmpty()) {
		            String email=getEmail(userId,token);
		            if(email.length()==0)
		            	throw new RuntimeException("Email not available for the user please add!");
		            String otp = String.valueOf(new Random().nextInt(900000) + 100000);
		            Otp otpEntity = otpRepository.findByEmail(email).orElse(new Otp());
		            otpEntity.setEmail(email);
		            otpEntity.setOtp(otp);
		            otpRepository.save(otpEntity);
		            msgSender.sendOtp(otpEntity.getEmail(),otpEntity.getOtp());
		            
		            return  ResponseEntity.ok("OTP sent to " + email);
		        } else {
		            throw new RuntimeException("Wallet already exists for user");
		        }
		    } else {
		        throw new RuntimeException("User does not exist");
		    }
	    }
	public ResponseEntity<String> registerNewWallet(int userId,String passcode,String inputOtp,String token) {
		String email=getEmail(userId,token);
		Optional<Otp> optionalOtp = otpRepository.findByEmail(email);
        if (optionalOtp.isEmpty())
        	throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No pending wallet registration against the user send OTP FIRST");
        	if(!optionalOtp.get().getOtp().equals(inputOtp)) {
        	throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid OTP");

        }
        	
	            Wallet w = new Wallet();
	            w.setStatus(WalletStatus.ACTIVE);
	            w.setUserId(userId);
	            w.setBalance(500);
	            w.setPasscode(passcode);
	            walletRepo.save(w);
	            return ResponseEntity.ok("Wallet created successfully");
	        
	    
	}


	public Wallet addMoney(AddMoneyDto addMoneyDto) {
		return null;
	}
	boolean verifyUser(int userId, String token) {
	    RestTemplate template = new RestTemplate();
	    String url = "http://localhost:9000/users/" + userId;

	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", "Bearer " + token);

	    HttpEntity<Void> entity = new HttpEntity<>(headers);

	    try {
	        ResponseEntity<UserDto> response = template.exchange(
	            url,
	            HttpMethod.GET,
	            entity,
	            UserDto.class
	        );
	        return response.getStatusCode().is2xxSuccessful() && response.getBody() != null;
	    } catch (HttpClientErrorException e) {
	        return false;
	    }
	}

	String getEmail(int userId, String token) {
	    RestTemplate template = new RestTemplate();
	    String url = "http://localhost:9000/users/" + userId;

	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", "Bearer " + token);

	    HttpEntity<Void> entity = new HttpEntity<>(headers);

	    try {
	        ResponseEntity<UserDto> response = template.exchange(
	            url,
	            HttpMethod.GET,
	            entity,
	            UserDto.class
	        );
	        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
	            return response.getBody().getEmail();
	        }
	    } catch (HttpClientErrorException e) {
	        return "";
	    }

	    return "";
	}

    public Wallet searchById(int id) {
		
		return walletRepo.findById(id).orElseThrow(()-> new ApplicationException("Wallet not found"));
	}
	public void updateBalance(float amount,int id) {
		Wallet wallet=searchById(id);
		wallet.setBalance(wallet.getBalance() + amount);
	    walletRepo.save(wallet);
	}
	
public Integer searchByuserId(Integer id) {
		
		Wallet w= walletRepo.findByUserId(id).orElseThrow(()-> new ApplicationException("Wallet not found"));
		return w.getWalletId();
	}


}
