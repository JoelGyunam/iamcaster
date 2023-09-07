package com.iamcaster.kmaforecast.observation.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iamcaster.common.WebClientForKMA;
import com.iamcaster.kmaforecast.observation.domain.Observation;
import com.iamcaster.kmaforecast.observation.repository.ObservationRepository;
import com.iamcaster.predict.service.PredictService;
import com.iamcaster.regional.userregion.service.UserRegionService;

@Service
public class ObservationService {

	@Autowired
	private WebClientForKMA webClientForKMA;
	@Autowired
	private ObservationRepository observationRepository;
	@Autowired
	private PredictService predictService;
	@Autowired
	private UserRegionService userRegionService;
	
	//파싱된 일자와 지역리스트를 파라미터로 전달받아, Observation 객체에 정보 담음.
	public List<Observation> getDataList(int parsedDate, List<Integer> STN_ID) {
		String stringData = webClientForKMA.fetchAndToStringForObservation(parsedDate, STN_ID);
		List<Observation> parsedDataList = webClientForKMA.parseDataForObservation(stringData);
		return parsedDataList;
	}
	
	//객체 상태의 Observation을 db에 insert.
	public int insertDataList(int parsedDate, List<Integer> STN_ID) {
		List<Observation> targetList = getDataList(parsedDate,STN_ID);
		return observationRepository.insertObservationList(targetList);
	}
	
	//키:날짜, 데이터:지역 세트 로 구성되어 있는 predictService의 tobescoredList를 호출해서, insertDataList 메소드를 통해 db에 인서트 후, 
	//predict테이블에 인서트 위한 addForUserPredict 메소드 호출.
	public int refreshObservation() {
		Map<Integer,Set<Integer>> targetMap = predictService.toBeScoredList();
		int sum = 0;
		if(targetMap==null) {
			return sum;
		} else {
			for(Integer yyddmm : targetMap.keySet()) {
				Set<Integer> regionValueSet = targetMap.get(yyddmm);
				List<Integer> regionValueList = new ArrayList<>();
				Iterator<Integer> iter = regionValueSet.iterator();
				while(iter.hasNext()) {
					int RGID = iter.next();
					int STN_ID = userRegionService.getRegionByRGID(RGID).getSTN_ID();
					regionValueList.add(STN_ID);
				}
				sum += insertDataList(yyddmm,regionValueList);
				addForUserPredict(targetMap);
			}
		}
		return sum;
	}
	
	//predictService에서 predict테이블에 정답정보를 인서트 할 수 있도록 데이터를 구성해서 predictService의 insertNewPredictAnswer 메소드 호출.
	public int addForUserPredict(Map<Integer,Set<Integer>> targetMap) { 
		int sum = 0;
		List<Observation> observationList = new ArrayList<>();
		if(targetMap==null) {
			return sum;
		}else {
			for(Integer key : targetMap.keySet()) {
				Set<Integer> STNIDSet = targetMap.get(key);
				Iterator<Integer> iter = STNIDSet.iterator();
				while(iter.hasNext()) {
					int RGID = iter.next();
					int STN_ID = userRegionService.getRegionByRGID(RGID).getSTN_ID();
					Observation observation = new Observation();
					observation.setYYMMDD_KST(key);
					observation.setSTN_ID(STN_ID);
					observationList.add(observation);
				}
			}
		}
		List<Observation> resultList = getListByYYMMDDandSTNID(observationList);
		predictService.insertNewPredictAnswer(resultList);
		return sum;
	}
	
	public List<Observation> getListByYYMMDDandSTNID(List<Observation> observation) {
		return observationRepository.selectListByYYMMDDandSTNID(observation);
	}
}
