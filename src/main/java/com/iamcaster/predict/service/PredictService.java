package com.iamcaster.predict.service;

import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iamcaster.common.Dater;
import com.iamcaster.kmaforecast.observation.domain.Observation;
import com.iamcaster.predict.domain.UserPredict;
import com.iamcaster.predict.dto.PredictRank;
import com.iamcaster.predict.dto.RankRegion;
import com.iamcaster.predict.dto.UserPredictDelivery;
import com.iamcaster.predict.repository.PredictRepository;
import com.iamcaster.regional.userregion.domain.UserRegion;
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
	@Autowired
	private MapService mapService;
	
	public List<RankRegion> getPredictRankByRGID(){
		// 지역ID 로 groupby 된 지역 별 기온 정/오답, 강수 정/오답 정보 select
		List<RankRegion> regionRankList = predictRepository.predictResultGroupByRGID();
		regionRankList = regionRankList.stream().map(eachRank -> {
//            { "RGID" : 1, "regionRank": 1, "regionName": '대전', "lat": 36.37199, "lng": 127.3721, "regionTotal": 30, "regionTempChance": 10, "regionRainChance": 20 },
//            { "RGID": 2, "regionRank": 2, "regionName": '대구', "lat": 35.87797, "lng": 128.65296, "regionTotal": 70, "regionTempChance": 80, "regionRainChance": 40 }
			int RGID = eachRank.getPredictRGID();
			int tempCorrect = eachRank.getTempCorrect();
			int tempWrong = eachRank.getTempWrong();
			int rainCorrect = eachRank.getRainCorrect();
			int rainWrong = eachRank.getRainWrong();
			
		// 위의 정/오답 정보를 확률로 변환
			int regionTotal = tempCorrect + tempWrong + rainCorrect + rainWrong;
			double regionTempChance =  ( (double)tempCorrect / (tempCorrect+tempWrong) ) * 100;
			double regionRainChance =  ( (double)rainCorrect / (rainCorrect+rainWrong) ) * 100;
			double regionTotalChance = (((double)(tempCorrect+rainCorrect)) / regionTotal) * 100;
			eachRank.setRegionTotal(regionTotal);
			eachRank.setRegionTempChance(regionTempChance);
			eachRank.setRegionRainChance(regionRainChance);
			eachRank.setRegionTotalChance(regionTotalChance);
			
		//확률을 소수점 둘째자리 이후 버림 및 view상 출력할 변수에 set
			DecimalFormat df = new DecimalFormat("0.##");
			eachRank.setDecimalFormatTotalChance(df.format(regionTotalChance));
			eachRank.setDecimalFormatTempChance(df.format(regionTempChance));
			eachRank.setDecimalFormatRainChance(df.format(regionRainChance));
			
		//지역ID 없을 시 null 처리 및
		//위의 지역ID로 지역테이블에서 지역정보 set
			if(userRegionService.getRegionByRGID(RGID) != null) {
				UserRegion getRegionInfo = userRegionService.getRegionByRGID(RGID);
				
				String regionName = getRegionInfo.getRegionName();
				double lat = getRegionInfo.getLat();
				double lng = getRegionInfo.getLon();
				eachRank.setRegionName(regionName);
				eachRank.setLat(lat);
				eachRank.setLng(lng);
			}
			return eachRank;
		})
		.collect(Collectors.toList());
		
		//전체 정답 확률 기준으로 내림차순 정렬 (1차 rank 정렬)
	    regionRankList = regionRankList.stream()
	            .sorted((rank1, rank2) -> Double.compare(rank2.getRegionTotalChance(), rank1.getRegionTotalChance()))
	            .collect(Collectors.toList());
	    
	    // 전체 응답 횟수의 평균 * 0.05% 이내의 응답횟수는 후순위의 rank 를 줄 수 있도록의 바운더리 숫자
	    double outOfBound = (regionRankList.stream().mapToInt(RankRegion::getRegionTotal).sum() / regionRankList.size()) * 0.0005;

	    //전체 응답 횟수가 위의 outOfBound 이상인 경우, 1위 부터 정답 확률 기준으로 rank 변수에 set (2차 rank 정렬)
	    int rank = 1;
	    for (int i = 0; i < regionRankList.size(); i++) {
	    	int eachTotal = regionRankList.get(i).getRegionTotal();
	    
	    	if(eachTotal > outOfBound) {
	    		regionRankList.get(i).setRegionRank(rank);
	    		String markerSVG = mapService.markerSVG(rank, regionRankList.get(i).getRegionName());
	    		regionRankList.get(i).setMarkerSVG(markerSVG);
	    		rank ++;
	    	} 
	    }
	    
	    //전체 응답 횟수가 위의 outOfBound 이하인 경우, 2차 rank정렬 맨 후순위 부터 정답 확률 기준으로 rank 변수에 set (3차 rank 정렬)
	    for(int i=0; i < regionRankList.size(); i++) {
	    	int eachTotal = regionRankList.get(i).getRegionTotal();
	    	
	    	if(eachTotal <= outOfBound) {
	    		regionRankList.get(i).setRegionRank(rank);
	    		String markerSVG = mapService.markerSVG(rank, regionRankList.get(i).getRegionName());
	    		regionRankList.get(i).setMarkerSVG(markerSVG);
	    		rank ++;
	    	} 
	    }
		return regionRankList;
	};
	
	public Map<String,List<PredictRank>> getPredictRank(int myUID, Integer paramRGID){
		
		// 리턴 객체 생성
		Map<String,List<PredictRank>> resultMap = new HashMap<>();
		
		// 타입(전국 or 지역) 정보 전달하는 객체 typeInput 및 리스트 생성
		List<PredictRank> typeList = new ArrayList<>();
		PredictRank typeInput = new PredictRank();

		
		//param에서 받은 지역ID 가 null 이면 typeInput 내 지역ID 에 0, 
		// null이 아니면 대상지역의 지역ID 및 지역명 세팅 후 resultMap에 put
		if(paramRGID==null) {
			typeInput.setPredictRGID(0);
		} else {
			typeInput.setPredictRGID(paramRGID);
			typeInput.setRegionName(userRegionService.getRegionByRGID(paramRGID).getRegionName());
		}
		typeList.add(typeInput);
		resultMap.put("regionType", typeList);
		
		// PredictRank객체로 이루어진 rankList 리스트에 UID로 그룹바이 되고 날씨 타입별 정/오답이 sum된 정보 담음
		// 날씨 정/오답이 담긴 rankList 리스트를 스트림화 해서 toModelList 에 담음.
		// 정확도가 높은 순부터 내림차순으로 sorted
		List<PredictRank> rankList = predictRepository.predictResultGroupByUID(paramRGID);
		List<PredictRank> toModelList = rankList.stream()
		
		.filter(eachRank -> userInfoService.getUserInfo(eachRank.getUID()) != null)
		.map(eachRank -> {
			int UID = eachRank.getUID();
			int correct = eachRank.getCorrect();
			int wrong = eachRank.getWrong();
			String nickname = userInfoService.getUserInfo(UID).getNickname();
			String regionName = userInfoService.getUserInfo(UID).getRegionName();
			int sumCount = correct+wrong;
			double accuracy = ((double)correct / sumCount)*100;
			String accuracyStyring = String.format("%.2f", accuracy) + "%";
			
			eachRank.setNickname(nickname);
			eachRank.setRegionName(regionName);
			eachRank.setSumCount(sumCount);
			eachRank.setAccuracy(accuracy);
			eachRank.setAccuracyString(accuracyStyring);
			
			return eachRank;
		})
		.sorted(Comparator.comparing(PredictRank::getAccuracy).reversed())
		.collect(Collectors.toList());
		
		// 유저 응답 횟수 평균 수의 0.05% 값 구해서, 이 이하 응답한 유저의 경우, 이 값 초과 응답자 랭크 이후의 숫자를 부여할 수 있는, 기준 숫자.
		double outOfBound = 0;
		if(toModelList.size()>0) {
			outOfBound = (rankList.stream().mapToInt(PredictRank::getSumCount).sum() / toModelList.size()) * 0.0005;
		}
		
		// outOfBound 초과한 유저들 1위부터 랭크 세팅
		int rank = 1;
		for(PredictRank eachRank : toModelList) {
			if(eachRank.getSumCount() > outOfBound) {
				eachRank.setRank(rank);
				rank ++;
			}
		}
		
		// outOfBound 이하 응답 유저들, 다음 순위부터 랭크 세팅
		for(PredictRank eachRank : toModelList) {
			if(eachRank.getSumCount() <= outOfBound) {
				eachRank.setRank(rank);
				rank ++;
			}
		}
		
		//결과가 담긴 toModelList를 rankMap에 put해서 로직 종료
		resultMap.put("rankInfo", toModelList);
		
		//내 순위정보를 위한 객체 생성 및 세팅

		List<PredictRank> myList = toModelList.stream().filter(ifMe -> ifMe.getUID() == myUID).collect(Collectors.toList());
		resultMap.put("myInfo", myList);

		return resultMap;
	};
	
	
	public List<UserPredictDelivery> getListByUID(int UID){
		
		UserPredict userPredict = new UserPredict();
		userPredict.setUID(UID);
		List<UserPredict> predictList = predictRepository.selectAllPredictByUID(userPredict);
		
		List<UserPredictDelivery> predictDTOList = new ArrayList<>();
		
		if(predictList.size()==0) {
			return predictDTOList;
		} else {
			for(UserPredict predictLine : predictList) {
				UserPredictDelivery userPredictDTO = new UserPredictDelivery();
				
				int UPID = predictLine.getUPID();
				int predictUID = predictLine.getUID();
				int predictOrder = predictLine.getPredictOrder();
				int predictRGID = predictLine.getPredictRGID();
				String weatherType = predictLine.getWeatherType();
				double predictedNum1 = predictLine.getPredictedNum1();
				double predictedNum2 = predictLine.getPredictedNum2();
				double realNum1 = predictLine.getRealNum1();
				double realNum2 = predictLine.getRealNum2();
				String myPredict = "";
				String realNumber = "";
				String result = predictLine.getResult();
				int scored = predictLine.getScored();
				// scored 1 = 채점 전, scored 0 = 채점 후
				if(scored==1) {
					if(weatherType.equals("temp")) {
						weatherType = "기온 예측 <span class=\"material-icons text-dark f-small\">thermostat</span>";
						myPredict = predictedNum1 + "°C / " + predictedNum2 + "°C";
					} else if(weatherType.equals("rain")) {
						weatherType = "기온 예측 <span class=\"material-icons text-dark f-small\">water_drop</span>";
						if(predictedNum1==0) {
							myPredict = "비 안와요";
						} else {
							myPredict = predictedNum1 +"mm";
						}
					}
					result="결과 대기중";
				} else {
					if(weatherType.equals("temp")) {
						weatherType = "기온 예측 <span class=\"material-icons text-dark f-small\">thermostat</span>";
						myPredict = predictedNum1 + "°C / " + predictedNum2 + "°C";
						realNumber = realNum1 + "°C / " + realNum2 + "°C";
					} else if(weatherType.equals("rain")) {
						weatherType = "기온 예측 <span class=\"material-icons text-dark f-small\">water_drop</span>";
						realNumber = realNum1 + "mm";
						if(predictedNum1==0) {
							myPredict = "비 안와요!";
						} else {
							myPredict = predictedNum1 +"mm";
						}
					}
					
				}
				ZonedDateTime createdAt = predictLine.getCreatedAt();
				ZonedDateTime updatedAt = predictLine.getUpdatedAt();
				String regionName = userRegionService.getRegionByRGID(predictRGID).getRegionName();
				String parsedCreatedDate = Dater.dateToString(createdAt,0);
				String parsedTargetDate = Dater.dateToString(createdAt,1);
				int daysLeftToBeScored = Dater.daysLeftToBeScored(createdAt);
				
				userPredictDTO.setUPID(UPID);
				userPredictDTO.setUID(predictUID);
				userPredictDTO.setPredictOrder(predictOrder);
				userPredictDTO.setPredictRGID(predictRGID);
				userPredictDTO.setWeatherType(weatherType);
				userPredictDTO.setPredictedNum1(predictedNum1);
				userPredictDTO.setPredictedNum2(predictedNum2);
				userPredictDTO.setRealNum1(realNum1);
				userPredictDTO.setRealNum2(realNum2);
				userPredictDTO.setResult(result);
				userPredictDTO.setScored(scored);
				userPredictDTO.setCreatedAt(createdAt);
				userPredictDTO.setUpdatedAt(updatedAt);
				userPredictDTO.setRegionName(regionName);
				userPredictDTO.setParsedCreatedDate(parsedCreatedDate);
				userPredictDTO.setParsedTargetDate(parsedTargetDate);
				userPredictDTO.setMyPredict(myPredict);
				userPredictDTO.setRealNumber(realNumber);
				userPredictDTO.setDaysLeftToBeScored(daysLeftToBeScored);
				
				predictDTOList.add(userPredictDTO);
				}
			}
		return predictDTOList;
	}
	
	
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
			return "정확";
		} else if(criteria1 <= 0.1) {
			return "정확";
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
