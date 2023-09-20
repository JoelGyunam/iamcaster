package com.iamcaster.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.iamcaster.user.domain.UserInfo;

@Repository
public interface UserInfoRepository {

	public List<UserInfo> getUserInfoByEmail(@Param("email") String email);
	public List<UserInfo> getUserInfoByUID (@Param("UID") int UID);
	public UserInfo getOneUserInfoByUID(@Param("UID") int UID);
	public int insertUserInfo(UserInfo userInfo);
	public int updatePW(UserInfo userInfo);
	public int updateOptionalTermsAgreed(UserInfo userInfo);
	public int updateOptionalTermsDisagreed(UserInfo userInfo);
	public int updateNickID(UserInfo userInfo);
	public int updateRGID(UserInfo userInfo);
	public int withdrawalUpdate(UserInfo userInfo);
}