package com.iamcaster.predict.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iamcaster.predict.dto.PredictRank;
import com.iamcaster.predict.dto.RankRegion;
import com.iamcaster.predict.service.PredictService;
import com.iamcaster.user.service.UserInfoService;

@RestController
@RequestMapping("/rest/rank")
public class UserRankRestController {

	@Autowired
	private PredictService predictService;
	@Autowired
	private UserInfoService userInfoService;
	
		
	@GetMapping("/map/byRegion")
	public List<RankRegion> mapDataByRGID(){
		return predictService.getPredictRankByRGID();
	}
	
//	@GetMapping("/toggleCame")
//	public Map<String, List<PredictRank>> getChangableArea(
//			@RequestParam(value="RGID", required=false)Integer RGID
//			,@RequestParam(value="UID", required=false)Integer UID
//			,Model model) {
//		
//		if(UID != null) {
//			int myRGID = userInfoService.getUserInfo(UID).getRGID();
//			return predictService.getPredictRank(myRGID);
//		} else {
//			return predictService.getPredictRank(RGID);
//		}
//	}

}
