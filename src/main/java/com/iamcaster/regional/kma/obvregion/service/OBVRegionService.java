package com.iamcaster.regional.kma.obvregion.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iamcaster.common.WebClientForKMA;
import com.iamcaster.regional.kma.obvregion.domain.OBVRegion;
import com.iamcaster.regional.kma.obvregion.repository.OBVRegionRepository;

@Service
public class OBVRegionService {

	@Autowired
	private WebClientForKMA webClientForKMA;
	@Autowired
	private OBVRegionRepository obvRegionRepository;
	
	public List<OBVRegion> selectAll(){
		return obvRegionRepository.selectAll();
	}
	
	public List<OBVRegion> getOBVRegionList(){
		String resultString = webClientForKMA.fetchAndToString("/api/typ01/url/stn_inf.php?inf=SFC&stn=&tm=202211300900&help=1&authKey=8HXVgof0RqS11YKH9EakVA");
		Object resultObject = webClientForKMA.parseData("obvRegion", resultString);
		List<OBVRegion> resultList = (List<OBVRegion>) resultObject;
		return resultList;
	}
	
	public int refreshOBVRegionList() {
	   List<OBVRegion> OBVRegionList = new ArrayList<>();
	   OBVRegionList = getOBVRegionList();
	   int result = 0;
	   for(OBVRegion line : OBVRegionList) {
		   int isExists = obvRegionRepository.countByFCT_ID(line.getFCT_ID());
		   if(isExists==0) {
			   result += obvRegionRepository.insertOBVRegion(line);
		   }
	   }
	   return result;
	}
	
}
