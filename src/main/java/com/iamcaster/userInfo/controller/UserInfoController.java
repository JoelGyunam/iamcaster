package com.iamcaster.userInfo.controller;

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
}
