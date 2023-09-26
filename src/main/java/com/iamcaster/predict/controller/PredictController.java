package com.iamcaster.predict.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iamcaster.predict.dto.UserPredictDelivery;
import com.iamcaster.predict.service.PredictService;
import com.iamcaster.regional.userregion.service.UserRegionService;

@RequestMapping("/main/predict")
@Controller
public class PredictController {

	@Autowired
	private PredictService predictService;
	@Autowired
	private UserRegionService userRegionService;
	
	@GetMapping()
	public String predictView(HttpSession session, Model model) {
		
		int UID = (int) session.getAttribute("UID");
		model.addAttribute("regionList",userRegionService.getAllRegionList());
		
		Map<Integer, UserPredictDelivery> predictViewList = predictService.getTodayPredictByUID(UID);
		
		if(predictViewList!=null) {
			model.addAttribute("predictedMap",predictViewList);
		};
		
		return "service/predict/predict";
	}
	
	
	@GetMapping("/1")
	@ResponseBody
	public Map<Integer, UserPredictDelivery> predictViewTest(HttpSession session, Model model) {
		
		int UID = (int) session.getAttribute("UID");
		
		Map<Integer, UserPredictDelivery> predictViewMap = predictService.getTodayPredictByUID(UID);
		
		if(predictViewMap!=null) {
			model.addAttribute("predictedMap",predictViewMap);
		};
		
		return predictViewMap;
	}
	
}
