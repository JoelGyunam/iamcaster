package com.iamcaster.predict.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iamcaster.common.Dater;
import com.iamcaster.kmaforecast.observation.domain.Observation;
import com.iamcaster.predict.domain.UserPredict;
import com.iamcaster.predict.dto.UserPredictDelivery;
import com.iamcaster.predict.repository.PredictRepository;
import com.iamcaster.regional.userregion.service.UserRegionService;
import com.iamcaster.user.dto.UserInfoOverral;
import com.iamcaster.user.service.UserInfoService;

@Service
public class PredictService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
	
	//createdAt 이 2일 전이고 scored가 1인 prdictRGID 의 리스트
	public Map<Integer,Set<Integer>> toBeScoredList(){
		Map<Integer,Set<Integer>> waitingMap = new HashMap<>();
		List<UserPredict> waitingSelectList = predictRepository.selectToBeScored();
		Set<Integer> isYYMMDDExistsSet = new HashSet<>();
		if(waitingSelectList.size()==0) {
			return null;
		} else {
			for(UserPredict waitingSelect :waitingSelectList) {
				Set<Integer> RGIDset = new HashSet<>();
				int yymmdd = Dater.parseDateForObservation(waitingSelect.getCreatedAt());
				if(isYYMMDDExistsSet.contains(yymmdd)) {
					RGIDset = waitingMap.get(yymmdd);
					RGIDset.add(waitingSelect.getPredictRGID());
					waitingMap.put(yymmdd, RGIDset);
					isYYMMDDExistsSet.add(yymmdd);
				} else {
					RGIDset.add(waitingSelect.getPredictRGID());
					waitingMap.put(yymmdd, RGIDset);
					isYYMMDDExistsSet.add(yymmdd);
				}
			}
		}
		return waitingMap;
	}
	public int insertNewPredictAnswer(List<Observation> answerObservationList) {
		int result=0;
		if(answerObservationList.size()==0) {
			return result;
		} else {
			for(Observation answerObservation : answerObservationList) {
				int yymmdd = answerObservation.getYYMMDD_KST();
				String createdDate = Dater.reverseParser(yymmdd);
				int STNID = answerObservation.getSTN_ID();
				int RGID = userRegionService.getRegionBySTNID(STNID).getRGID();
				double minTemp = answerObservation.getTA_MIN();
				double maxTemp = answerObservation.getTA_MAX();
				double rainmm = answerObservation.getRN_DAY();
				
				List<UserPredict> userPredictList = predictRepository.getUPIDbyCreatedDateAndRGID(createdDate, RGID);
				if(userPredictList.size() == 0) {
					return result;
				} else {
					for(UserPredict eachPredict : userPredictList) {
						String weatherType=eachPredict.getWeatherType();
						if(weatherType.equals("temp")) {
							eachPredict.setRealNum1(minTemp);
							eachPredict.setRealNum2(maxTemp);
							eachPredict.setResult(tempScoring(eachPredict));
						} else if(weatherType.equals("rain")) {
							eachPredict.setRealNum1(rainmm);
							eachPredict.setResult(rainScoring(eachPredict));
						}
						result += predictRepository.updateAnswerByUPID(eachPredict);
					}
				}
			}
			return result;
		}
		
	}
	
	public String tempScoring(UserPredict userPredict) {
		double predict1 = userPredict.getPredictedNum1();
		double predict2 = userPredict.getPredictedNum2();
		double real1 = userPredict.getRealNum1();
		double real2 = userPredict.getRealNum2();
		
		double criteria1 = (predict1/real1)-1;
		if(criteria1<0) {
			criteria1 = -criteria1;
		}
		
		double criteria2 = (predict2/real2)-1;
		if(criteria2<0) {
			criteria2 = -criteria2;
		}
		
		double average = (criteria1+criteria2)/2;
		if(average<=0.1) {
			return "정확";
		} else {
			return "오보";
		}
	}
	
	public String rainScoring(UserPredict userPredict) {
		double predict1 = userPredict.getPredictedNum1();
		double real1 = userPredict.getRealNum1();
		
		double criteria1 = (predict1/real1)-1;
		if(criteria1<0) {
			criteria1 = -criteria1;
		}
		if(predict1 == 0 && real1==0) {
			return "정보";
		} else if(criteria1 <= 0.1) {
			return "정보";
		} else {
			return "오보";
		}
	}
	
	public Map<String,Integer> getScoreNumbers(int UID) {
		UserPredict userPredict = new UserPredict();
		userPredict.setUID(UID);
		List<UserPredict> predictList = predictRepository.selectAllPredictByUID(userPredict);
		Map<String,Integer> resultMap = new HashMap<>();
		int tempRight = 0;
		int tempWrong = 0;
		int rainRight = 0;
		int rainWrong = 0;
		int unScored = 0;
		
		if(userPredict!=null) {
			for(UserPredict eachPredict : predictList) {
				if(eachPredict.getScored()==0) {
					if(eachPredict.getWeatherType().equals("temp")) {
						if(eachPredict.getResult().equals("정확")) {
							tempRight ++;
							} else if(eachPredict.getResult().equals("오보")) {
								tempWrong++;
								}
						} else if(eachPredict.getWeatherType().equals("rain")) {
							if(eachPredict.getResult().equals("정확")) {
								tempRight ++;
								} else if(eachPredict.getResult().equals("오보")) {
									tempWrong++;
									}
					}
				} else {
					unScored++;
					}
				}
			}
		resultMap.put("tempPredictRight", tempRight);
		resultMap.put("tempPredictWrong", tempWrong);
		resultMap.put("rainPredictRight", rainRight);
		resultMap.put("rainPredictWrong", rainWrong);
		resultMap.put("unScored", unScored);
		return resultMap;
	}
	
}
