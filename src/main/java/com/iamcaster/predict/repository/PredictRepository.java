package com.iamcaster.predict.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.iamcaster.predict.domain.UserPredict;
import com.iamcaster.predict.dto.PredictRank;
import com.iamcaster.predict.dto.RankRegion;

@Repository
public interface PredictRepository {

	public List<UserPredict> selectPredictByUID(@Param("UID")int UID);
	public int insertPredict(UserPredict userPredict);
	public int updatePredict(UserPredict userPredict);
	public List<UserPredict> selectToBeScored();
	public List<UserPredict> getUPIDbyCreatedDateAndRGID(@Param("createdDate") String createdDate, @Param("predictRGID") int predictRGID);
	public int updateAnswerByUPID(UserPredict userPredict);
	public List<UserPredict> selectAllPredictByUID(UserPredict userPredict);
	public List<PredictRank> predictResultGroupByUID(@Param(value="predictRGID") Integer RGID);
	public List<RankRegion> predictResultGroupByRGID();
	public List<UserPredict> selectTodayPredictByRGIDandWeatherType(@Param("RGID")int RGID,@Param("weatherType")String weatherType );
	public UserPredict selectTodayPredictByUIDandWeatherType(UserPredict userPredict);
}
