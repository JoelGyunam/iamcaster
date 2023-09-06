package com.iamcaster.regional.userregion.domain;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegion {
	private int RGID;
	private String regSourceID;
	private int STN_ID;
	private String regionName;
	private double lat;
	private double lon;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;
}
