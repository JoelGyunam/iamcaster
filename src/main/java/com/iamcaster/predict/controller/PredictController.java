package com.iamcaster.predict.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/main/predict")
@Controller
public class PredictController {

	@GetMapping()
	public String predictView() {
		return "service/predict/predict";
	}
	
}
