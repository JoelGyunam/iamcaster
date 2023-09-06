package com.iamcaster.regional.userregion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iamcaster.regional.userregion.domain.UserRegion;
import com.iamcaster.regional.userregion.repository.UserRegionRepository;

@Service
public class UserRegionService {

	@Autowired
	private UserRegionRepository userRegionRepository;
	
	public List<UserRegion> getMergedRegionalData(){
		return userRegionRepository.innerJoinSFCRegionAndOBVRegion();
	}
	
	public int refreshUserRegionList() {
		List<UserRegion> userRegionList = getMergedRegionalData();
		int result = 0;
		if(userRegionList.size()==0) {
			return result;
		}
		for(UserRegion line : userRegionList) {
			int count = userRegionRepository.countByRegSourceID(line.getRegSourceID());
			if(count == 0) {
				result += userRegionRepository.insertUserRegion(line);
			}
		}
		return result;
	}
	
	public List<UserRegion> getAllRegionList(){
		return userRegionRepository.selectAllRegion();
	}

	public UserRegion getRegionByRGID(int RGID) {
		return userRegionRepository.selectRegionByRGID(RGID);
	}
}
