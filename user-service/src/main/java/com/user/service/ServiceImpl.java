package com.user.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.dto.UserDto;
import com.user.entity.User;
import com.user.exception.ApplicationException;
import com.user.repository.UserRepository;
@Service
public class ServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private MessageSender msgSender;
	public User registerUser(User user) {
		User existingUser=userRepo.findByUserName(user.getUserName());
		if(existingUser!=null) {
			throw new ApplicationException("User already present");
		}
		UserDto userDto= new UserDto();
		BeanUtils.copyProperties(user, userDto);
		msgSender.sendNotification(userDto);
		return userRepo.save(user);
	}
	public User searchById(int id) {
		
		return userRepo.findById(id).orElseThrow(()-> new ApplicationException("User not found"));
	}


}
