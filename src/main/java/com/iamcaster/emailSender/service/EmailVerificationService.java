package com.iamcaster.emailSender.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iamcaster.emailSender.domain.EmailVerification;
import com.iamcaster.emailSender.dto.EmailSendDto;
import com.iamcaster.emailSender.repository.EmailVerificationRepository;

@Service
public class EmailVerificationService {

	@Autowired
	private EmailVerificationRepository emailVerificationRepository;
	@Autowired
	private EmailSendService emailSendService;
    
	public boolean emailCodeVerifier(String email, int code) {
		EmailVerification emailVerification = new EmailVerification();
		emailVerification = emailVerificationRepository.selectByEmail(email);
		if(emailVerification == null) {
			return false;
		}
		int rightCode = emailVerification.getEmailCode();
		return (rightCode==code);
	}
	
    public int sendVerificationEmail(String email) {
    	//코드 생성
    	String code = Integer.toString(generateEmailCode());
    	//재발행 여부 확인
    	int result = 0;
    	EmailVerification emailVerification = new EmailVerification();
    	emailVerification = emailVerificationRepository.selectByEmail(email);
    	if(emailVerification == null) {
    		EmailVerification emailVerificationForFirstUser = new EmailVerification();
    		//emailverification 테이블에 insert
    		emailVerificationForFirstUser.setEmail(email);
    		emailVerificationForFirstUser.setEmailCode(Integer.parseInt(code));
    		result = emailVerificationRepository.insertEmailCode(emailVerificationForFirstUser);
    	}else {
    		emailVerification.setEmailCode(Integer.parseInt(code));
    		result = emailVerificationRepository.updateCodeByEmail(emailVerification);
    	}
    	//sendEmail 메소드 호출
    	String type = "emailCode";
    	String path = "/static/thymeleaf-templates/"+type+".html";
    	EmailSendDto emailSendDto = new EmailSendDto();
    	emailSendDto.setType(type);
    	emailSendDto.setSubject("[나도캐스터] 인증번호가 도착했어요.");
    	emailSendDto.setContext(emailSendService.setContext(type,code));
    	emailSendDto.setCode(code);
    	emailSendDto.setAddressTo(email);
    	emailSendDto.setAddressFrom("joeldev@naver.com");
    	emailSendService.sendEmail(emailSendDto,"emailCode");
    	
        return result;
    }
	
	public int generateEmailCode() {
		
		Random random = new Random();
		int digit = random.nextInt(9999 - 1001) + 1000;
		
		return digit;
	}
	
}
