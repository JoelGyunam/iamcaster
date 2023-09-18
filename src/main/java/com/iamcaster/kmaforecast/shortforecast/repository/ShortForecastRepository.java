package com.iamcaster.kmaforecast.shortforecast.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.iamcaster.kmaforecast.shortforecast.domain.ShortForecast;

@Repository
public interface ShortForecastRepository {

	public int upsertShortForecastList(List<ShortForecast> shortForecastList);
	public List<ShortForecast> selectTodayShortForecast(String REG_ID);
}
