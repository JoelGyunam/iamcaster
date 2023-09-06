package com.iamcaster.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.iamcaster.regional.kma.obvregion.domain.OBVRegion;
import com.iamcaster.regional.kma.sfcregion.domain.SFCRegion;

import reactor.core.publisher.Mono;

@Service
public class WebClientForKMA {

	private final WebClient webClient;
	
	public WebClientForKMA(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("https://apihub.kma.go.kr").build();
	}
	
	public String fetchAndToString(String uri) {
		Mono<String> mono = webClient.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(String.class);
		
		String resultString = mono.block();
		return resultString;
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
