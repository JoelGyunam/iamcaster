package com.iamcaster.regional.kma.obvregion.repository;

import org.springframework.stereotype.Repository;

import com.iamcaster.regional.kma.obvregion.domain.OBVRegion;

@Repository
public interface OBVRegionRepository {
	
	public int countByFCT_ID(String FCT_ID);
	public int insertOBVRegion(OBVRegion obvRegion);


}
