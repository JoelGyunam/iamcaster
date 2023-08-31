package com.iamcaster.emailSender.domain;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailVerification {

	private int EVID;
	private String email;
	private int emailCode;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;
	
}
