package com.iamcaster.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/policies")
public class policiesController {

	@GetMapping("/terms")
	public String termsView() {
		return "policies/terms";
	}
}
