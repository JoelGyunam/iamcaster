package com.iamcaster.emailSender.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.iamcaster.emailSender.domain.EmailVerification;

@Repository
public interface EmailVerificationRepository {

	public int insertEmailCode(EmailVerification emailVerification);
	
	public EmailVerification selectByEmail(@Param("email")String email);
	
	public int updateCodeByEmail(EmailVerification emailVerification);
}
