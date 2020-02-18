package com.bridgelabz.fundoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.dto.ForgotPasswordDto;
import com.bridgelabz.fundoo.dto.LoginDto;
import com.bridgelabz.fundoo.dto.ResetPasswordDto;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.service.UserService;

@RestController
@RequestMapping("/home")
public class UserController {
	@Autowired
	private UserService service;
	
	@PostMapping("/register")
	public String newRegistration(@RequestBody User registerEntry) {
		return service.newRegistration(registerEntry);
	}

	@PostMapping("/login")
	public String newLogin(@RequestBody LoginDto login) {
		return service.newLogin(login);

	}
	@PostMapping("/forgotpassword")
	public String passWordForgot(@RequestBody ForgotPasswordDto forgotPassWord) {
		return service.forgotPass(forgotPassWord);
		
	}
	@PostMapping("/resetpassword")
	public String resetPassword(@RequestBody ResetPasswordDto resetPassWord) {
		return service.resetPass(resetPassWord);
		
		
	}
}
