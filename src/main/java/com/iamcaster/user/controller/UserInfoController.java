package com.iamcaster.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iamcaster.regional.userregion.service.UserRegionService;

@Controller
@RequestMapping
public class UserInfoController {

	@Autowired
	private UserRegionService userRegionService;
	
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
		public String registrationView(Model model) {
			model.addAttribute("regionList",userRegionService.getAllRegionList());
		
			return "joiner/reg/reg";
	}
}
