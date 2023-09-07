package com.iamcaster.predict.dto;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AnswerDeliverDto {
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
	
	private int intCreatedDate;
	private double minTemp;
	private double maxTemp;
	private double rainmm;
}
