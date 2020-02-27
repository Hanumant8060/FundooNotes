package com.bridgelabz.fundoo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.dto.ForgotPasswordDto;
import com.bridgelabz.fundoo.dto.LoginDto;
import com.bridgelabz.fundoo.dto.ResetPasswordDto;
import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.UserRepository;
import com.bridgelabz.fundoo.utility.JMS;
import com.bridgelabz.fundoo.utility.TokenService;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private TokenService tokenservice;

	@Autowired
	private JMS mailsender;

	public String newRegistration(User registerEntry) {
		String emailId = registerEntry.getEmail();
		Optional<User> newUser = repository.findByEmail(emailId);
		if (newUser.isEmpty()) {
			repository.save(registerEntry);
		} else {
			throw new UserException("user already registered");
		}
		return "add user";
	}

	public String newLogin(LoginDto loginDto) {
		Optional<User> user = repository.findByEmail(loginDto.getEmail());
		if (user.isEmpty()) {
			throw new UserException("user not present");
		}
		if (!user.get().getPassword().equals(loginDto.getPassword())) {
			throw new UserException("Password-Mismatch");
		}
		String userToken = tokenservice.createToken(user.get().getEmail());
		mailsender.sendEmail(loginDto.getEmail(), userToken);
		return "login succesfully " + userToken;

	}

	public String forgotPass(ForgotPasswordDto forgotPassWord) {
		Optional<User> user = repository.findByEmail(forgotPassWord.getEmail());
		if (user.isEmpty()) {
			throw new UserException("User does not exist");
		} else {
			mailsender.sendEmail(forgotPassWord.getEmail(), "http://localhost:8080/home/forgotpassword");
		}
		return "check link on your mail";

	}

	public String resetPass(ResetPasswordDto resetPassWord) {
		Optional<User> user = repository.findByEmail(resetPassWord.getEmail());
		if (user.isPresent()) {
			if (user.get().getPassword().equals(user.get().getConfirm_password())) {
				user.get().setPassword(resetPassWord.getPassword());
				user.get().setConfirm_password(resetPassWord.getConfirm_password());
				repository.save(user.get());
				mailsender.sendEmail(resetPassWord.getEmail(), "password reset succesfully");
			}
		} else {
			throw new UserException("User not exist");
		}
		return "password reset";
	}

}
