package com.iamcaster.predict.dto;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPredictDelivery {
	private int UPID;
	private int predictOrder;
	private int UID;
	private int predictRGID;
	private String weatherType;
	private double predictedNum1;
	private double predictedNum2;
	private double realNum1;
	private double realNum2;
	private String result;
	private int scored;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;
	
	private String regionName;
	private String parsedCreatedDate;
	private String parsedTargetDate;
	private String myPredict;
	private String realNumber;
	private int daysLeftToBeScored;
	
	private String shortForecastAMString;
	private String shortForecastPMString;
	
}
