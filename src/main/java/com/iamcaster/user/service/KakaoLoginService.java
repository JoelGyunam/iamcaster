package com.iamcaster.user.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.iamcaster.common.Encrypt;
import com.iamcaster.user.domain.UserInfo;
import com.iamcaster.user.repository.UserInfoJpaRepository;

public class KakaoLoginService {

	@Autowired
	private UserInfoJpaRepository userInfoJpaRepository;
	@Autowired
	private UserInfoService userInfoService;

	// 카카오로그인 메인 메소드

	// 카카오로그인유저 인서트 메소드
	public UserInfo kakaoRegistration(String Email, int NickID, int RGID) {
		
		if(userInfoService.ifRegisteredEmail(Email)) {
			return null;
		} else {
			UserInfo userInfo = new UserInfo();
			userInfo.setEmail(Email);
			userInfo.setIfKakao(true);
			userInfo.setNickID(NickID);
			userInfo.setRGID(RGID);
			userInfo.setIfOptionalTermsAgreed(false);
			
			//난수 비밀번호 생성
			Random random = new Random();
			String newPassword = "";
			
			for(int i = 0; i < 6; i++) {
				int digitOrLetter = random.nextInt(2);
				if(digitOrLetter==0) {
					int digit = random.nextInt(1, 10);
					newPassword += Integer.toString(digit);
				} else {
					char letter = (char)random.nextInt(65, 90);
					newPassword += letter;
				}
			}
			
			String salt = Encrypt.getSalt();
			String encPassword = Encrypt.getEncrypt(newPassword, salt);
			userInfo.setSalt(salt);
			userInfo.setPassword(encPassword);
			
			UserInfo result = userInfoJpaRepository.save(userInfo);
			if(result != null) {
				return result;
			} else
				return null;
			
		}
		

	}

	//

}
