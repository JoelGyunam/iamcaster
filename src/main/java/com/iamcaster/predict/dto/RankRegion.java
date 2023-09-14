package com.iamcaster.predict.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankRegion {

	private int predictRGID;
	private int tempCorrect;
	private int tempWrong;
	private int rainCorrect;
	private int rainWrong;
	
	private int regionRank;
	private String regionName;
	private double lat;
	private double lng;
	private int regionTotal;
	
	private double regionTempChance;
	private double regionRainChance;
	private double regionTotalChance;
	private String decimalFormatTempChance;
	private String decimalFormatRainChance;
	private String decimalFormatTotalChance;
	
	private String markerSVG;
	
}
