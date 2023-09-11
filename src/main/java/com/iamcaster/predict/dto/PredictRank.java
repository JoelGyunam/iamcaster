package com.iamcaster.predict.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PredictRank {

	private int UID;
	private int predictRGID;
	private int correct;
	private int wrong;
	
	private int rank;
	private String nickname;
	private String regionName;
	private double accuracy;
	private String accuracyString;
	private int sumCount;
}
