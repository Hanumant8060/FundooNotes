package com.bridgelabz.fundoo.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.dto.ForgotPasswordDto;
import com.bridgelabz.fundoo.dto.LoginDto;
import com.bridgelabz.fundoo.dto.ResetPasswordDto;
import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.UserRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.utility.JMS;
import com.bridgelabz.fundoo.utility.TokenService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

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
//		mailsender.sendEmail(loginDto.getEmail(), userToken);
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
	public String uploadProPic(String token, MultipartFile file) throws IOException {
		String emailid = tokenservice.getUserIdFromToken(token);
		Optional<User> user = repository.findByEmail(emailid);
		if (user.isPresent()) {
			System.out.println("hey");
			File uploadFile = new File(file.getOriginalFilename());
			System.out.println(uploadFile);
			BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
			outStream.write(file.getBytes());
			outStream.close();
			Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", "yelloracaves", "api_key",
					"982216137489194", "api_secret", "rsDRuXLXg5n1HTZstmcckBxoOxY"));
			System.out.println(ObjectUtils.emptyMap());
			Map<?, ?> uploadProfile;
			//uploadProfile = cloudinary.uploader().upload(uploadFile, ObjectUtils.emptyMap());
			uploadProfile = cloudinary.uploader().upload(uploadFile, ObjectUtils.emptyMap());
			

//			user.get().setImage(uploadProfile.get("secure_url").toString());
			user.get().setImage(uploadProfile.get("secure_url").toString());
			repository.save(user.get());
			return "profile picture set successfully";
                                                                                                                                                                                                                                                                                                                                                                                                                                    
		}
		return null;

	}

}
