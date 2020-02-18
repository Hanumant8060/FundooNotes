package com.bridgelabz.fundoo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.dto.LoginDto;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.UserRepository;

@Service
public class LoginService {
	@Autowired
	private UserRepository loginrepository;

	public String newLogin(LoginDto loginDto) {
		Optional<User> user = loginrepository.findByEmail(loginDto.getEmail());
		if (user.isEmpty()) {
			System.out.println("username not valid ");
			return "username is invalid";
		}
		if (!user.get().getPassword().equals(loginDto.getPassword())) {
			System.out.println("password not match");
			return "password is invalid ";
		}

		return "login succesfully";

	}

}
