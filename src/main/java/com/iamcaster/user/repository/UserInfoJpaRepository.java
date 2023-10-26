package com.iamcaster.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iamcaster.user.domain.UserInfo;

@Repository
public interface UserInfoJpaRepository extends JpaRepository<UserInfo, Integer>{
	
}
