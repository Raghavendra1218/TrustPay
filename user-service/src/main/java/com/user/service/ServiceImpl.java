package com.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.entity.User;
import com.user.exception.ApplicationException;
import com.user.repository.UserRepository;
@Service
public class ServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	public User registerUser(User user) {
		User existingUser=userRepo.findByUserName(user.getUserName());
		if(existingUser!=null) {
			throw new ApplicationException("User already present");
		}
		return userRepo.save(user);
	}
	public User searchById(int id) {
		//why optional ????
		return userRepo.findById(id).orElseThrow(()-> new ApplicationException("User not found"));
	}


}
