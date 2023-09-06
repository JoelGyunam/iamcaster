package com.iamcaster.kmaforecast.observation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iamcaster.common.WebClientForKMA;
import com.iamcaster.kmaforecast.observation.domain.Observation;
import com.iamcaster.kmaforecast.observation.repository.ObservationRepository;

@Service
public class ObservationService {

	@Autowired
	private WebClientForKMA webClientForKMA;
	@Autowired
	private ObservationRepository observationRepository;
	
	public List<Observation> getDataList(int daysAgo, List<Integer> STN_ID) {
		String stringData = webClientForKMA.fetchAndToStringForObservation(-daysAgo, STN_ID);
		List<Observation> parsedDataList = webClientForKMA.parseDataForObservation(stringData);
		return parsedDataList;
	}
	
	public int insertDataList(int daysAgo, List<Integer> STN_ID) {
		List<Observation> targetList = getDataList(daysAgo,STN_ID);
		return observationRepository.insertObservationList(targetList);
	}
}
