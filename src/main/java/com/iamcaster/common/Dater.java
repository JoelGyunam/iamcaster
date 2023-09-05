package com.iamcaster.common;

import java.time.ZonedDateTime;

public class Dater {

	public static String getToday() {
		ZonedDateTime now = ZonedDateTime.now();
		
		String today = now.toString();
		return today.substring(0, 10);
	}
	
}
