package com.iamcaster.regional.kma.sfcregion.domain;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SFCRegion {
	private int SFCRegionID;
	private String REG_ID;
	private String TM_ST;
	private String TM_ED;
	private String REG_SP;
	private String REG_NAME;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;
}
