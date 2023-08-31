package com.iamcaster.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iamcaster.common.Encrypt;
import com.iamcaster.user.domain.UserInfo;
import com.iamcaster.user.repository.UserInfoRepository;

@Service
public class UserInfoService {

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	public boolean ifRegisteredEmail(String email) {
		List<UserInfo> userList = userInfoRepository.getUserInfoByEmail(email);
		if(userList.size()==0) {
			return false;
		} else return true;
	}
	
	public UserInfo registration(String email, String password, int NickID, int RGID) {
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail(email);
		userInfo.setNickID(NickID);
		userInfo.setRGID(RGID);
		
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
}
