package com.iamcaster.kmaforecast.observation.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.iamcaster.kmaforecast.observation.domain.Observation;

@Repository
public interface ObservationRepository {

	public int insertObservationList(List<Observation> observation);
	
	public int insertObservation(Observation observation);
	public int countObservationByDateAndSTNID(Observation observation);
	public int updateObservationByOBVID(Observation observation);
	public List<Observation> selectListByYYMMDDandSTNID(List<Observation> observation);
}
