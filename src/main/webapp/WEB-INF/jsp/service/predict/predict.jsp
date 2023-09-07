<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
	<title>나도캐스터 - 날씨 예측하기</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="/static/css/style.css" type="text/css">
</head>
<body>
	<div id="wrap">
		<header class="container">
			<nav id="ordinaryHeader">
				<div class="d-flex justify-content-around align-items-center">
					<div>
						<button class="btn bg-white"><i class="bi-justify f-title"></i></button>
					</div>
					<div class="text-center mt-2" onclick="location.href='/main/predict'">
						<div class="f-small">모두가 기상국장이 되는 곳</div>
						<div class="f-content font-weight-bold">나도 캐스터</div>
					</div>
					<div>
						<div class="bg-white"><i class="bi-justify text-white"></i></div>
					</div>
				</div>
				<ul class="nav f-small justify-content-between border-bottom border-top">
					<li class="nav-item">
						<a class="nav-link text-dark font-weight-bold" href="/main/predict">날씨 예측하기</a>
					</li>
					<li class="nav-item">
						<a class="nav-link text-dark font-weight-bold" href="/main/myscore">나의 정확도</a>
					</li>
					<li class="nav-item">
						<a class="nav-link text-dark font-weight-bold" href="/main/userRank">캐스터 순위</a>
					</li>
					<li class="nav-item">
						<a class="nav-link text-dark font-weight-bold" href="/main/shortforecast">단기 예보</a>
					</li>
				</ul>
			</nav>
		</header>
		
		<section id="content" class="container">
			<div style="height:5px"></div>
			
	<c:forEach var="predict" items="${predictListMap }">
		<c:choose>
		<c:when test="${predict.weatherType == 'temp' }">
			<div id="tempCard" class="group-${predict.predictOrder } card m-3 py-3" data-eachupid="${predict.UPID }" data-region="${predict.predictRGID }" data-weathertype="${predict.weatherType }" data-opened="false" data-order="${predict.predictOrder }" data-createdat=${predict.createdAt }>
				<div class="mx-3">
					<c:choose>
						<c:when test="${predict.predictOrder == 1 || predict.predictOrder == 2}">
							<div class="f-content font-weight-bold"><span class="material-icons text-dark">thermostat</span>나의 지역 기온 예측하기</div>
						</c:when>
						<c:otherwise>
							<div class="f-content font-weight-bold"><span class="material-icons text-dark">thermostat</span>다른 지역 기온 예측하기</div>
						</c:otherwise>					
					</c:choose>
					<div class="f-content">내일 <span id="regionName" class="group-region-${predict.predictOrder }">${predict.regionName}</span>지역의 기온을 예측해 주세요!</div>
				</div>
				<div class="d-flex justify-content-around mx-2">
					<div class="mx-3">
						<label class="f-small font-weight-bold">내일 최저 기온 예측</label>
						<div class="input-group">
							<input id="predictedNum1" type="text" class="predictedNum1 form-control" id="" placeholder="${predict.predictedNum1 }">
							<div class="input-group-prepend">
								<div class="input-group-text">°C</div>
							</div>
						</div>
					</div>
					<div class="mx-3">
						<label class="f-small font-weight-bold">내일 최고 기온 예측</label>
						<div class="input-group">
							<input id="predictedNum2" type="text" class="predictedNum2 form-control" placeholder="${predict.predictedNum2 }">
							<div class="input-group-prepend">
								<div class="input-group-text">°C</div>
							</div>
						</div>
					</div>
				</div>
				<div class="mx-2 mt-4 d-flex align-items-center f-small justify-content-center text-white bg-success rounded">
					<div class="mr-1 font-weight-bold">Tip. 내일 <span class="group-region-${predict.predictOrder }">${predict.regionName}</span> 지역, 기상청 예보</div>
					<div class="mr-1">최고 기온</div>
					<div>36.5</div>
					<div>°C</div>
					<div class="mx-1">최저 기온</div>
					<div>36.5</div>
					<div>°C</div>
				</div>
				<div class="mx-2 mt-3 d-flex align-items-center f-small justify-content-center text-white bg-success rounded">
					<div class="mr-1 font-weight-bold">Tip. 높은 적중률 사용자의 예측</div>
					<div class="mr-1">최고</div>
					<div>36.5</div>
					<div>°C</div>
					<div class="mx-1">최저</div>
					<div>36.5</div>
					<div>°C</div>
				</div>
				
				<div class="d-flex flex-column mx-5 my-3">
					<c:choose>
						<c:when test="${predict.createdAt == null}">
							<button id="predictSubmit" class="predictSubmitBtn btn btn-warning m-1" disabled>제출하기</button>
						</c:when>
						<c:otherwise>
							<button id="predictEditSubmit" class="predictEditSubmitBtn btn btn-primary m-1" disabled>수정하기</button>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:when>
		<c:when test="${predict.weatherType == 'rain'}" >
			<div id="rainCard" class="group-${predict.predictOrder-1 } card m-3 py-3" data-eachupid="${predict.UPID }" data-region="${predict.predictRGID }" data-weathertype="${predict.weatherType }" data-predict1="${predict.predictedNum1 }" data-opened="false" data-order="${predict.predictOrder }" data-createdat=${predict.createdAt }>
				<div class="mx-3">
				<c:choose>
					<c:when test="${predict.predictOrder == 1 || predict.predictOrder == 2}">
						<div class="f-content font-weight-bold"><span class="material-icons text-dark">water_drop</span>나의 지역 강수 예측하기</div>
					</c:when>
					<c:otherwise>
						<div class="f-content font-weight-bold"><span class="material-icons text-dark">water_drop</span>다른 지역 강수 예측하기</div>
					</c:otherwise>					
				</c:choose>
					<div class="f-content">내일 <span id="regionName" class="group-region-${predict.predictOrder-1 }">${predict.regionName}</span> 지역의 강수량을 예측해 주세요!</div>
				</div>
				<div class="mx-2">
					<div class="mx-3">
						<label class="f-small font-weight-bold">내일 강수 예측</label>
						<select class="rainSelect form-control">
							<c:choose>
								<c:when test="${predict.createdAt != null && predict.predictedNum1 <= 0.0}">
									<option value="noRain" selected>내일은 비 안와요!</option>
									<option value="yesRain" >내일 비 와요!</option>
								</c:when>
								<c:when test="${predict.createdAt != null && predict.predictedNum1 > 0.0}">
									<option value="noRain" >내일은 비 안와요!</option>
									<option value="yesRain" selected>내일 비 와요!</option>
								</c:when>
								<c:otherwise>
									<option value="noRain">내일은 비 안와요!</option>
									<option value="yesRain">내일 비 와요!</option>
								</c:otherwise>
							</c:choose>
						</select>
					</div>
					<div class="rainPredictInput">
						<label class="f-small font-weight-bold mt-3 ml-3">강수량 예측</label>
						<div class="mx-3">
							<div class="input-group">
								<input id="predictedNum1" type="text" class="predictedNum1 form-control" placeholder="${predict.predictedNum1 }">
								<div class="input-group-prepend">
									<div class="input-group-text">mm</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="mx-2 mt-4 d-flex align-items-center f-small justify-content-center text-white bg-success rounded">
					<div class="mr-1 font-weight-bold">Tip. 내일 <span id="regionName" class="group-region-${predict.predictOrder-1 }">${predict.regionName}</span> 지역, 기상청 예보</div>
					<div class="mr-1">강수확률</div>
					<div>50</div>
					<div>%</div>
				</div>
				<div class="mx-2 mt-3 d-flex align-items-center f-small justify-content-center text-white bg-success rounded">
					<div class="mr-1 font-weight-bold">Tip. 높은 적중률 사용자의 예측</div>
					<div class="mr-1">강수확률</div>
					<div>36.5</div>
					<div>%</div>
				</div>
				<div class="d-flex flex-column mx-5 my-3">
					<c:choose>
						<c:when test="${predict.createdAt == null}">
							<button id="predictSubmit" class="predictSubmitBtn btn btn-warning m-1" disabled>제출하기</button>
						</c:when>
						<c:otherwise>
							<button id="predictEditSubmit" class="predictEditSubmitBtn btn btn-primary m-1" disabled>수정하기</button>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:when>
		</c:choose>
	</c:forEach>		
		
			<div id="openNewCard" class="m-3 py-3 bg-secondary rounded">
				<div class="my-3">
					<div class="my-2 text-center">다른 지역 날씨도 예측하기</div>
					<div class="my-2 text-center"><i class="bi-plus-circle"></i></div>
				</div>
			</div>
			<div id="newCardLimit" class="m-3 py-3 bg-secondary rounded">
				<div class="my-3">
					<div class="my-2 text-center f-content">최대 3개 지역의 날씨를 예측할 수 있어요!</div>
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
	
	<jsp:include page="/WEB-INF/jsp/global/footer.jsp"/>

	<script src="https://code.jquery.com/jquery-3.6.4.min.js" integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

	<script>
		$(document).ready(function(){
			var showGroup = 1;
			var hiddenCard = 0;
			$("#newCardLimit").hide();

			
			 // DOM 켜졌을 때 card div 에 model로 받은 data 기준으료, createdat 이 없거나, 내일 비 안옴 응답 시 강수량 예측 필드 hide
			$('.card').each(function(){
		        var eachCreatedAt = $(this).data("createdat");
				var eachPredict1 = $(this).data("predict1");
				if(eachCreatedAt == '' || (eachCreatedAt != '' && eachPredict1 == 0.0)){
				$(this).closest(".card").find(".rainPredictInput").hide();
				}
			});
			
			$("#openNewCard").on("click",function(){
				$("#openNewCardBtn").click();
			});
			
			//지역 선택 셀렉트 박스 선택 시 선택하기 버튼 활성화
			$("#whichRegion").on("change",function(){
				var selectedRegion = $("#whichRegion").val();
				if(selectedRegion == null){
					alert("지역을 선택해 주세요.");
					return;
				} else{
					$("#regionSelectBtn").prop("disabled",false)
				}
			});
			
			//지역 선택 버튼 클릭 시 두개 카드 보이기
			$("#regionSelectBtn").on("click",function(){
				var selectedRGID = $("#whichRegion").val();
				var selectedRegion = $("#whichRegion option[value='" + selectedRGID + "']").text();
				showGroup += 2;
				var groupClassSelector = ".group-"+showGroup;
				var groupRegionClassSelector = ".group-region-"+showGroup;
				$(groupRegionClassSelector).text(selectedRegion);
				$(groupClassSelector).attr("data-region",selectedRGID);
				$(groupClassSelector).show();
				console.log(showGroup);
				if(showGroup >= 5){
					$("#openNewCard").hide();
					$("#newCardLimit").show();
				};
			})
			
			// 기본 상단 2개 카드를 제외하고, model에서 받은 응답 데이터가 없는 카드의 경우 hide
			$('.card').each(function() {
		        var eachCreatedAt = $(this).data("createdat");
		        var eachOrder = $(this).data("order");

		        if (eachOrder > '2' && eachCreatedAt == '') {
		            $(this).hide(); 
		        }
		    });
			
			// hide 후 open 되어있는 카드의 수 및 showGroup 이 5면 지역추가 버튼 숨기기
			$(".card").each(function(){
				var ifHidden = $(this).css("display")=="none";
				if(ifHidden){
					hiddenCard ++;
				};
				showGroup = Math.floor((6 - hiddenCard)/2);
				console.log("hiddencard " + hiddenCard);
				console.log("showgroup " +showGroup);
				if(showGroup >= 5){
					$("#openNewCard").hide();
					$("#newCardLimit").show();
				}
			});

			
			// 비옴 선택 시 강수량 예측 필드 show, 안옴 선택시 hide
			$(".rainSelect").on("change",function(){
				var rainSelect = $(this).val();
				if(rainSelect=="yesRain"){
					$(this).closest(".card").find(".rainPredictInput").show();
				} else{
					$(this).closest(".card").find(".rainPredictInput").hide();
					$(this).closest(".card").find("#predictedNum1").val('0.0');
				}
			});
			
			
			// predictedNum 영역 숫자 입력 시 제출하기/수정하기 버튼 활성화 및 비활성화
			$((".predictedNum1,.predictedNum2")).on("keyup",function(){
				var predictedNum1 = $(this).closest(".card").find("#predictedNum1").val();
				var predictedNum2 = $(this).closest(".card").find("#predictedNum2").val();
				var weatherType = $(this).closest(".card").data("weathertype");
				
				if(weatherType=="temp"){
					if(predictedNum1!="" && predictedNum2!=""){
						$(this).closest(".card").find("#predictSubmit").prop("disabled",false);
						$(this).closest(".card").find("#predictEditSubmit").prop("disabled",false);
					} else{
						$(this).closest(".card").find("#predictSubmit").prop("disabled",true);
						$(this).closest(".card").find("#predictEditSubmit").prop("disabled",true);
					}
				};
				if(weatherType=="rain"){
					if(predictedNum1!=''){
						$(this).closest(".card").find("#predictSubmit").prop("disabled",false);
						$(this).closest(".card").find("#predictEditSubmit").prop("disabled",false);
					} else{
						$(this).closest(".card").find("#predictSubmit").prop("disabled",true);
						$(this).closest(".card").find("#predictEditSubmit").prop("disabled",true);
					}
				};
			})
			
			// 강수예측(비와요 안와요) 변화 시 제출하기/수정하기 버튼 활성화 및 비활성화
			$(".rainSelect").on("change",function(){
				var rainYesNo = $(this).val();
				var predictedNum1 = $(this).closest(".card").find("#predictedNum1").val();
				if(rainYesNo=="yesRain" && (predictedNum1=="" || predictedNum1<0)){
					$(this).closest(".card").find("#predictSubmit").prop("disabled",true);
					$(this).closest(".card").find("#predictEditSubmit").prop("disabled",true);
				} else{
					$(this).closest(".card").find("#predictSubmit").prop("disabled",false);
					$(this).closest(".card").find("#predictEditSubmit").prop("disabled",false);
				}
			});
			
			$(".predictSubmitBtn").on("click",function(){
				
				var weatherType = $(this).closest(".card").data("weathertype");
				var predictedNum1 = $(this).closest(".card").find("#predictedNum1").val();
				var predictedNum2 = $(this).closest(".card").find("#predictedNum2").val();
				var predictOrder = $(this).closest(".card").data("order");
				var predictRGID = $(this).closest(".card").data("region");
				
				$.ajax({
					url:"/rest/predict/submit"
					,type:"get"
					,data:{
						"weatherType":weatherType
						,"predictedNum1":predictedNum1
						,"predictedNum2":predictedNum2
						,"predictOrder":predictOrder
						,"predictRGID":predictRGID
					}
					,success:function(data){
						if(data.result=="success"){
							alert("2일 후에 결과를 확인해 주세요!");
						//	location.reload();
						} else{
							alert("날씨 예측 정보를 다시 확인해 주세요");
						}
					}
					,error:function(){
						alert("날씨 예측 업로드 중에 오류가 발생했어요.");
					}
				})
			})
			
			$(".predictEditSubmitBtn").on("click",function(){
				
				var predictUPID = $(this).closest(".card").data("eachupid");
				var predictedNum1 = $(this).closest(".card").find("#predictedNum1").val();
				var predictedNum2 = $(this).closest(".card").find("#predictedNum2").val();
				
				$.ajax({
					url:"/rest/predict/edit"
					,type:"get"
					,data:{
						"predictUPID":predictUPID
						,"predictedNum1":predictedNum1
						,"predictedNum2":predictedNum2
					}
					,success:function(data){
						if(data.result=="success"){
							alert("수정되었어요\n2일 후에 결과를 확인해 주세요!");
							location.reload();
						} else{
							alert("날씨 예측 정보를 다시 확인해 주세요");
						}
					}
					,error:function(){
						alert("날씨 예측 수정 중에 오류가 발생했어요.");
					}
				})
			})
			
			$.ajax({
				url:"/rest/observation/newList/refresh"
				,type:"get"
			})
			
		});
	</script>

</body>
</html>