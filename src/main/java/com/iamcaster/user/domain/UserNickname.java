package com.iamcaster.user.domain;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNickname {

	private int NickID;
	private String nickname;
	private int UID;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;	
	
}
