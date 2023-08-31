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
	
	public int registration(String email, String password, int NickID, int RGID) {
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail(email);
		userInfo.setNickID(NickID);
		userInfo.setRGID(RGID);
		
		String salt = Encrypt.getSalt();
		String encPassword =Encrypt.getEncrypt(password, salt);
		userInfo.setSalt(salt);
		userInfo.setPassword(encPassword);

		return userInfoRepository.insertUserInfo(userInfo);
	}
}
