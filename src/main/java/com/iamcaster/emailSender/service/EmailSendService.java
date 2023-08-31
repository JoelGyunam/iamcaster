package com.iamcaster.emailSender.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.iamcaster.emailSender.dto.EmailSendDto;

@Service
public class EmailSendService {

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	public void sendEmail(EmailSendDto emailSendDto, String type) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		try {
			MimeMessageHelper mimeMessgaeHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			mimeMessgaeHelper.setTo(emailSendDto.getAddressTo());
			mimeMessgaeHelper.setFrom(emailSendDto.getAddressFrom());
			mimeMessgaeHelper.setSubject(emailSendDto.getSubject());
			mimeMessgaeHelper.setText(emailSendDto.getContext(),true);
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public String setContext(String type, String code) {
		Context context = new Context();
		context.setVariable("code", code);
		return templateEngine.process(type,context);
	}
	
}
