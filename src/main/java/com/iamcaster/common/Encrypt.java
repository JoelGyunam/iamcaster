package com.iamcaster.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Encrypt {

	public static String getSalt() {
		SecureRandom sr = new SecureRandom();
		byte[] salt = new byte[20];
		
		sr.nextBytes(salt);
		
		StringBuffer sb = new StringBuffer();
		for(byte b : salt) {
			sb.append(String.format("%02x", b));
		}
		
		return sb.toString();
	}
	
	public static String getEncrypt(String message, String salt) {
		String result = "";
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update((message + salt).getBytes());
			byte[]msgSalt = md.digest();
			
			StringBuffer sb = new StringBuffer();
			for(byte b : msgSalt) {
				sb.append(String.format("%02x", b));
			}
			
			result = sb.toString();
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
}