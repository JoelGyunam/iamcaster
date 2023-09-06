package com.iamcaster.regional.kma.sfcregion.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iamcaster.common.WebClientForKMA;
import com.iamcaster.regional.kma.sfcregion.domain.SFCRegion;
import com.iamcaster.regional.kma.sfcregion.service.SFCRegionService;

import reactor.core.publisher.Mono;

@RequestMapping("/rest/sfcregion")
@RestController
public class SFCRegionRestController {

	@Autowired
	private SFCRegionService sfcRegionService;
	@Autowired
	private WebClientForKMA webClientForKMA;
	
	//https://annajin.tistory.com/101
	
	@GetMapping("/getList")
	public List<SFCRegion> getSFCRegionList() {
		 
		return sfcRegionService.getList();
	}
	@GetMapping("/getList/1")
	public String getSFCRegionListTest() {
		
		return webClientForKMA.fetchAndToString("/api/typ01/url/fct_shrt_reg.php?tmfc=0&disp=1&help=0&authKey=8HXVgof0RqS11YKH9EakVA");
	}
	
	@GetMapping("/getList/save")
	public Map<String,Integer> refreshSFCRegionList() {
		int result = sfcRegionService.refreshSFCRegionList();
		Map<String,Integer> resultMap = new HashMap<>();
		resultMap.put("newLines", result);
		return resultMap;
	}
	
}