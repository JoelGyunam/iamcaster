<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
	<title>나도캐스터 - 나의 정확도</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="/static/css/style.css" type="text/css">

<!-- Google tag (gtag.js) -->
<script async src="https://www.googletagmanager.com/gtag/js?id=G-XSSQ6SECB8"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'G-XSSQ6SECB8');
</script>

</head>
<body>
	<div id="wrap">
		<jsp:include page="/WEB-INF/jsp/global/header.jsp"/>
		
		<section id="content" class="border-top">
			<div style="height:10px"></div>
			<div class="d-flex ml-2 align-items-center">
				<div class="bg-primary text-white f-small px-3 rounded">안내</div>
				<div class=" f-small ml-3 d-flex align-items-center font-weight-bold">기온예측은 ± 10%의 오차범위를 인정하여 계산됩니다.</div>
			</div>
			
			<div class="card my-2">
				<div class="f-content font-weight-bold m-3">정확도 통계</div>
				
				<div class="m-3">
					<canvas id="donutChart"></canvas>
				</div>
			
				<div class="m-3">
					<div class="d-flex align-items-center justify-content-center">
						<div class="bg-warning rounded" style="height:10px; width:20px;"></div><div class="f-small mx-2">전체 예측 성공률 <span id="totalChance" class="font-weight-bold">100%</span></div>
					</div>
					<div class="d-flex align-items-center justify-content-center">
						<div class="bg-primary rounded" style="height:10px; width:20px;"></div><div class="f-small mx-2">기온 예측 성공률 <span id="tempChance" class="font-weight-bold">100%</span></div>
					</div>
					<div class="d-flex align-items-center justify-content-center">
						<div class="bg-success rounded" style="height:10px; width:20px;"></div><div class="f-small mx-2">강수 예측 성공률 <span id="rainChance" class="font-weight-bold">100%</span></div>
					</div>
				</div>
				
				<div class="m-3">
					<canvas id="barChart"></canvas>
				</div>
			</div>
			
			<div class="card my-2">
				<div class="f-content font-weight-bold m-3">기록</div>

				<c:choose>
				<c:when test="${empty predictList}">
				<div class="f-content m-5 text-center" onclick="location.href='/main/predict'">
					<div class="m-3">아직 예측한 예보가 없어요.</div>
					<div class="m-3">지금 바로 내일 날씨를 예측해보고, 나의 정확도를 확인해 보세요!</div>
					<button class="m-3 btn btn-primary">날씨 예측하기</button>
				</div>
				</c:when>
				<c:otherwise>
				<div>
					<table class="table f-small text-center m-2">
						<thead>
							<tr>
								<th>날짜 </th>
								<th>지역 </th>
								<th>예측 구분 </th>
								<th>나의 예측 </th>
								<th>실제 날씨 </th>
								<th>결과 날씨 </th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="predict" items="${predictList }">
							<tr>
								<td>${predict.parsedTargetDate }<br><span class="f-xsmall">(예측 제출일:${predict.parsedCreatedDate })</span></td>
								<td>${predict.regionName}</td>
								<td valign="middle" align="center">${predict.weatherType}</td>
								<td>${predict.myPredict}</td>
						<c:choose>
							<c:when test="${predict.result eq '결과 대기중' }">
								<td colspan="2" class="text-success">${predict.result }<br><span class="f-xsmall">${predict.daysLeftToBeScored}일 후에 결과를 확인할 수 있어요!</span></td>
							</c:when>
							<c:otherwise>
								<td>${predict.realNumber}</td>
								<c:choose>
									<c:when test="${predict.result eq '정확' }">
										<td class="text-primary">${predict.result }👍</td>
									</c:when>
									<c:otherwise>
										<td>${predict.result }</td>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
				</c:otherwise>
				</c:choose>				
			</div>
			<div style="height:5px"></div>
		</section>
	
<%-- 모달 스크립트 시작 --%>

		<button id="openNewCardBtn" type="button" class="btn btn-primary d-none" data-toggle="modal" data-target="#openNewCardModal">
		  Launch demo modal
		</button>
		
		<!-- Modal -->
		<div class="modal fade px-5" id="openNewCardModal" tabindex="-1" aria-labelledby="emailDupAlert" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered">
		    <div class="modal-content">
		      <div class="modal-body text-center f-content">
		      	<div>
			     	<label class="f-content font-weight-bold">어느 지역의 날씨를 예측할까요?</label>
					<select id="whichRegion" class="form-control">
						<option selected disabled>지역을 선택해주세요!</option>
						<c:forEach var="region" items="${regionList }">
						<option value="${region.RGID }">${region.regionName }</option>
						</c:forEach>
					</select>
		      	</div>
		      </div>
		      <div class="modal-footer d-flex justify-content-center">
		        <button id="regionSelectBtn" type="button" class="btn btn-primary" data-dismiss="modal" disabled>선택하기</button>
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
		      </div>
		    </div>
		  </div>
		</div>

