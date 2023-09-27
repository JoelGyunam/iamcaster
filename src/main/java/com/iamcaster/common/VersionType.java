package com.iamcaster.common;

public class VersionType {

	public static String versionType() {
		String dev = "http://localhost:8080";
		String service = "http://iamcaster.com:8080";
		
		String version = "service";
		
		if(version.equals("dev")) {
			return dev;
		} else
			return service;
		
	}
	
}
