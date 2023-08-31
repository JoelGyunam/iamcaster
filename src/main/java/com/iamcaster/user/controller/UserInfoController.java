package com.iamcaster.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class UserInfoController {

	@GetMapping("/greeting")
	public String greetingView() {
		return "joiner/greeting/greeting";
	}
	
	@GetMapping("/main/login")
		public String loginView() {
			return "joiner/login/login";
	}
	
	
	@GetMapping("/main/login/forgotPassword")
	public String forgotPWView() {
		return "joiner/login/forgotpw";
	}
	
	@GetMapping("/registration")
		public String registrationView() {
			return "joiner/reg/reg";
	}
}
