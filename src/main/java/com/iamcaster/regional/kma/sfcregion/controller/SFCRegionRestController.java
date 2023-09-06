package com.iamcaster.regional.kma.sfcregion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iamcaster.regional.kma.sfcregion.domain.SFCRegion;
import com.iamcaster.regional.kma.sfcregion.service.SFCRegionService;

import reactor.core.publisher.Mono;

@RequestMapping("/rest/sfcregion")
@RestController
public class SFCRegionRestController {

	@Autowired
	private SFCRegionService sfcRegionService;
	
	//https://annajin.tistory.com/101
	
	@GetMapping("/getList")
	public List<SFCRegion> getSFCRegionList() {
		 
		return sfcRegionService.getList();
	}
	
	@GetMapping("/getList/save")
	public int refreshSFCRegionList() {
		return sfcRegionService.refreshSFCRegionList();
	}
	
}