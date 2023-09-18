package com.iamcaster.kmaforecast.shortforecast.domain;

import java.sql.Date;
import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortForecast {

	private int SFCID;
	private String REG_ID;	//예보구역코드
	private String TM_FC;	//발표시각(e.g. 202309150000)
	private String TM_EF;	//발효시각(e.g. 202309150500)
	private String MOD;		//구간 (A01(24시간),A02(12시간))
	private int NE;			//발효번호
	private int TA;			//기온
	private int ST;			//강수확률(%)
	private String SKY;		//하늘상태코드 (DB01(맑음),DB02(구름조금),DB03(구름많음),DB04(흐림))
	private int PREP;		//강수유무코드 (1(비),2(비/눈),4(눈/비),3(눈))
	private String WF;		//예보
	private ZonedDateTime createdAt;
	private Date createdDate;
	private ZonedDateTime updatedAt;
	
}
