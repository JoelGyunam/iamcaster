package com.iamcaster.user.domain;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {

	private int UID;
	private String email;
	private String password;
	private String salt;
	private int NickID;
	private int RGID;
	private boolean ifOptionalTermsAgreed;
	private ZonedDateTime optionalTerms;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;
	private boolean ifKakao;
	
}
