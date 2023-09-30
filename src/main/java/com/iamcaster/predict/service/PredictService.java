package com.iamcaster.predict.service;

import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
import com.iamcaster.kmaforecast.shortforecast.service.ShortForecastService;
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
	@Autowired
	private ShortForecastService shortForecastService;
	
	public List<RankRegion> getPredictRankByRGID(){
		// 지역ID 로 groupby 된 지역 별 기온 정/오답, 강수 정/오답 정보 select
		List<RankRegion> regionRankList = predictRepository.predictResultGroupByRGID();
		
		if(regionRankList.size()==0) {
			return regionRankList;
		} 
		
		else
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
						weatherType = "강수 예측 <span class=\"material-icons text-dark f-small\">water_drop</span>";
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
	
	
	public Map<Integer, UserPredictDelivery> getTodayPredictByUID(int UID){

		UserInfoOverral userSummary = userInfoService.getUserInfo(UID);
		int RGID = userSummary.getRGID();
		String regionName = userSummary.getRegionName();

		// 리턴 대상 맵 생성
		Map<Integer, UserPredictDelivery> predictViewMap = new HashMap<>();
		
		//사용자 지역으로 첫 2개 카드(나의 지역 기온/강수) 세팅 및 나머지 4개 카드 더미 데이터 세팅.
		for(int i = 1; i <= 6; i++) {
			UserPredictDelivery defaultSetting = new UserPredictDelivery();
			defaultSetting.setPredictRGID(RGID);
			defaultSetting.setRegionName(regionName);
			defaultSetting.setPredictOrder(i);
			String shortForecastAMString = "";
			String shortForecastPMString = "";
			String highUserPredictMin = "";
			String highUserPredictMax = "";
			String highUserPredictRain = "";
			if(i == 1) {
				shortForecastAMString = guideNumber(RGID,"temp","kma").get("AM");
				shortForecastPMString = guideNumber(RGID,"temp","kma").get("PM");
				highUserPredictMin = guideNumber(RGID,"temp","highUser").get("min");
				highUserPredictMax = guideNumber(RGID,"temp","highUser").get("max");
				defaultSetting.setHighUserPredict1(highUserPredictMin);
				defaultSetting.setHighUserPredict2(highUserPredictMax);
				defaultSetting.setWeatherType("temp");
			} else if(i == 2) {
				shortForecastAMString = guideNumber(RGID,"rain","kma").get("AM");
				shortForecastPMString = guideNumber(RGID,"rain","kma").get("PM");
				highUserPredictRain = guideNumber(RGID,"rain","highUser").get("rain");
				defaultSetting.setHighUserPredict1(highUserPredictRain);
				defaultSetting.setWeatherType("rain");
			} else if(i%2 != 0) {
				defaultSetting.setPredictRGID(0);
				defaultSetting.setRegionName(null);
				defaultSetting.setWeatherType("temp");
			} else if(i%2 == 0) {
				defaultSetting.setPredictRGID(0);
				defaultSetting.setRegionName(null);
				defaultSetting.setWeatherType("rain");
			}
			defaultSetting.setShortForecastAMString(shortForecastAMString);
			defaultSetting.setShortForecastPMString(shortForecastPMString);
			
			predictViewMap.put(i, defaultSetting);
		}
		
		// createdAt이 오늘 인 유저의 predict 리스트로 받음.
		List<UserPredict> predictList = predictRepository.selectPredictByUID(UID);
		if(predictList.size()==0) {
			return predictViewMap;
		} else {
			for(int j = 0; j < predictList.size(); j++) {
				
				int predictOrder = predictList.get(j).getPredictOrder();
				
				UserPredictDelivery userPredictDTO = new UserPredictDelivery();
				int UPID = predictList.get(j).getUPID();
				int thisUID = predictList.get(j).getUID();
				int predictRGID = predictList.get(j).getPredictRGID();
				String weatherType = predictList.get(j).getWeatherType();
				double predictedNum1 = predictList.get(j).getPredictedNum1();
				double predictedNum2 = predictList.get(j).getPredictedNum2();

				ZonedDateTime createdAt = predictList.get(j).getCreatedAt();
				ZonedDateTime updatedAt = predictList.get(j).getUpdatedAt();
				String thisRegionName = userRegionService.getRegionByRGID(predictList.get(j).getPredictRGID()).getRegionName();
				String shortForecastAMString = "";
				String shortForecastPMString = "";
				String highUserPredictMin = "";
				String highUserPredictMax = "";
				String highUserPredictRain = "";
				if(weatherType.equals("temp")) {
					shortForecastAMString = guideNumber(predictRGID,"temp","kma").get("AM");
					shortForecastPMString = guideNumber(predictRGID,"temp","kma").get("PM");
					highUserPredictMin = guideNumber(predictRGID,"temp","highUser").get("min");
					highUserPredictMax = guideNumber(predictRGID,"temp","highUser").get("max");
					userPredictDTO.setHighUserPredict1(highUserPredictMin);
					userPredictDTO.setHighUserPredict2(highUserPredictMax);
				} else if(weatherType.equals("rain")){
					shortForecastAMString = guideNumber(predictRGID,"rain","kma").get("AM");
					shortForecastPMString = guideNumber(predictRGID,"rain","kma").get("PM");
					highUserPredictRain = guideNumber(predictRGID,"rain","highUser").get("rain");
					userPredictDTO.setHighUserPredict1(highUserPredictRain);
				}
				userPredictDTO.setUPID(UPID);
				userPredictDTO.setPredictOrder(predictOrder);
				userPredictDTO.setUID(thisUID);
				userPredictDTO.setPredictRGID(predictRGID);
				userPredictDTO.setWeatherType(weatherType);
				userPredictDTO.setPredictedNum1(predictedNum1);
				userPredictDTO.setPredictedNum2(predictedNum2);
				userPredictDTO.setCreatedAt(createdAt);
				userPredictDTO.setUpdatedAt(updatedAt);
				userPredictDTO.setRegionName(thisRegionName);
				userPredictDTO.setShortForecastAMString(shortForecastAMString);
				userPredictDTO.setShortForecastPMString(shortForecastPMString);
				predictViewMap.put(predictOrder,userPredictDTO);
				
				//predictOrder 1,2,4 가 리스트에 담길 경우 3에 대한 처리 혹은 1,2,3 일 떄 4에 대한 처리
				// 두번째 이상 리스트 순번 이거나, 마지막 순번인 경우, 3 에 대한 처리
				if(predictOrder > 3 && predictOrder - predictList.get(j-1).getPredictOrder() != 1) {
					//건너뛴 predictOrder가 1 이상일 때, 몇개를 건너뛰었는지 숫자.
					int howManyLeft = predictOrder - predictList.get(j-1).getPredictOrder()-1;
					//건너뛴 숫자만큼 for문으로 채워줌.
					for(int k = 0; k < howManyLeft; k++) {
						
						UserPredictDelivery defaultSetting = new UserPredictDelivery();
						int predictOrderForNull = (predictList.get(j).getPredictOrder())-1-k;
						int RGIDForNull =  predictList.get(j-k).getPredictRGID();
						String regionNameForNull =  userRegionService.getRegionByRGID(RGIDForNull).getRegionName();
						defaultSetting.setPredictRGID(RGIDForNull); //
						defaultSetting.setRegionName(regionNameForNull);
						defaultSetting.setPredictOrder(predictOrderForNull);
						String shortForecastAMStringForNull = "";
						String shortForecastPMStringForNull = "";
						
						if(predictOrderForNull % 2 != 0) {
							shortForecastAMStringForNull = guideNumber(RGIDForNull,"temp","kma").get("AM");
							shortForecastPMStringForNull = guideNumber(RGIDForNull,"temp","kma").get("PM");
							highUserPredictMin = guideNumber(RGID,"temp","highUser").get("min");
							highUserPredictMax = guideNumber(RGID,"temp","highUser").get("max");
							defaultSetting.setWeatherType("temp");
							defaultSetting.setHighUserPredict1(highUserPredictMin);
							defaultSetting.setHighUserPredict2(highUserPredictMax);
						} else if(predictOrderForNull % 2 == 0) {
							shortForecastAMStringForNull = guideNumber(RGIDForNull,"rain","kma").get("AM");
							shortForecastPMStringForNull = guideNumber(RGIDForNull,"rain","kma").get("PM");
							highUserPredictRain = guideNumber(RGID,"rain","highUser").get("rain");
							defaultSetting.setHighUserPredict1(highUserPredictRain);
							defaultSetting.setWeatherType("rain");
						}
						defaultSetting.setShortForecastAMString(shortForecastAMStringForNull);
						defaultSetting.setShortForecastPMString(shortForecastPMStringForNull);
						predictViewMap.put(predictOrderForNull, defaultSetting);
					}
				};
				
				// 현재 순번이 마지막인데 짝수가 아니라면 (4에 대한 처리)
				if(j+1 == predictList.size() && predictOrder % 2  != 0){
					UserPredictDelivery defaultSetting = new UserPredictDelivery();
					int predictOrderForNull = predictOrder+1;
					int RGIDForNull =  predictList.get(j).getPredictRGID();
					String regionNameForNull =  userRegionService.getRegionByRGID(RGIDForNull).getRegionName();
					defaultSetting.setPredictRGID(RGIDForNull); //
					defaultSetting.setRegionName(regionNameForNull);
					defaultSetting.setPredictOrder(predictOrderForNull);
					String shortForecastAMStringForNull = "";
					String shortForecastPMStringForNull = "";
					if(predictOrderForNull % 2 != 0) {
						shortForecastAMStringForNull = guideNumber(RGIDForNull,"temp","kma").get("AM");
						shortForecastPMStringForNull = guideNumber(RGIDForNull,"temp","kma").get("PM");
						highUserPredictMin = guideNumber(RGID,"temp","highUser").get("min");
						highUserPredictMax = guideNumber(RGID,"temp","highUser").get("max");
						defaultSetting.setWeatherType("temp");
						defaultSetting.setHighUserPredict1(highUserPredictMin);
						defaultSetting.setHighUserPredict2(highUserPredictMax);
					} else if(predictOrderForNull % 2 == 0) {
						shortForecastAMStringForNull = guideNumber(RGIDForNull,"rain","kma").get("AM");
						shortForecastPMStringForNull = guideNumber(RGIDForNull,"rain","kma").get("PM");
						highUserPredictRain = guideNumber(RGID,"rain","highUser").get("rain");
						defaultSetting.setWeatherType("rain");
						defaultSetting.setHighUserPredict1(highUserPredictRain);
					}
					defaultSetting.setShortForecastAMString(shortForecastAMStringForNull);
					defaultSetting.setShortForecastPMString(shortForecastPMStringForNull);
					predictViewMap.put(predictOrderForNull, defaultSetting);
				}
			}
		}
		return predictViewMap;
	};
	
	
	public Map<Integer, UserPredictDelivery> newCards(int RGID, int order){
		Map<Integer, UserPredictDelivery> predictViewMap = new HashMap<>();

		//사용자 지역으로 첫 2개 카드(나의 지역 기온/강수) 세팅.
		for(int i = order; i <= order+1; i++) {
			UserPredictDelivery defaultSetting = new UserPredictDelivery();
			defaultSetting.setPredictRGID(RGID); //
			String regionName = userRegionService.getRegionByRGID(RGID).getRegionName();
			defaultSetting.setRegionName(regionName);
			defaultSetting.setPredictOrder(i);
			String shortForecastAMString = "";
			String shortForecastPMString = "";
			String highUserPredictMin = "";
			String highUserPredictMax = "";
			String highUserPredictRain = "";
			if(i%2 != 0) {
				shortForecastAMString = guideNumber(RGID,"temp","kma").get("AM");
				shortForecastPMString = guideNumber(RGID,"temp","kma").get("PM");
				highUserPredictMin = guideNumber(RGID,"temp","highUser").get("min");
				highUserPredictMax = guideNumber(RGID,"temp","highUser").get("max");
				defaultSetting.setWeatherType("temp");
				defaultSetting.setHighUserPredict1(highUserPredictMin);
				defaultSetting.setHighUserPredict2(highUserPredictMax);
			} else if(i%2 == 0) {
				shortForecastAMString = guideNumber(RGID,"rain","kma").get("AM");
				shortForecastPMString = guideNumber(RGID,"rain","kma").get("PM");
				highUserPredictRain = guideNumber(RGID,"rain","highUser").get("rain");
				defaultSetting.setWeatherType("rain");
				defaultSetting.setHighUserPredict1(highUserPredictRain);
			}
			defaultSetting.setShortForecastAMString(shortForecastAMString);
			defaultSetting.setShortForecastPMString(shortForecastPMString);
			
			predictViewMap.put(i, defaultSetting);
		}
		return predictViewMap;
	}
	
	
	
	//type=temp/rain, source=kma(기상청)/highUser(고득점유저)
	public Map<String,String> guideNumber(int RGID, String type, String source) {
		Map<String,String> resultMap = new HashMap<>();
		if(source.equals("kma")) {
			int tempAM = shortForecastService.getShortForecastByRGID(RGID).get(2).getTA();
			int tempPM = shortForecastService.getShortForecastByRGID(RGID).get(3).getTA();
			int rainAM = shortForecastService.getShortForecastByRGID(RGID).get(2).getST();
			int rainPM = shortForecastService.getShortForecastByRGID(RGID).get(3).getST();
			if(type.equals("temp")) {
				resultMap.put("ifNull", "notNull");
				resultMap.put("AM", (tempAM + "℃"));
				resultMap.put("PM", (tempPM + "℃"));
			} else if(type.equals("rain")) {
				resultMap.put("ifNull", "notNull");
				resultMap.put("AM", (rainAM + "%"));
				resultMap.put("PM", (rainPM + "%"));
			}
		} else if(source.equals("highUser")) {
			Integer highestUID = getHighestUser(RGID, type);
			if(highestUID == null) {
				resultMap.put("ifNull", "null");
			} else {
				UserPredict userPredict = new UserPredict();
				userPredict.setUID(highestUID);
				userPredict.setWeatherType(type);
				userPredict.setPredictRGID(RGID);
				
				userPredict = predictRepository.selectTodayPredictByUIDandWeatherType(userPredict);
				if(userPredict != null) {
					if(type.equals("temp")) {
						resultMap.put("ifNull", "notNull");
						resultMap.put("min", (userPredict.getPredictedNum1() + "℃"));
						resultMap.put("max", (userPredict.getPredictedNum2() + "℃"));
					} else if(type.equals("rain")) {
						resultMap.put("ifNull", "notNull");
						resultMap.put("rain", (userPredict.getPredictedNum1() + "mm"));
					}
				}
			}
		}
		return resultMap;
	};
	
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
	};
	
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
	};
	
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
								rainRight ++;
								} else if(eachPredict.getResult().equals("오보")) {
									rainWrong++;
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
	
	//오늘 해당 지역에 응답한 유저 리스트 > getScoreNumbers 로 예측 정확도로 정렬 > 50% 이상의 예측정확도 높은 유저 픽
	public Integer getHighestUser(int RGID, String weatherType) {
		//지역아이디와 예측 종류에 따라 해당 일자에 응답한 유저응답정보리스트 불러오기
		List<UserPredict> predictList= predictRepository.selectTodayPredictByRGIDandWeatherType(RGID, weatherType);
		if(predictList.size()==0 || predictList.isEmpty()) {
			return null;
		} else {
			List<PredictRank> rankList = new ArrayList<>();
			for(UserPredict each : predictList) {
				int eachUID = each.getUID();
				Map<String,Integer> scoreMap = getScoreNumbers(eachUID);
				
				double accuracy = 0;
				
				if(weatherType.equals("temp")) {
					int tempRight = scoreMap.get("tempPredictRight");
					int tempWrong = scoreMap.get("tempPredictWrong");
					accuracy = (double)tempRight / (double)(tempRight+tempWrong);
				} else if(weatherType.equals("rain")) {
					int rainRight = scoreMap.get("rainPredictRight");
					int rainWrong = scoreMap.get("rainPredictWrong");
					accuracy = (double)rainRight / (double)(rainRight+rainWrong);
				}
				
				PredictRank toRank = new PredictRank();
				toRank.setUID(eachUID);
				toRank.setAccuracy(accuracy);
				rankList.add(toRank);
			}
			rankList.sort((compare1,compare2) -> Double.compare(compare2.getAccuracy(), compare1.getAccuracy()));
			int highestUID = rankList.get(0).getUID();
			return highestUID;
		}
	}
}
