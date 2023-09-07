package com.iamcaster.predict.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.iamcaster.predict.domain.UserPredict;

@Repository
public interface PredictRepository {

	public List<UserPredict> selectPredictByUID(@Param("UID")int UID);
	
	public int insertPredict(UserPredict userPredict);
	public int updatePredict(UserPredict userPredict);
	public List<UserPredict> selectToBeScored();
	public List<UserPredict> getUPIDbyCreatedDateAndRGID(@Param("createdDate") String createdDate, @Param("predictRGID") int predictRGID);
	public int updateAnswerByUPID(UserPredict userPredict);
}
