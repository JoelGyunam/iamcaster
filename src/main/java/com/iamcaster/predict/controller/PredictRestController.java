package com.iamcaster.predict.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iamcaster.predict.service.PredictService;

@RestController
@RequestMapping("/rest/predict")
public class PredictRestController {
	
	@Autowired
	private PredictService predictService;
	
	@GetMapping("/submit")
	public Map<String,String> addNewPredict(
			@RequestParam("predictOrder") int predictOrder
			,@RequestParam("predictRGID") int predictRGID
			,@RequestParam("weatherType") String weatherType
			,@RequestParam("predictedNum1") double predictedNum1
			,@RequestParam(required=false, value="predictedNum2") Double predictedNum2
			,HttpSession session){
		int UID = (int) session.getAttribute("UID");
		if(predictedNum2==null) {
			predictedNum2 = 0.0;
		}
		
		int result = predictService.insertPredict(UID, predictOrder, predictRGID, weatherType, predictedNum1, predictedNum2);
		Map<String,String> resultMap = new HashMap<>();
		if(result == 1) {
			resultMap.put("result", "success");
		}else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	@GetMapping("/edit")
	public Map<String,String> editPredict(
			@RequestParam("predictUPID") int UPID
			,@RequestParam("predictedNum1") double predictedNum1
			,@RequestParam(required=false, value="predictedNum2") Double predictedNum2
			,HttpSession session){
		int UID = (int) session.getAttribute("UID");
		if(predictedNum2==null) {
			predictedNum2 = 0.0;
		}
		int result = predictService.editPredict(UID, UPID, predictedNum1, predictedNum2);
		Map<String,String> resultMap = new HashMap<>();
		if(result == 1) {
			resultMap.put("result", "success");
		}else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
}
