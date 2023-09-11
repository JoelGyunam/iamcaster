package com.iamcaster.predict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iamcaster.predict.service.PredictService;

@Controller
@RequestMapping("/main/userRank")
public class UserRankController {
	
	@Autowired
	private PredictService predictService;

	@GetMapping()
	public String userRankView(Model model) {
		
		model.addAttribute("rankList",predictService.getPredictRank());
		
		return "service/predict/userrank";
	}
	
}
