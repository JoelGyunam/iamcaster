package com.iamcaster.regional.userregion.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iamcaster.regional.userregion.domain.UserRegion;
import com.iamcaster.regional.userregion.service.UserRegionService;

@RestController
@RequestMapping("/rest/region/user")
public class UserRegionRestController {

	@Autowired
	private UserRegionService userRegionService;
	
	@GetMapping("/mergedList")
	public List<UserRegion> getMergedList(){
		return userRegionService.getMergedRegionalData();
	}
	
	@GetMapping("/mergedList/refresh")
	public Map<String,Integer> refreshUserRegionList(){
		int result = userRegionService.refreshUserRegionList();
		Map<String,Integer> resultMap = new HashMap<>();
		resultMap.put("newLines", result);
		return resultMap;
	}

}
