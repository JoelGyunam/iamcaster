package com.iamcaster.user.service;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iamcaster.predict.service.PredictService;
import com.iamcaster.regional.userregion.service.UserRegionService;
import com.iamcaster.user.domain.UserInfo;
import com.iamcaster.user.dto.UserInfoOverral;
import com.iamcaster.user.repository.UserInfoRepository;

@Service
public class GetUserInfoService {

	@Autowired
	private PredictService predictService;
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private UserNicknameService userNicknameService;
	@Autowired
	private UserRegionService userRegionService;
	
	public UserInfoOverral getUserInfo(int UID) {
		UserInfo userInfo = userInfoRepository.getOneUserInfoByUID(UID);
		UserInfoOverral overral = new UserInfoOverral();
		if(userInfo == null) {
			return null;
		} else {
			overral.setUID(userInfo.getUID());
			overral.setEmail(userInfo.getEmail());
			overral.setPassword(userInfo.getPassword());
			overral.setSalt(userInfo.getSalt());
			overral.setNickID(userInfo.getNickID());
			overral.setNickname(userNicknameService.getByNickID(userInfo.getNickID()).getNickname());
			overral.setRGID(userInfo.getRGID());
			overral.setRegionName(userRegionService.getRegionByRGID(userInfo.getRGID()).getRegionName());
			overral.setCreatedAt(userInfo.getCreatedAt());
			overral.setUpdatedAt(userInfo.getUpdatedAt());
			overral.setOptionalTerms(userInfo.getOptionalTerms());
			overral.setIfKakao(userInfo.isIfKakao());
			
			int sumPredict = predictService.getScoreNumbers(UID).get("tempPredictRight")
					+ predictService.getScoreNumbers(UID).get("tempPredictWrong")
					+ predictService.getScoreNumbers(UID).get("rainPredictRight")
					+ predictService.getScoreNumbers(UID).get("rainPredictWrong")
					;
			int writeNumber = predictService.getScoreNumbers(UID).get("tempPredictRight")
					+ predictService.getScoreNumbers(UID).get("rainPredictRight");
			
			double accuracyRate = ((double)writeNumber/sumPredict)*100.0;
			if(Double.isNaN(accuracyRate)) {
				overral.setAccuracyRate("아직 예측 정보가 없어요!");
			} else {
				DecimalFormat df = new DecimalFormat("0.##");
				overral.setAccuracyRate(df.format(accuracyRate)+"%");
			}
		}
		return overral;
	}
}
