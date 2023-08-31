package com.iamcaster.emailSender.dto;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailSendDto {

	private int EVID;
	private String email;
	private int emailCode;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;
	
	private String addressTo;
	private String addressFrom;
	private String subject;
	private String code;
	private String context;
	private String type;
	
}
