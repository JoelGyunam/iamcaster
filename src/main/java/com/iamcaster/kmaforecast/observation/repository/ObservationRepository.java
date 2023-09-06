package com.iamcaster.kmaforecast.observation.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.iamcaster.kmaforecast.observation.domain.Observation;

@Repository
public interface ObservationRepository {

	public int insertObservationList(List<Observation> observation);
	
}
