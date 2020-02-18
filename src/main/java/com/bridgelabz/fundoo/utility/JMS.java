package com.bridgelabz.fundoo.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class JMS {
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendEmail(String email, String token) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("hanumantdalvi028@gmail.com");
        mail.setTo(email);
        mail.setSubject("Testing a token");
        mail.setText(token);
        javaMailSender.send(mail);

    }
}
