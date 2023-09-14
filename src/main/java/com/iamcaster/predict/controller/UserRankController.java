package com.iamcaster.predict.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.iamcaster.predict.service.PredictService;
import com.iamcaster.user.service.UserInfoService;

@Controller
@RequestMapping("/main/userRank")
public class UserRankController {
	
	@Autowired
	private PredictService predictService;
	@Autowired
	private UserInfoService userInfoService;

	@GetMapping()
	public String userRankView(HttpSession session,@RequestParam(value="RGID", required=false)Integer RGID,Model model) {
		
		int UID = (int) session.getAttribute("UID");
		model.addAttribute("UID", UID);
		
		model.addAttribute("rankList",predictService.getPredictRank(UID, RGID));
		
		return "service/predict/userrank";
	}
	
	@GetMapping("/toggleCame")
	public String getChangableArea(
			@RequestParam(value="RGID", required=false)Integer RGID
			,@RequestParam(value="UID", required=false)Integer UID
			,HttpSession session
			,Model model) {
		
		int myUID = (int) session.getAttribute("UID");
		
		if(UID != null) {
			int myRGID = userInfoService.getUserInfo(UID).getRGID();
			model.addAttribute("rankList",predictService.getPredictRank(myUID, myRGID));
		} else if(RGID != null) {
			model.addAttribute("rankList",predictService.getPredictRank(myUID, RGID));
		} else {
			model.addAttribute("rankList",predictService.getPredictRank(myUID, null));
		}
		
		return "service/predict/userrank-changearea";
	}
	
}
