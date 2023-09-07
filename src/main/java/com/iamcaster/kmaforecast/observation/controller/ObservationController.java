package com.iamcaster.kmaforecast.observation.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iamcaster.kmaforecast.observation.domain.Observation;
import com.iamcaster.kmaforecast.observation.service.ObservationService;

@RestController
@RequestMapping("/rest/observation")
public class ObservationController {

	@Autowired
	private ObservationService observationService;
	
	@GetMapping("/test")
	public List<Observation> getList() {
		List<Integer> STN_IDList = new ArrayList<>();
		STN_IDList.add(119);
		STN_IDList.add(202);
		
		return observationService.getDataList(20230808, STN_IDList);
	}
	
	@GetMapping("/newList/refresh")
	public Map<String,Integer> insertList(){
		int result = observationService.refreshObservation();
		Map<String,Integer> resultMap = new HashMap<>();
		resultMap.put("resultNumbers", result);
		return resultMap;
	}
}