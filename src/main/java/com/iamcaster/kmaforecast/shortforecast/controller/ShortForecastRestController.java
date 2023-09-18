package com.iamcaster.kmaforecast.shortforecast.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iamcaster.kmaforecast.shortforecast.domain.ShortForecast;
import com.iamcaster.kmaforecast.shortforecast.service.ShortForecastService;

@RestController
@RequestMapping("/rest/shortforecast")
public class ShortForecastRestController {

	@Autowired
	private ShortForecastService shortForecastService;
	
	@GetMapping("/getData")
	public List<ShortForecast> getShortForecast(@RequestParam("RGID") int RGID) {
		return shortForecastService.pullShortForecastFromKMA(RGID);
	}
	
	@GetMapping("/upsert")
	public Map<String,Integer> upsertShortForecast(@RequestParam("RGID") int RGID){
		int result = shortForecastService.upsertTodayShortForecast(RGID);
		Map<String,Integer> resultMap = new HashMap<>();
		resultMap.put("inserted", result);
		return resultMap;
	}
	
	@GetMapping("/forTip")
	public Map<String,String> tomorrowShortForecast(@RequestParam("RGID") int RGID){
		Map<String,String> resultMap = new HashMap<>();
		String tomorrowTempAM = shortForecastService.getShortForecastByRGID(RGID).get(2).getTA() + " ℃";
		String tomorrowTempPM = shortForecastService.getShortForecastByRGID(RGID).get(3).getTA() + " ℃";
		String tomorrowRainAM = shortForecastService.getShortForecastByRGID(RGID).get(2).getST() + " %";
		String tomorrowRainPM = shortForecastService.getShortForecastByRGID(RGID).get(3).getST() + " %";
		
		resultMap.put("tomorrowTempAM", tomorrowTempAM);
		resultMap.put("tomorrowTempPM", tomorrowTempPM);
		resultMap.put("tomorrowRainAM", tomorrowRainAM);
		resultMap.put("tomorrowRainPM", tomorrowRainPM);
		
		return resultMap;

	}
}
