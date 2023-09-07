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
	
	public static int parseDateForObservation(ZonedDateTime date) {
		String dayString = date.toString().substring(0,10).replace("-", "");
		return Integer.parseInt(dayString);
	}
	
	public static String reverseParser(int date) {
		String dayString = Integer.toString(date);
        String year = dayString.substring(0, 4);
        String month = dayString.substring(4, 6);
        String day = dayString.substring(6, 8);
        String concated = year + "-" + month + "-" + day;

        return concated;

	}
}
