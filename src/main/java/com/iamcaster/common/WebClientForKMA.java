package com.iamcaster.common;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.iamcaster.kmaforecast.observation.domain.Observation;
import com.iamcaster.kmaforecast.shortforecast.domain.ShortForecast;
import com.iamcaster.regional.kma.obvregion.domain.OBVRegion;
import com.iamcaster.regional.kma.sfcregion.domain.SFCRegion;

import reactor.core.publisher.Mono;

@Service
public class WebClientForKMA {

	private final WebClient webClient;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public WebClientForKMA(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("https://apihub.kma.go.kr").build();
	}
	
	public String fetchAndToStringForShortForecast(String REG_ID) {
		String parameter = "/api/typ01/url/fct_afs_dl.php?disp=1&authKey=8HXVgof0RqS11YKH9EakVA&timfc=0&reg="+REG_ID;
		Mono<String> mono = webClient.get()
				.uri(parameter)
				.retrieve()
				.bodyToMono(String.class);
		
		String resultString = mono.block();
		return resultString;
	}
	
	public List<ShortForecast> parseDataForShortForecast(String data){
		List<ShortForecast> shortForecastList = new ArrayList<>();
		String[] lines = data.trim().split("\n");
		for(String line : lines) {
			if(line.startsWith("#")) {
				continue;
			}
			ShortForecast shortForecast = lineParserForShortForecast(line);
			shortForecastList.add(shortForecast);
		}
		return shortForecastList;
	}
	
	public ShortForecast lineParserForShortForecast(String line) {
		String[] parts = line.trim().split(",");
		if(parts.length < 17) {
			throw new IllegalArgumentException("유효하지 않은 데이터 인입");
		}
		
		ShortForecast shortForecast = new ShortForecast();
		
		String REG_ID = parts[0];	//예보구역코드
		String TM_FC = parts[1];	//발표시각(e.g. 202309150500)
		String TM_EF = parts[2];	//발효시각(e.g. 202309150500)
		String MOD = parts[3];		//구간 (A01(24시간),A02(12시간))
		int NE = Integer.parseInt(parts[4]);			//발효번호
		int TA = Integer.parseInt(parts[12]);			//기온
		int ST = Integer.parseInt(parts[13]);			//강수확률(%)
		String SKY = parts[14];		//하늘상태코드 (DB01(맑음),DB02(구름조금),DB03(구름많음),DB04(흐림))
		int PREP = Integer.parseInt(parts[15]);		//강수유무코드 (1(비),2(비/눈),4(눈/비),3(눈))
		String WF = parts[16];		//예보
		
		shortForecast.setREG_ID(REG_ID);
		shortForecast.setTM_FC(TM_FC);
		shortForecast.setTM_EF(TM_EF);
		shortForecast.setMOD(MOD);
		shortForecast.setNE(NE);
		shortForecast.setTA(TA);
		shortForecast.setST(ST);
		shortForecast.setSKY(SKY);
		shortForecast.setPREP(PREP);
		shortForecast.setWF(WF);
		
		return shortForecast;
	}
	
	public String fetchAndToStringForObservation(int parsedDate,List<Integer> STN_ID) {
		
		String stnIDString = "";
		if(STN_ID.size()==1) {
			stnIDString = STN_ID.get(0).toString();
		} else if(STN_ID.size() > 1) {
			for(Integer oneSTN_ID : STN_ID) {
				stnIDString += oneSTN_ID.toString().concat(":");
			}
		}
		String parameter = "/api/typ01/url/kma_sfcdd.php?authKey=8HXVgof0RqS11YKH9EakVA&tm="+parsedDate+"&stn="+stnIDString;
		logger.info(parameter, stnIDString, parameter);;
		Mono<String> mono = webClient.get()
				.uri(parameter)
				.retrieve()
				.bodyToMono(String.class);
		
		String resultString = mono.block();
		return resultString;
	}
	
	public String fetchAndToString(String uri) {
		Mono<String> mono = webClient.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(String.class);
		
		String resultString = mono.block();
		return resultString;
	}
	
	public List<Observation> parseDataForObservation(String data){
		List<Observation> observationList = new ArrayList<>();
		String[] lines = data.split("\n");
		for(String line : lines) {
			if(line.startsWith("#")) {
				continue;
			}
			Observation observation = lineParserForObservation(line);
			observationList.add(observation);
		}
		return observationList;
	}
	
	public Object parseData(String source, String data) {
        List<OBVRegion> obvRegionList = new ArrayList<>();
        List<SFCRegion> sfcRegionList = new ArrayList<>();
        
        String[] lines = data.split("\n");
        for(String line : lines) {
        	if(line.startsWith("#")) {
        		continue;
        	}
        	if(source.equals("obvRegion")) {
        		OBVRegion obvRegion = lineParserForOBVRegion(line);
        		obvRegionList.add(obvRegion);
        	} else if(source.equals("sfcRegion")) {
        		SFCRegion sfcRegion = lineParserForSFCRegion(line);
        		sfcRegionList.add(sfcRegion);
        	}
        }
        if(source.equals("obvRegion")) {
        	return obvRegionList;
        } else if(source.equals("sfcRegion")) {
        	return sfcRegionList;
        } else return null;
	}
	
	public Observation lineParserForObservation(String line) {
		String[] parts = line.trim().split(",");
		if(parts.length < 56) {
			throw new IllegalArgumentException("유효하지 않은 데이터 인입");
		}
		Observation observation = new Observation();
		
		int YYMMDD_KST = Integer.parseInt(parts[0]);
		int STN_ID = Integer.parseInt(parts[1]);
		double setTA_AVG = Double.parseDouble(parts[10]);
		double setTA_MAX = Double.parseDouble(parts[11]);
		double setTA_MIN = Double.parseDouble(parts[13]);
		double setRN_DAY = Double.parseDouble(parts[39]);
		if(setRN_DAY<0.0) {
			setRN_DAY = 0.0;
		};
		
		observation.setYYMMDD_KST(YYMMDD_KST);
		observation.setSTN_ID(STN_ID);
		observation.setTA_AVG(setTA_AVG);
		observation.setTA_MAX(setTA_MAX);
		observation.setTA_MIN(setTA_MIN);
		observation.setRN_DAY(setRN_DAY);
		
		return observation;
	}
	
	public OBVRegion lineParserForOBVRegion(String line) {
		String[] parts = line.trim().split("\s+");
		if(parts.length < 15) {
			throw new IllegalArgumentException("유효하지 않은 데이터 인입");
		}
		OBVRegion obvRegion = new OBVRegion();
		obvRegion.setSTN_ID(Integer.parseInt(parts[0]));
		obvRegion.setLON(Double.parseDouble(parts[1]));
		obvRegion.setLAT(Double.parseDouble(parts[2]));
		obvRegion.setSTN_KO(parts[10]);
		obvRegion.setFCT_ID(parts[12]);
		obvRegion.setLAW_ID(parts[13]);
		return obvRegion;
	}
	
	public SFCRegion lineParserForSFCRegion(String line) {
		String[] parts = line.trim().split("\s+");
		if(parts.length < 5) {
			throw new IllegalArgumentException("유효하지 않은 데이터 인입");
		}
		SFCRegion sfcRegion = new SFCRegion();
		sfcRegion.setREG_ID(parts[0]);
		sfcRegion.setTM_ST(parts[1]);
		sfcRegion.setTM_ED(parts[2]);
		sfcRegion.setREG_SP(parts[3]);
		sfcRegion.setREG_NAME(parts[4]);
		return sfcRegion;
	}
}
