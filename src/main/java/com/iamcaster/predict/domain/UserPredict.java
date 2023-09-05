package com.iamcaster.predict.domain;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPredict {
	int UPID;
	int predictOrder;
	int UID;
	int predictRGID;
	String weatherType;
	double predictedNum1;
	double predictedNum2;
	double realNum1;
	double realNum2;
	String result;
	int scored;
	ZonedDateTime createdAt;
	ZonedDateTime updatedAt;
}
