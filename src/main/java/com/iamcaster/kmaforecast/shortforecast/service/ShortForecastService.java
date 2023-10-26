package com.iamcaster.kmaforecast.shortforecast.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iamcaster.common.WebClientForKMA;
import com.iamcaster.kmaforecast.shortforecast.domain.ShortForecast;
import com.iamcaster.kmaforecast.shortforecast.repository.ShortForcastJpaRepository;
import com.iamcaster.kmaforecast.shortforecast.repository.ShortForecastRepository;
import com.iamcaster.regional.userregion.service.UserRegionService;

@Service
public class ShortForecastService {

	@Autowired
	private ShortForcastJpaRepository shortForecastJpaRepository;
	@Autowired
	private WebClientForKMA webClientForKMA;
	@Autowired
	private UserRegionService userRegionService;
	@Autowired
	private ShortForecastRepository shortForecastRepository;
	
	public List<ShortForecast> pullShortForecastFromKMA(int RGID) {
		
		String REG_ID = userRegionService.getRegionByRGID(RGID).getRegSourceID();
		String shortForecastString = webClientForKMA.fetchAndToStringForShortForecast(REG_ID);
		
		List<ShortForecast> shortForcastList = webClientForKMA.parseDataForShortForecast(shortForecastString);
		
		return shortForcastList;
	}
	
	public int upsertTodayShortForecast(int RGID) {
		List<ShortForecast> shortForcastList = pullShortForecastFromKMA(RGID);
		return shortForecastRepository.upsertShortForecastList(shortForcastList);
	}
	
	public List<ShortForecast> getShortForecastByRGID(int RGID){
		String REG_ID = userRegionService.getRegionByRGID(RGID).getRegSourceID();
		int currentHour = ZonedDateTime.now().getHour();
		
		//지역ID 기준으로, 오늘의 단기예보 정보 리스트를 db에서 셀렉트
		//리스트가 비어있거나, updatedAt의 시간이 현재 시간보다 이전이면 리스트 갱신 및 갱신된 리스트 리턴
		//리스트가 비어있지 않고, updatedAt 이 현재 조회시간에 이루어진 이력이 있으면 기존 리스트 리턴
		List<ShortForecast> forecastList = shortForecastJpaRepository.findTodayShortForecastsbyRegId(REG_ID);
		if(
				forecastList == null 
				|| forecastList.size()<1
				|| forecastList.get(0).getUpdatedAt().getHour()<currentHour
				) {
			upsertTodayShortForecast(RGID);
			return shortForecastJpaRepository.findTodayShortForecastsbyRegId(REG_ID);
		} else {
			return forecastList;
		}
	}
}
	