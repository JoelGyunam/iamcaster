package com.iamcaster.user.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iamcaster.user.domain.UserNickname;
import com.iamcaster.user.repository.UserNicknameJpaRepository;
import com.iamcaster.user.repository.UserNicknameRepository;

@Service
public class UserNicknameService {

	@Autowired
	private UserNicknameRepository userNicknameRepository;
	@Autowired
	private UserNicknameJpaRepository userNicknameJpaRepository;
	
	public int withdrawalNickname(int UID) {
		UserNickname userNickname = new UserNickname();
		userNickname.setUID(UID);
		return userNicknameRepository.withdrawalNickname(userNickname);
	}
	
	public boolean ifDuplicatedNickname(String nickname) {
		int nicknameCount = userNicknameJpaRepository.countByNickname(nickname);
		if(nicknameCount==0) {
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
		
		UserNickname nickname = userNicknameJpaRepository.findById(NickID)
				.orElseThrow(() -> new EntityNotFoundException());
		
		UserNickname updateNickname = nickname
				.toBuilder()
				.UID(UID)
				.build();
		userNicknameJpaRepository.save(updateNickname);
		
		return updateNickname.getUID() == UID ? 1 : 0;
	}
	
	public UserNickname getByNickID(int NickID) {
		return userNicknameRepository.selectOneByNickID(NickID);
	}
}