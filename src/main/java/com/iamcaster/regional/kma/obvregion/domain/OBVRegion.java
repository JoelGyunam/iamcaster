package com.iamcaster.regional.kma.obvregion.domain;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OBVRegion {

	
	private int OBVRegionID;
	private int STN_ID;
	private double LON;
	private double LAT;
	private String STN_KO;
	private String FCT_ID;
	private String LAW_ID;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;
	
}
