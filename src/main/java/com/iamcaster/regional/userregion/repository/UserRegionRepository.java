package com.iamcaster.regional.userregion.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.iamcaster.regional.userregion.domain.UserRegion;

@Repository
public interface UserRegionRepository {

	public List<UserRegion> innerJoinSFCRegionAndOBVRegion();
	
	public int countByRegSourceID(String regSourceID);
	
	public int insertUserRegion(UserRegion userRegion);
	
	public List<UserRegion> selectAllRegion();
	
	public UserRegion selectRegionByRGID(int RGID);
	public UserRegion selectRegionBySTNID(int STNID);
}
