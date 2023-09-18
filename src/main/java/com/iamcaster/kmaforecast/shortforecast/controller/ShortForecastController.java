package com.iamcaster.kmaforecast.shortforecast.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.iamcaster.kmaforecast.shortforecast.service.ShortForecastService;
import com.iamcaster.regional.userregion.service.UserRegionService;
import com.iamcaster.user.service.UserInfoService;

@RequestMapping("/main/shortforecast")
@Controller
public class ShortForecastController {
	
	@Autowired
	private ShortForecastService shortForecastService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserRegionService userRegionService;

	@GetMapping()
	public String shortForecastView(HttpSession session, Model model) {
		
		int UID = (int)session.getAttribute("UID");
		int RGID = userInfoService.getUserInfo(UID).getRGID();
		String regionName = userRegionService.getRegionByRGID(RGID).getRegionName();
		
		model.addAttribute("myRegionName",regionName);
		model.addAttribute("regionList",userRegionService.getAllRegionList());
		model.addAttribute("shortForecastList",shortForecastService.getShortForecastByRGID(RGID));
		
		return "service/kmaForecast/shortForecast";
	}
	
	@GetMapping("/byRegion")
	public String reloadForecastTable(@RequestParam("RGID") int RGID, Model model) {
		model.addAttribute("shortForecastList",shortForecastService.getShortForecastByRGID(RGID));
		return "service/kmaForecast/reload-table";
	}
	
	
}
