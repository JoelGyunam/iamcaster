package com.iamcaster.menu.domain;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notice {

	private int noticeID;
	private String subject;
	private String content;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;
	
}
