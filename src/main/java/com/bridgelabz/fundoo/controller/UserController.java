package com.bridgelabz.fundoo.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.bridgelabz.fundoo.dto.ForgotPasswordDto;
import com.bridgelabz.fundoo.dto.LoginDto;
import com.bridgelabz.fundoo.dto.ResetPasswordDto;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.service.UserService;
import com.bridgelabz.fundoo.utility.TokenService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/home")
public class UserController {
	@Autowired
	private UserService service;
	@Autowired
	TokenService tokenService;

	@PostMapping("/register")
	public Response newRegistration(@Valid @RequestBody User registerEntry) {
		service.newRegistration(registerEntry);
		return new Response("Registered succesfully", "OK", 200);
	}

	@PostMapping("/login")
	public Response newLogin(@Valid @RequestBody LoginDto login) {
		String token = tokenService.createToken(login.getEmail());
		service.newLogin(login);
		return new Response("login succesfully", token, 200);

	}

	@PostMapping("/forgotpassword")
	public Response passWordForgot(@RequestBody ForgotPasswordDto forgotPassWord) {
		service.forgotPass(forgotPassWord);
		return new Response("Reset password link send on your registerd email", "OK", 200);
	}

	@PostMapping("/resetpassword")
	public Response resetPassword(@Valid @RequestBody ResetPasswordDto resetPassWord) {
		service.resetPass(resetPassWord);
		return new Response("password reset successfully", "OK", 200);

	}

	@PostMapping(value = "/uplaodImage", consumes = "multipart/form-data")
	public Response uplaodImage(@RequestHeader String token, MultipartFile file) throws IOException {
		return service.uploadProPic(token, file);

	}

}
