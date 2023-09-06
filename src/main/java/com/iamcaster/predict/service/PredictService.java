package com.iamcaster.predict.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iamcaster.predict.domain.UserPredict;
import com.iamcaster.predict.dto.UserPredictDelivery;
import com.iamcaster.predict.repository.PredictRepository;
import com.iamcaster.regional.userregion.service.UserRegionService;
import com.iamcaster.user.dto.UserInfoOverral;
import com.iamcaster.user.service.UserInfoService;

@Service
public class PredictService {

	@Autowired
	private PredictRepository predictRepository;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserRegionService userRegionService;
	
	public List<UserPredictDelivery> getTodayPredictByUID(int UID){

		UserInfoOverral userSummary = userInfoService.getUserInfo(UID);
		int RGID = userSummary.getRGID();
		String regionName = userSummary.getRegionName();
		
		List<UserPredictDelivery> predictMapList = new ArrayList<>(); 
		
		for(int i = 0; i < 6; i++) {
			UserPredictDelivery userPredictForNull = new UserPredictDelivery();
			userPredictForNull.setPredictOrder(i+1);
			if(i < 2) {
				userPredictForNull.setPredictRGID(RGID);
				userPredictForNull.setRegionName(regionName);
			}
			if((i+1)%2==0) {
				userPredictForNull.setWeatherType("rain");
			}else {
				userPredictForNull.setWeatherType("temp");
			}
			predictMapList.add(i,userPredictForNull);
		}
		
		List<UserPredict> predictList = predictRepository.selectPredictByUID(UID);
		
		if(predictList.size()==0) {
			return predictMapList;
		} else {
			for(int j = 0; j < predictList.size(); j++) {
				UserPredictDelivery userPredictDTO = new UserPredictDelivery();

				userPredictDTO.setUPID(predictList.get(j).getUPID());
				userPredictDTO.setPredictOrder(predictList.get(j).getPredictOrder());
				userPredictDTO.setUID(predictList.get(j).getUID());
				userPredictDTO.setPredictRGID(predictList.get(j).getPredictRGID());
				userPredictDTO.setWeatherType(predictList.get(j).getWeatherType());
				userPredictDTO.setPredictedNum1(predictList.get(j).getPredictedNum1());
				userPredictDTO.setPredictedNum2(predictList.get(j).getPredictedNum2());
				userPredictDTO.setRealNum1(predictList.get(j).getRealNum1());
				userPredictDTO.setRealNum2(predictList.get(j).getPredictedNum2());
				userPredictDTO.setResult(predictList.get(j).getResult());
				userPredictDTO.setScored(predictList.get(j).getScored());
				userPredictDTO.setCreatedAt(predictList.get(j).getCreatedAt());
				userPredictDTO.setUpdatedAt(predictList.get(j).getUpdatedAt());
				userPredictDTO.setRegionName(userRegionService.getRegionByRGID(predictList.get(j).getPredictRGID()).getRegionName());
				predictMapList.set(userPredictDTO.getPredictOrder()-1,userPredictDTO);
				}
			}
		return predictMapList;
	}
	
	public int insertPredict(
			int UID 
			,Integer predictOrder
			,int predictRGID
			,String weatherType
			,double predictedNum1
			,double predictedNum2
			) {
		UserPredict userPredict = new UserPredict();
		userPredict.setUID(UID);
		userPredict.setPredictOrder(predictOrder);
		userPredict.setPredictRGID(predictRGID);
		userPredict.setWeatherType(weatherType);
		userPredict.setPredictedNum1(predictedNum1);
		userPredict.setPredictedNum2(predictedNum2);
		
		return predictRepository.insertPredict(userPredict);
	}
	
	public int editPredict(int UID,int UPID,double predictedNum1,double predictedNum2) {
		UserPredict userPredict = new UserPredict();
		userPredict.setUID(UID);
		userPredict.setUPID(UPID);
		userPredict.setPredictedNum1(predictedNum1);
		userPredict.setPredictedNum2(predictedNum2);
		return predictRepository.updatePredict(userPredict);
	}
}
