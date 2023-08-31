package com.iamcaster.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.iamcaster.user.domain.UserInfo;

@Repository
public interface UserInfoRepository {

	public List<UserInfo> getUserInfoByEmail(@Param("email") String email);
	
	public int insertUserInfo(UserInfo userInfo);
}
