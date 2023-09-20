package com.iamcaster.menu.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iamcaster.regional.userregion.service.UserRegionService;
import com.iamcaster.user.service.GetUserInfoService;

@Controller
@RequestMapping("/main/menu")
public class MenuController {

	@Autowired
	private GetUserInfoService getUserInfoService;
	@Autowired
	private UserRegionService userRegionService;
	
	@GetMapping()
	public String menuMainView(HttpSession session,Model model) {
		int UID = (int)session.getAttribute("UID");
		
		model.addAttribute("userinfo",getUserInfoService.getUserInfo(UID));
		model.addAttribute("regionList",userRegionService.getAllRegionList());
		
		return "menu/main";
	}
	
}
