package com.iamcaster.user.dto;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoOverral {

	private int UID;
	private String email;
	private String password;
	private String salt;
	private int NickID;
	private int RGID;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;
	
	private String Nickname;
	private String regionName;
}
