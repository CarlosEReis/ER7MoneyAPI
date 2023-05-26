package com.carloser7.er7money.api.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.carloser7.er7money.api.property.Er7moneyApiProperty;

@Configuration
public class MailConfig {
	
	@Autowired
	private Er7moneyApiProperty property;

	@Bean
	public JavaMailSender javaMailSender() {
		Properties properties = new Properties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", true);
		properties.put("mail.smtp.connectiontimeout", 10000);
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setJavaMailProperties(properties);
		mailSender.setHost(this.property.getMail().getHost());
//		mailSender.setPort(this.property.getMail().getPort());
		mailSender.setUsername(this.property.getMail().getUsername());
		mailSender.setPassword(this.property.getMail().getPassword());

		return mailSender;
	}
}
