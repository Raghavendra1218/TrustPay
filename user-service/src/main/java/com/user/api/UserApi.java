package com.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.dto.LoginRequest;
import com.user.dto.VerifyRequest;
import com.user.entity.Otp;
import com.user.entity.User;
import com.user.security.JwtUtil;
import com.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserApi {
	@Autowired
	private UserService userService;
	@Autowired
    private JwtUtil jwtUtil;
	@PostMapping(value = "/register",consumes = {"application/xml","application/json"})
	 public String sendOtp(@RequestBody User user) {
        return userService.sendOtp(user);
    }
	@GetMapping("/hi")
	public String test() {
		return "hi";
	}
	@PostMapping("register/verify-otp")
	 public String verifyAndRegister(@RequestBody VerifyRequest request) {
        boolean success = userService.verifyOtpAndRegister(request.getEmail(), request.getOtp());
        return success ? "User registered successfully" : "Invalid OTP";
    }

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest request) {
	    User user = userService.validateUser(request.getUserName(), request.getPassword());
	   System.out.println(request.getUserName()+" "+request.getPassword());
	    if (user == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	    }
	    if (!"VERIFIED".equalsIgnoreCase(user.getStatus())) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please verify your email via OTP before logging in.");
	    }

	    String token = jwtUtil.generateToken(user.getUserName());
	    return ResponseEntity.ok(token);
	}

	@GetMapping("/{id}")
public ResponseEntity<User> searchById( @PathVariable("id") int id){
	User u=userService.searchById(id);
	return new ResponseEntity<>(u,HttpStatus.OK);
}
}
