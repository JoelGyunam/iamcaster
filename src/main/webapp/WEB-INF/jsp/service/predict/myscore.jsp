<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
	<title>나도캐스터 - 나의 정확도</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="/static/css/style.css" type="text/css">
</head>
<body>
	<div id="wrap">
		<jsp:include page="/WEB-INF/jsp/global/header.jsp"/>
		
		<section id="content" class="container border-top">
			<div style="height:5px"></div>
			<div class="d-flex container ml-2 align-items-center">
				<div class="bg-primary text-white f-small px-3 rounded">안내</div>
				<div class=" f-small ml-3 d-flex align-items-center font-weight-bold">기온예측은 ± 10%의 오차범위를 인정하여 계산됩니다.</div>
			</div>
			
			<div class="card my-2">
				<div class="f-content font-weight-bold m-2">정확도 통계</div>
				<div class="m-3">
					<canvas id="myChart"></canvas>
				</div>
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
	    const ctx = document.getElementById('myChart');
	    var tempPredictRight = Number($("#tempPredictRight").val());
	    var tempPredictWrong = Number($("#tempPredictWrong").val());
	    var rainPredictRight = Number($("#rainPredictRight").val());
	    var rainPredictWrong = Number($("#rainPredictWrong").val());
	    var totalChance = ((tempPredictRight+rainPredictRight)/(tempPredictRight+rainPredictRight+tempPredictWrong+rainPredictWrong))*1000;
	    var tempChance = (tempPredictRight/(tempPredictRight+tempPredictWrong))*1000;
	    var rainChance = (rainPredictRight/(rainPredictRight+rainPredictWrong))*1000;
	    
	    var totalPredictLabel = "전체 예측 ["+(tempPredictRight+rainPredictRight)+"/"+(tempPredictRight+rainPredictRight+tempPredictWrong+rainPredictWrong)+"]";
	    var tempPredictLabel = "기온 예측 ["+(tempPredictRight)+"/"+(tempPredictRight+tempPredictWrong)+"]";
	    var rainPredictLabel = "강수 예측 ["+(rainPredictRight)+"/"+(rainPredictRight+rainPredictWrong)+"]";
	    
	    new Chart(ctx, {
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
	</script>

</body>
</html>