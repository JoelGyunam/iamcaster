package com.iamcaster.common;

import java.time.ZonedDateTime;

public class Dater {

	public static String getToday() {
		ZonedDateTime now = ZonedDateTime.now();
		
		String today = now.toString();
		return today.substring(0, 10);
	}
	
	public static String getPastDate(int days) {
		ZonedDateTime targetDay = ZonedDateTime.now().minusDays(-days);
		
		String dayString = targetDay.toString();
		return dayString.substring(0, 10).replace("-", "");
	}
	
}
