package com.iamcaster.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iamcaster.user.domain.UserNickname;
import com.iamcaster.user.repository.UserNicknameRepository;

@Service
public class UserNicknameService {

	@Autowired
	private UserNicknameRepository userNicknameRepository;
	
	public boolean ifDuplicatedNickname(String nickname) {
		List<UserNickname> nicknameList = new ArrayList<>();
		nicknameList = userNicknameRepository.selectByNickname(nickname);
		if(nicknameList.size()==0) {
			return false;
		} else return true;
	}
	
	public int insertNickname(String nickname) {
		UserNickname userNickname = new UserNickname();
		userNickname.setNickname(nickname);
		userNicknameRepository.insertNickname(userNickname);
		return userNickname.getNickID();
	}
	
	public int deleteNicknameByNickID(int NickID) {
		return userNicknameRepository.deleteNicknameByNickID(NickID);
	}
	
	public int setUIDforNickname(int UID, int NickID) {
		
		UserNickname userNickname = new UserNickname();
		userNickname.setUID(UID);
		userNickname.setNickID(NickID);
		
		return userNicknameRepository.updateNickname(userNickname);
	}
}