<%-- 모달 스크립트 끝--%>
	</div>
	
	<c:forEach var="score" items="${scoreNumbers }">
		<input type="hidden" id="${score.key }" value="${score.value }">
	</c:forEach>

	
	<jsp:include page="/WEB-INF/jsp/global/footer.jsp"/>

	<script src="https://code.jquery.com/jquery-3.6.4.min.js" integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@0.7.0"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	

	<script>
		$(document).ready(function(){
			
			
		});

	</script>
	<script>
	    const ctxForBarChart = document.getElementById('barChart');
	    var tempPredictRight = Number($("#tempPredictRight").val());
	    var tempPredictWrong = Number($("#tempPredictWrong").val());
	    var rainPredictRight = Number($("#rainPredictRight").val());
	    var rainPredictWrong = Number($("#rainPredictWrong").val());
	    var totalChance = ((tempPredictRight+rainPredictRight)/(tempPredictRight+rainPredictRight+tempPredictWrong+rainPredictWrong))*1000;
	    var tempChance = (tempPredictRight/(tempPredictRight+tempPredictWrong))*1000;
	    var rainChance = (rainPredictRight/(rainPredictRight+rainPredictWrong))*1000;
	    if(isNaN(totalChance)){
	    	totalChance = Number(0);
	    }
	    if(isNaN(tempChance)){
	    	tempChance = Number(0);
	    }
	    if(isNaN(rainChance)){
	    	rainChance = Number(0);
	    }
	    
	    var totalPredictLabel = "전체 예측 ["+(tempPredictRight+rainPredictRight)+"/"+(tempPredictRight+rainPredictRight+tempPredictWrong+rainPredictWrong)+"]";
	    var tempPredictLabel = "기온 예측 ["+(tempPredictRight)+"/"+(tempPredictRight+tempPredictWrong)+"]";
	    var rainPredictLabel = "강수 예측 ["+(rainPredictRight)+"/"+(rainPredictRight+rainPredictWrong)+"]";
	
		$(document).ready(function(){
			if(isNaN(totalChance)){
		    	$("#totalChance").text("0%");
			} else{
			    $("#totalChance").text((totalChance/10).toFixed(2) + "%");
			};
			if(isNaN(tempChance)){
		    	$("#tempChance").text("0%");
			} else{
			    $("#tempChance").text((tempChance/10).toFixed(2) + "%");
			};
			if(isNaN(rainChance)){
		    	$("#rainChance").text("0%");
			} else{
			    $("#rainChance").text((rainChance/10).toFixed(2) + "%");
			};
		    
		});

	    new Chart(ctxForBarChart, {
	        type: 'bar',
	        data: {
	        labels: [totalPredictLabel, tempPredictLabel, rainPredictLabel],
	        datasets: 
	            [
	                {
	                label: {display:false},
	                data: [totalChance,tempChance,rainChance],
	                borderWidth: 'auto',
	                barThickness:10,
	                backgroundColor:['#007BFF'],
	                borderRadius:10,
	                },
	                {label: {display:false},
	                data: [1000,1000,1000],
	                borderWidth: 'auto',
	                borderRadius:10,
	                barThickness:10,
	                backgroundColor:['#C2DFFF'],}
	                
	            ]
	        },
	        options: {
	            indexAxis:'y',
	            maintainAspectRatio: false,
	            scales: {
	                y: {
	                tick: {count: 10},
	                beginAtZero: true,
	                border:{display:false},
	                grid:{display:false},
	                stacked:true,
	                tick:  {spacing: 1}
	                },
	                x: {
	                border:{display:false},
	                grid:{display:false},
	                ticks:{display:false},
	                stacked:false
	                },
	            },
	            plugins:{
	                tooltip: {
	                    enabled: false
	                },
	                legend:{
	                    display:false,
	                }
	            },
	            
	        },
	    });
	    
	    
	    const ctxForDonutChart = document.getElementById('donutChart');
	    new Chart(ctxForDonutChart, {
	        type: 'doughnut',
	        data: {

	        datasets: 
	            [
	                {
	                    label: "전체 예측 성공률",
	                    data: [totalChance,1000-totalChance],
	                    borderWidth: 0,
	                    backgroundColor:['#FFC107','#FFE69A'],
	                    borderRadius:[10],
	                }
	                ,{
	                    label: "기온 예측 성공률",
	                    data: [tempChance,1000-tempChance],
	                    borderWidth: 0,
	                    borderRadius:10,
	                    backgroundColor:['#007BFF','#C2DFFF'],

	                }
	                ,{
	                    label: "강수 예측 성공률",
	                    data: [rainChance,1000-rainChance],
	                    borderWidth: 0,
	                    borderRadius:10,
	                    backgroundColor:['#1E973A','#A4EBB4'],
	                }
	            ]
	        },
	        options: {
	            maintainAspectRatio: false,
	            responsive: true,
	            plugins:{
	                tooltip: {
	                    enabled: false,
	                },
	                legend:{
	                    display:true,
	                }
	            },
	            
	            
	        },
	    });
	</script>

</body>
</html>