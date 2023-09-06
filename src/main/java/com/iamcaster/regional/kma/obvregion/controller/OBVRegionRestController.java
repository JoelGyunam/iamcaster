package com.iamcaster.regional.kma.obvregion.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iamcaster.regional.kma.obvregion.domain.OBVRegion;
import com.iamcaster.regional.kma.obvregion.service.OBVRegionService;

@RequestMapping("/rest/obvregion")
@RestController
public class OBVRegionRestController {

	@Autowired
	private OBVRegionService obvRegionService;
	
	@GetMapping("/getList")
	public List<OBVRegion> getOBVRegionList(){
		return obvRegionService.getOBVRegionList();
	}
	
	@GetMapping("/getList/save")
	public Map<String,Integer> refreshOBVRegionList() {
		int result =  obvRegionService.refreshOBVRegionList();
		Map<String,Integer> resultMap = new HashMap<>();
		resultMap.put("newLines", result);
		return resultMap;
	}	
}
