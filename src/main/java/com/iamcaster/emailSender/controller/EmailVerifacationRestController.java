package com.iamcaster.emailSender.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iamcaster.emailSender.service.EmailVerificationService;

@RestController
@RequestMapping("/rest/emailVerify")
public class EmailVerifacationRestController {

	@Autowired
	private EmailVerificationService emailVerificationService;
	
	@PostMapping("/send")
	public Map<String,String> emailCodeSender(@RequestParam("email") String email){
		Map<String,String> resultMap = new HashMap<>();
		
		int result = emailVerificationService.sendVerificationEmail(email);
		
		if(result == 1) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	@PostMapping("/verify")
	public Map<String,Boolean> emailCodeVerifier(@RequestParam("email") String email, @RequestParam("code") int code){
		Map<String,Boolean> resultMap = new HashMap<>();
		boolean result = emailVerificationService.emailCodeVerifier(email, code);
		resultMap.put("ifMatched", result);
		return resultMap;
	}
	
}
