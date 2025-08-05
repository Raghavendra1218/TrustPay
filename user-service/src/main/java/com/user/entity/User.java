package com.user.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Entity
@Data
public class User {
	@Id
	@GeneratedValue
	private int userId;
		@NotNull(message = "UserName cannot be blank/null")
		@NotBlank(message = "UserName cannot be blank/null")
		@Column(unique = true)
	private String userName;
		@NotNull(message = "Password cannot be blank/null")
		@NotBlank(message = "Password cannot be blank/null")
		// @Length (value =8,message = "password have atleast 8 chars")
		
	private String password;
		@Min(value =2000000000,message = "Invalid phone number")
	private long phoneNo;
	private String address;
	@Email
	@NotNull(message = "email cannot be blank/null")
	@NotBlank(message = "email cannot be blank/null")
	private String email;
	

}
