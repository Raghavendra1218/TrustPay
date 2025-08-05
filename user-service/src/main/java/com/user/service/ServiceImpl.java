package com.user.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.dto.UserDto;
import com.user.entity.Otp;
import com.user.entity.User;
import com.user.exception.ApplicationException;
import com.user.repository.OtpRepository;
import com.user.repository.UserRepository;

@Service
public class ServiceImpl implements UserService {
	@Autowired
	private MessageSender msgSender;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private UserRepository userRepository;


    public String sendOtp(User user) {
        Optional<User> existing = userRepository.findByEmail(user.getEmail());

        if (existing.isPresent()) {
            throw new ApplicationException("Email already registered and verified.");
        }

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        Otp otpEntity = otpRepository.findByEmail(user.getEmail()).orElse(new Otp());
        otpEntity.setEmail(user.getEmail());
        otpEntity.setOtp(otp);
        otpRepository.save(otpEntity);

       
        msgSender.sendOtp(otpEntity.getEmail(),otpEntity.getOtp());
        
        return "OTP sent to " + user.getEmail();
    }

    public boolean verifyOtpAndRegister(User user, String inputOtp) {
        Optional<Otp> optionalOtp = otpRepository.findByEmail(user.getEmail());
        if (optionalOtp.isEmpty() || !optionalOtp.get().getOtp().equals(inputOtp)) {
            return false;
        }

       
        userRepository.save(user);

        otpRepository.delete(optionalOtp.get());
        UserDto userDto= new UserDto();
		BeanUtils.copyProperties(user, userDto);
        msgSender.sendNotification(userDto);
        return true;
    }

    public User validateUser(String userName, String password) {
        User user = userRepository.findByUserName(userName);
        return (user != null && user.getPassword().equals(password)) ? user : null;
    }

    public User searchById(int id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ApplicationException("User not found"));
    }


}
