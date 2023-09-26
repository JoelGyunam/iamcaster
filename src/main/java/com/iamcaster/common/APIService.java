package com.iamcaster.common;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iamcaster.user.dto.KakaoUserInfo;
import com.iamcaster.user.dto.OAuthToken;

@Service
public class APIService {
	

	
	
	public KakaoUserInfo fetchOAuthUserInfo(String ACCESS_TOKEN) {
		String HOST_URL = "https://kapi.kakao.com/v2/user/me";
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Content-type","application/x-www-form-urlencoded;charset=utf-8");
		headers.set("Authorization", "Bearer " + ACCESS_TOKEN);
		
		HttpEntity<MultiValueMap<String,String>> userInfoReqEntity = new HttpEntity<>(headers);
		
		ResponseEntity<String> responseUserInfo = restTemplate.exchange(
				HOST_URL
				, HttpMethod.POST
				, userInfoReqEntity
				,String.class);
		
		ObjectMapper objectMapper = new ObjectMapper();
		KakaoUserInfo kakaoUserInfo = null;
		
		try {
			kakaoUserInfo = objectMapper.readValue(responseUserInfo.getBody(), KakaoUserInfo.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return kakaoUserInfo;
	}
	
	public OAuthToken fetchOAuthToken(String code) {
		String HOST_URL = "https://kauth.kakao.com/oauth/token";
		RestTemplate restTemplate = new RestTemplate();
		
		//헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", "authorization_code");
		map.add("client_id", "c549a77834b5765bcedd1aedb20e046c");
		map.add("redirect_uri", VersionType.versionType()+"/Oauth/authorize/callback");
		map.add("code", code);
		
		HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(map,headers);
		
		//Post보내기
		ResponseEntity<String> response = restTemplate.exchange(HOST_URL
				,HttpMethod.POST
				,entity
				,String.class);
		
		if(response.getStatusCode().is5xxServerError()) {
			return null;
		}
		if(response.getStatusCode().is4xxClientError()) {
			return null;
		}
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oAuthToken = null;
		try {
			oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return oAuthToken;
	}
}
