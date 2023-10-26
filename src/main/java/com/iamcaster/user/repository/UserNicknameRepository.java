package com.iamcaster.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.iamcaster.user.domain.UserNickname;

@Repository
public interface UserNicknameRepository {

	public int insertNickname(UserNickname userNickname);
	public List<UserNickname> selectByNickname(@Param("nickname") String nickname);
	public UserNickname selectOneByNickID(int NickID);
	public int deleteNicknameByNickID(int NickID);
	public int updateNickname(UserNickname userNickname);
	public int withdrawalNickname(UserNickname userNickname);
	
}
