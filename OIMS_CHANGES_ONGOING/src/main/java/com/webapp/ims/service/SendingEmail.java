package com.webapp.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendingEmail {

	
	 @Autowired
	 private JavaMailSender javaMailSender;

	public boolean sentEmail( String subject, String message,String... address) {
		boolean bool = false;
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(address);// "swapnil.singh@velocis.co.in", "sachin.sachan@velocis.co.in",
								// "singhsachinsachan@gmail.com");

			msg.setSubject(subject);
			msg.setText(message);

			javaMailSender.send(msg);
			bool = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return bool;
	}

}
