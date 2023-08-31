package com.iamcaster.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iamcaster.user.domain.UserNickname;

public interface UserNicknameRepository {

	public int insertNickname(UserNickname userNickname);
	public List<UserNickname> selectByNickname(@Param("nickname") String nickname);
	public int deleteNicknameByNickID(int NickID);
	
}
