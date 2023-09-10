package com.iamcaster.predict.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iamcaster.predict.service.PredictService;

@Controller
@RequestMapping("/main/myscore")
public class MyScoreController {
	
	@Autowired
	private PredictService predictService;

	@GetMapping()
	public String myScoreView(HttpSession session, Model model) {
		
		int UID = (int) session.getAttribute("UID");
		model.addAttribute("scoreNumbers", predictService.getScoreNumbers(UID));
		model.addAttribute("predictList",predictService.getListByUID(UID));
		
		return "service/predict/myscore";
	}
}
