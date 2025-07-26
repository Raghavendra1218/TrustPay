package com.user.service;

import com.user.entity.User;

public interface UserService {
	User searchById(int id);
	User validateUser(String username, String password);
	public boolean verifyOtpAndRegister(String email, String inputOtp);
    public String sendOtp(User user);
}
