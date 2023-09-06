package com.iamcaster.regional.kma.sfcregion.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.iamcaster.common.WebClientForKMA;
import com.iamcaster.regional.kma.sfcregion.domain.SFCRegion;
import com.iamcaster.regional.kma.sfcregion.repository.SFCRegionRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class SFCRegionService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SFCRegionRepository sfcRegionRepository;
	@Autowired
	private WebClientForKMA webClientForKMA;

    private final WebClient webClient;

    public SFCRegionService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://apihub.kma.go.kr").build();
    }
    
    public String fetchSFCRegionList() {
        Mono<String> mono =  webClient.get()
                .uri("/api/typ01/url/fct_shrt_reg.php?tmfc=0&disp=1&help=0&authKey=8HXVgof0RqS11YKH9EakVA")
                .retrieve()
                .bodyToMono(String.class);
        
        String resultString = mono.block();
		return resultString;
    }
    
    public List<SFCRegion> parseData(String data) {
        List<SFCRegion> sfcRegionList = new ArrayList<>();
        String[] lines = data.split("\n");
        for (String line : lines) {
            if (line.startsWith("#")) {
                continue;
            }
            SFCRegion sfcRegion = parseLine(line);
            sfcRegionList.add(sfcRegion);
        }
        return sfcRegionList;
    }

    public SFCRegion parseLine(String line) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length < 5) {
            throw new IllegalArgumentException("Invalid data format");
        }
        SFCRegion sfcRegion = new SFCRegion();
        sfcRegion.setREG_ID(parts[0]);
        sfcRegion.setTM_ST(parts[1]);
        sfcRegion.setTM_ED(parts[2]);
        sfcRegion.setREG_SP(parts[3]);
        sfcRegion.setREG_NAME(parts[4]);
        return sfcRegion;
    }

   public List<SFCRegion> getList(){
	   String resultString = webClientForKMA.fetchAndToString("/api/typ01/url/fct_shrt_reg.php?tmfc=0&disp=1&help=0&authKey=8HXVgof0RqS11YKH9EakVA");
//	   List<SFCRegion> parsedResult = webClientForKMA.parseData("sfcRegion",resultString);
	   Object parsedObject = webClientForKMA.parseData("sfcRegion",resultString);
	   List<SFCRegion> parsedResult = (List<SFCRegion>) parsedObject;
	   return parsedResult;
   }

   public int refreshSFCRegionList() {
	   List<SFCRegion> SFCRegionList = new ArrayList<>();
	   SFCRegionList = getList();
	   int result = 0;
	   for(SFCRegion line : SFCRegionList) {
		   if(!line.getREG_SP().equals("C")) {
			   continue;
		   }
		   int isExists = sfcRegionRepository.countByReg_ID(line.getREG_ID());
		   if(isExists==0) {
			   result += sfcRegionRepository.insertSFCRegion(line);
			   logger.trace("ab");
		   }
	   }
	   return result;
   }
}