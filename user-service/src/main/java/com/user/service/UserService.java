package com.user.service;

import com.user.entity.User;

public interface UserService {
	User registerUser(User user);
	User searchById(int id);
	User validateUser(String username, String password);

}
