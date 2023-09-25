package com.iamcaster.user.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iamcaster.common.Encrypt;
import com.iamcaster.emailSender.dto.EmailSendDto;
import com.iamcaster.emailSender.service.EmailSendService;
import com.iamcaster.regional.userregion.service.UserRegionService;
import com.iamcaster.user.domain.UserInfo;
import com.iamcaster.user.dto.UserInfoOverral;
import com.iamcaster.user.repository.UserInfoRepository;

@Service
public class UserInfoService {

	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private EmailSendService emailSendService;
	@Autowired
	private UserRegionService userRegionService;
	@Autowired
	private UserNicknameService userNicknameService;
	
	public UserInfo kakaoIntegration(String email) {
		List<UserInfo> userList = userInfoRepository.getUserInfoByEmail(email);
		if(userList.size()==0) {
			return null;
		} else {
			UserInfo userInfo = userList.get(0);
			userInfo.setIfKakao(true);
			userInfoRepository.updateKakaoInfo(userInfo);
			return userInfo;
		}
	}
	

	public String withdrawal(int UID) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUID(UID);
		userInfo.setEmail(UID+"@not.user");
		int userInfoResult = userInfoRepository.withdrawalUpdate(userInfo);
		Random random = new Random();
		String randomPW = Integer.toString(random.nextInt());
		int pwResult = changePW(UID,"asdfasdf");
		
		int nicknameResult = userNicknameService.withdrawalNickname(UID);
		
		if(userInfoResult == 1
				&& pwResult == 1
				&&nicknameResult ==1			
				) {
			return "bye";
		} else {
			return "fail";
		}
		
	}
	
	public int updateRGID(int UID, int RGID) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUID(UID);
		userInfo.setRGID(RGID);
		return userInfoRepository.updateRGID(userInfo);
	}
	
	public int updateNickID(int UID, int NickID) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUID(UID);
		userInfo.setNickID(NickID);
		return userInfoRepository.updateNickID(userInfo);
	}
	
	public int optionalTermsSubmit(int UID, boolean agreed) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUID(UID);
		if(agreed) {
			return userInfoRepository.updateOptionalTermsAgreed(userInfo);
		} else {
			return userInfoRepository.updateOptionalTermsDisagreed(userInfo);
		}
	};
	
	public UserInfoOverral getUserInfo(int UID) {
		UserInfo userInfo = userInfoRepository.getOneUserInfoByUID(UID);
		UserInfoOverral overral = new UserInfoOverral();
		if(userInfo == null) {
			return null;
		} else {
			overral.setUID(userInfo.getUID());
			overral.setEmail(userInfo.getEmail());
			overral.setPassword(userInfo.getPassword());
			overral.setSalt(userInfo.getSalt());
			overral.setNickID(userInfo.getNickID());
			overral.setNickname(userNicknameService.getByNickID(userInfo.getNickID()).getNickname());
			overral.setRGID(userInfo.getRGID());
			overral.setRegionName(userRegionService.getRegionByRGID(userInfo.getRGID()).getRegionName());
			overral.setCreatedAt(userInfo.getCreatedAt());
			overral.setUpdatedAt(userInfo.getUpdatedAt());
			overral.setOptionalTerms(userInfo.getOptionalTerms());
		}
		return overral;
	}
	
	public boolean ifRegisteredEmail(String email) {
		List<UserInfo> userList = userInfoRepository.getUserInfoByEmail(email);
		if(userList.size()==0) {
			return false;
		} else return true;
	}
	
	public UserInfo registration(String email, String password, int NickID, int RGID, boolean ifOptionalTermsAgreed, boolean ifKakao) {
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail(email);
		userInfo.setIfKakao(false);
		userInfo.setNickID(NickID);
		userInfo.setRGID(RGID);
		userInfo.setIfOptionalTermsAgreed(ifOptionalTermsAgreed);
		userInfo.setIfKakao(ifKakao);
		
		String salt = Encrypt.getSalt();
		String encPassword =Encrypt.getEncrypt(password, salt);
		userInfo.setSalt(salt);
		userInfo.setPassword(encPassword);

		int result = userInfoRepository.insertUserInfo(userInfo);
		if(result == 1) {
			return userInfo;
		} else
		return null;
	}
	
	public UserInfo login(String email, String password) {
		
		List<UserInfo> userList = userInfoRepository.getUserInfoByEmail(email);
		if(userList.size()==0) {
			return null;
		} 
		int UID = userList.get(0).getUID();
		int NickID = userList.get(0).getNickID();
		int RGID = userList.get(0).getRGID();
				
		String salt = userList.get(0).getSalt();
		String encPassword = userList.get(0).getPassword();
		String pw = Encrypt.getEncrypt(password, salt);

		if(encPassword.equals(pw)) {
			UserInfo userInfo = new UserInfo();
			userInfo.setUID(UID);
			userInfo.setEmail(email);
			userInfo.setNickID(NickID);
			userInfo.setRGID(RGID);
			return userInfo;
		} else {
			return null;
		}
	}
	
	public Integer sendTempPW(String email) {
		List<UserInfo> userList = userInfoRepository.getUserInfoByEmail(email);
		if(userList.size()==0) {
			return null;
		}
		int UID = userList.get(0).getUID();
		
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
		
		String type = "tempPassword";
    	//String path = "/static/thymeleaf-templates/"+type+".html";
    	EmailSendDto emailSendDto = new EmailSendDto();
    	emailSendDto.setType(type);
    	emailSendDto.setSubject("[나도캐스터] 임시 비밀번호가 발급됬어요.");
    	emailSendDto.setContext(emailSendService.setContext(type,newPassword));
    	emailSendDto.setCode(newPassword);
    	emailSendDto.setAddressTo(email);
    	emailSendDto.setAddressFrom("joeldev@naver.com");
    	emailSendService.sendEmail(emailSendDto,type);
		
		int passwordChangeResult = changePW(UID,newPassword);
		return passwordChangeResult;
		
	}
	
	public Integer changePW(int UID, String newPassword) {
		List<UserInfo> userList = userInfoRepository.getUserInfoByUID(UID);
		if(userList.size()==0) {
			return null;
		}
		String newSalt = Encrypt.getSalt();
		String newEncPassword = Encrypt.getEncrypt(newPassword, newSalt);
		UserInfo targetUser = new UserInfo();
		targetUser.setUID(UID);
		targetUser.setSalt(newSalt);
		targetUser.setPassword(newEncPassword);
		
		return userInfoRepository.updatePW(targetUser);
	}
	
}
