package com.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wallet.dto.AddMoneyDto;
import com.wallet.dto.UserDto;
import com.wallet.entity.Wallet;
import com.wallet.entity.WalletStatus;
import com.wallet.repository.WalletRepository;
@Service
public class ServiceImpl implements WalletService {
	@Autowired
	private WalletRepository walletRepo;
	public Wallet registerNewWallet(int userId) {
		boolean userChk=verifyUser(userId);
		if(userChk==true) {
			Wallet w=new Wallet();
			w.setStatus(WalletStatus.ACTIVE);
			w.setUserId(userId);
			w.setWalletBalance(500);
			walletRepo.save(w);
			return w;
		}
	throw new RuntimeException("user already exits");
	}

	public Wallet addMoney(AddMoneyDto addMoneyDto) {
		return null;
	}
	boolean verifyUser(int userId) {
		RestTemplate template=new RestTemplate();
		//currently no load balancing is used ////
		String url="http://localhost:9000/users/"+userId;
		UserDto user=template.getForObject(url, UserDto.class);
		if(user!=null) {
			return true;
		}
		else return false;
	}


}
