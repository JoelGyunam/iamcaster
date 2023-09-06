package com.iamcaster.regional.kma.sfcregion.repository;

import org.springframework.stereotype.Repository;

import com.iamcaster.regional.kma.sfcregion.domain.SFCRegion;

@Repository
public interface SFCRegionRepository {

	public int countByReg_ID(String REG_ID);
	
	public int insertSFCRegion(SFCRegion sFCRegion);
	
}
