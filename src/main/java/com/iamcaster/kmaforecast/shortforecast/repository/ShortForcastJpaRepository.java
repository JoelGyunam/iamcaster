package com.iamcaster.kmaforecast.shortforecast.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iamcaster.kmaforecast.shortforecast.domain.ShortForecast;

@Repository
public interface ShortForcastJpaRepository extends JpaRepository<ShortForecast, Integer>{

	@Query("SELECT s FROM ShortForecast s WHERE s.REG_ID = :regId AND s.createdDate = CURRENT_DATE ORDER BY s.NE ASC")
	List<ShortForecast> findTodayShortForecastsbyRegId(@Param("regId") String regId);
}
