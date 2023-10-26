package com.iamcaster.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iamcaster.user.domain.UserNickname;

public interface UserNicknameJpaRepository extends JpaRepository<UserNickname, Integer>{
	public int countByNickname(String nickname);
}
