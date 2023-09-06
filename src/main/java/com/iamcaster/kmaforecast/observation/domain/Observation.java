package com.iamcaster.kmaforecast.observation.domain;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Observation {

	private int OBVID;
	private int YYMMDD_KST;
	private int STN_ID;
	private double TA_AVG;
	private double TA_MAX;
	private double TA_MIN;
	private double RN_DAY;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;
}
