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
	@PostMapping(consumes = {"application/xml","application/json"})
public ResponseEntity<User> registerNewUser( @RequestBody @Valid User user){
	User u=userService.registerUser(user);
	return new ResponseEntity<>(u,HttpStatus.CREATED);
}
	@GetMapping("/hi")
	public String test() {
		return "hi";
	}
	@PostMapping(value = "/login", consumes = {"application/json"})
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        User user = userService.validateUser(request.getUsername(), request.getPassword());
        if (user != null) {
            String token = jwtUtil.generateToken(user.getUserName());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

	@GetMapping("/{id}")
public ResponseEntity<User> searchById( @PathVariable("id") int id){
	User u=userService.searchById(id);
	return new ResponseEntity<>(u,HttpStatus.OK);
}
}
