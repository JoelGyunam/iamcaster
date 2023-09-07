package com.iamcaster.predict.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PredictServiceTest {

	@Autowired
	private PredictService predictService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Test
	void test() {
		
//		double predict1 = userPredict.getPredictedNum1();
//		double predict2 = userPredict.getPredictedNum2();
//		double real1 = userPredict.getRealNum1();
//		double real2 = userPredict.getRealNum2();
			double predict1 = 0.1;
//			double predict2 = 35.4;
			double real1 = 0;
//			double real2 = 37.4;
			String result = "";
			
			double criteria1 = (predict1/real1)-1;
			if(criteria1<0) {
				criteria1 = -criteria1;
			}
			if(predict1 == 0 && real1==0) {
				result = "정보";
			} else if(criteria1 <= 0.1) {
				result = "정보";
			} else {
				result = "오보";
			}
			logger.info("criteria1 : "+Double.toString(criteria1));
			logger.info("result : "+result);
			
//			double criteria2 = (predict2/real2)-1;
//			if(criteria2<0) {
//				criteria2 = -criteria2;
//			}
//			logger.info("criteria2 : "+Double.toString(criteria2));
			
//			double average = (criteria1+criteria2)/2;
//			logger.info("average : "+Double.toString(average));

		}
	}
