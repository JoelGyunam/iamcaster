package com.iamcaster.user.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iamcaster.predict.controller.PredictController;
import com.iamcaster.regional.userregion.service.UserRegionService;

@Controller
@RequestMapping
public class UserInfoController {

	@Autowired
	private UserRegionService userRegionService;
	@Autowired
	private PredictController predictController;

	@GetMapping()
	public String intro(HttpSession session, Model model) {
		Integer UID = (Integer)session.getAttribute("UID");
		if(UID == null) {
			return greetingView();
		} else
			return predictController.predictView(session, model);
	}
	
	@GetMapping("/greeting")
	public String greetingView() {
		return "joiner/greeting/greeting";
	}

	@GetMapping("/main/login")
	public String loginView(Model model, @Nullable HttpSession session) {
		model.addAttribute("regionList", userRegionService.getAllRegionList());

		return "joiner/login/login";
	}

	@GetMapping("/main/login/forgotPassword")
	public String forgotPWView() {
		return "joiner/login/forgotpw";
	}

	@GetMapping("/registration")
	public String registrationView(Model model) {
		model.addAttribute("regionList", userRegionService.getAllRegionList());

		return "joiner/reg/reg";
	}
}
