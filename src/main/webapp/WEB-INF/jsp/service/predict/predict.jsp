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
		<jsp:include page="/WEB-INF/jsp/global/header.jsp"/>
		
		<section id="content" class="container border-top">
			<div style="height:5px"></div>
			<div class="d-flex container ml-2 align-items-center">
				<div class="bg-primary text-white f-small px-3 rounded">안내</div>
				<div class=" f-small ml-3 d-flex align-items-center font-weight-bold">예측한 날씨는 2일 후, 나의 정확도에서 확인할 수 있어요!</div>
			</div>
			
			<div id="predictCards">
			
			<c:forEach var="predict" items="${predictedMap}">
			<c:set var="p" value="${predict.value}" scope="request" />
	
				<div id="predictCard-${p.predictOrder }" class="grouper card m-3 py-3" data-eachupid="${p.UPID }" data-region="${p.predictRGID }" data-weathertype="${p.weatherType }" data-order="${p.predictOrder }" data-createdat=${p.createdAt }>
					<div class="mx-3">
						<c:choose>
							<c:when test="${p.predictOrder == 1}">
								<div class="f-content font-weight-bold"><span class="material-icons text-dark">thermostat</span>나의 지역 기온 예측하기</div>
								<div class="f-content">내일 <span id="regionName-${p.predictOrder}">${p.regionName}</span>지역의 기온을 예측해 주세요!</div>
							</c:when>
							<c:when test="${p.predictOrder == 2}">
								<div class="f-content font-weight-bold"><span class="material-icons text-dark">water_drop</span>나의 지역 강수 예측하기</div>
								<div class="f-content">내일 <span id="regionName-${p.predictOrder}">${p.regionName}</span>지역의 강수를 예측해 주세요!</div>
							</c:when>
							<c:when test="${p.predictOrder == 3 || p.predictOrder == 5}">
								<div class="f-content font-weight-bold"><span class="material-icons text-dark">thermostat</span>나의 지역 기온 예측하기</div>
								<div class="f-content">내일 <span id="regionName-${p.predictOrder}">${p.regionName}</span>지역의 기온을 예측해 주세요!</div>
							</c:when>
							<c:when test="${p.predictOrder == 4 || p.predictOrder == 6}">
								<div class="f-content font-weight-bold"><span class="material-icons text-dark">water_drop</span>나의 지역 강수 예측하기</div>
								<div class="f-content">내일 <span id="regionName-${p.predictOrder}">${p.regionName}</span>지역의 강수를 예측해 주세요!</div>
							</c:when>
						</c:choose>
					</div>
					
					<c:choose>
					<c:when test="${p.weatherType == 'temp'}">
					<div class="d-flex justify-content-around mx-2">
						<div class="mx-3">
							<label class="f-small font-weight-bold">내일 최저 기온 예측</label>
							<div class="input-group">
								<input id="${p.predictOrder}input1" type="number" class="predictedNum1 form-control" placeholder="${p.predictedNum1 }">
								<div class="input-group-prepend">
									<div class="input-group-text">°C</div>
								</div>
							</div>
						</div>
						<div class="mx-3">
							<label class="f-small font-weight-bold">내일 최고 기온 예측</label>
							<div class="input-group">
								<input id="${p.predictOrder}input2" type="number" class="predictedNum2 form-control" placeholder="${p.predictedNum2 }">
								<div class="input-group-prepend">
									<div class="input-group-text">°C</div>
								</div>
							</div>
						</div>
					</div>
					<div class="mx-2 mt-4 d-flex align-items-center f-small justify-content-center text-white bg-success rounded">
						<div class="mr-1 font-weight-bold">Tip. 내일 <span>${p.regionName}</span> 지역, 기상청 예보</div>
						<div class="mr-1">오전</div>
						<div id="sfcAM-${p.predictOrder }" class="shortAM">${p.shortForecastAMString }</div>
						<div class="mx-1">오후</div>
						<div id="sfcPM-${p.predictOrder }" class="shortAM">${p.shortForecastPMString }</div>
					</div>
					<c:choose>
					<c:when test="${empty p.highUserPredict1}">
					</c:when>
					<c:otherwise>
					<div class="mx-2 mt-3 d-flex align-items-center f-small justify-content-center text-white bg-success rounded">
						<div class="mr-1 font-weight-bold">Tip. 높은 적중률 사용자의 예측</div>
						<div class="mr-1">최고</div>
						<div>${p.highUserPredict1 }</div>
						<div class="mx-1">최저</div>
						<div>${p.highUserPredict2 }</div>
					</div>
					</c:otherwise>
					</c:choose>
					</c:when>
					
				
					<c:when test="${p.weatherType == 'rain'}">
					<div class="mx-2">
						<div class="mx-3">
							<label class="f-small font-weight-bold">내일 강수 예측</label>
							<select class="rainSelect form-control">
								<c:choose>
									<c:when test="${p.createdAt != null && p.predictedNum1 <= 0.0}">
										<option value="yesRain" >내일 비 와요!</option>
										<option value="noRain" selected>내일은 비 안와요!</option>
									</c:when>
									<c:when test="${p.createdAt != null && p.predictedNum1 > 0.0}">
										<option value="noRain" >내일은 비 안와요!</option>
										<option value="yesRain" selected>내일 비 와요!</option>
									</c:when>
									<c:otherwise>
										<option value="noRain">내일은 비 안와요!</option>
										<option value="yesRain" selected>내일 비 와요!</option>
									</c:otherwise>
								</c:choose>
							</select>
						</div>
						<div class="rainPredictInput">
							<label class="f-small font-weight-bold mt-3 ml-3">강수량 예측</label>
							<div class="mx-3">
								<div class="input-group">
									<input id="predictedNum1" type="number" class="predictedNum1 form-control" placeholder="${p.predictedNum1 }">
									<div class="input-group-prepend">
										<div class="input-group-text">mm</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="mx-2 mt-4 d-flex align-items-center f-small justify-content-center text-white bg-success rounded">
						<div class="mr-1 font-weight-bold">Tip. 내일 <span id="regionName" class="group-region-${p.predictOrder-1 }">${p.regionName}</span> 지역, 기상청 예보</div>
						<div class="mr-1">오전 강수확률</div>
						<div id="sfcAM-${p.predictOrder }" class="shortAM">${p.shortForecastAMString }</div>
						<div class="mx-1">오후 강수확률</div>
						<div id="sfcPM-${p.predictOrder }" class="shortPM">${p.shortForecastPMString }</div>
					</div>
					<c:choose>
					<c:when test="${empty p.highUserPredict1}">
					</c:when>
					<c:otherwise>
					<div class="mx-2 mt-3 d-flex align-items-center f-small justify-content-center text-white bg-success rounded">
						<div class="mr-1 font-weight-bold">Tip. 높은 적중률 사용자의 예측</div>
						<div class="mr-1">강수량</div>
						<div>${p.highUserPredict1 }</div>
					</div>
					</c:otherwise>
					</c:choose>
					</c:when>
					</c:choose>
					
					<div class="d-flex flex-column mx-5 my-3">
						<c:choose>
							<c:when test="${p.createdAt == null}">
								<button id="${p.predictOrder }submitBtn" class="predictBtn predictSubmitBtn btn btn-warning m-1" disabled>제출하기</button>
							</c:when>
							<c:otherwise>
								<button id="${p.predictOrder }editBtn"  class="predictBtn predictEditSubmitBtn btn btn-primary m-1" disabled>수정하기</button>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</c:forEach>
					
			</div>
		
			<div id="openNewCard" class="m-3 py-3 bg-dark rounded text-white">
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
			
			$("#predictCard-3").addClass("d-none");
			$("#predictCard-4").addClass("d-none");
			$("#predictCard-5").addClass("d-none");
			$("#predictCard-6").addClass("d-none");
			
			ifCardLimited();
			
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
			
			// 지역 선택 시 새로운카드 정보 api 호출 및 성공 시 정보 세팅
			$("#regionSelectBtn").on("click",function(){
				var selectedRGID = $("#whichRegion").val();
				var orderNumber = ifCardLimited();
				$.ajax({
					url:"/rest/predict/addCard"
					,type:"get"
					,data:{
						"RGID":selectedRGID
						,"order":orderNumber}
					,success:function(data){
						var regionName = data[orderNumber].regionName;
						var rgid = data[orderNumber].predictRGID;
						var sfcTempAM = data[orderNumber].shortForecastAMString;
						var sfcTempPM = data[orderNumber].shortForecastPMString;
						var sfcRainAM = data[orderNumber+1].shortForecastAMString;
						var sfcRainPM = data[orderNumber+1].shortForecastPMString;
						$("#predictCard-"+orderNumber).removeClass("d-none");
						$("#predictCard-"+(orderNumber+1)).removeClass("d-none");
						$("#regionName-"+(orderNumber)).text(regionName);
						$("#regionName-"+(orderNumber+1)).text(regionName);
						$("#sfcAM-"+(orderNumber)).text(sfcTempAM);
						$("#sfcPM-"+(orderNumber)).text(sfcTempPM);
						$("#sfcAM-"+(orderNumber+1)).text(sfcRainAM);
						$("#sfcPM-"+(orderNumber+1)).text(sfcRainPM);
						$("#predictCard-"+(orderNumber)).attr("data-region",rgid);
						$("#predictCard-"+(orderNumber+1)).attr("data-region",rgid);
						$("#predictCard-"+(orderNumber)).attr("data-createdat","new");
						$("#predictCard-"+(orderNumber+1)).attr("data-createdat","new");
						ifCardLimited();

					}
				})
			});
			

 	 		$(".rainSelect").each(function(){
				var rainSelect = $(this).val();
				if(rainSelect=="yesRain"){
					$(this).closest(".grouper").find(".rainPredictInput").show();
					
				} else if(rainSelect == "noRain"){
					$(this).closest(".grouper").find(".rainPredictInput").hide();
					$(this).closest(".grouper").find(".predictedNum1").val('0.00');
				}
			}); 
			
			
			// 비옴 선택 시 강수량 예측 필드 show, 안옴 선택시 hide
 			$(".rainSelect").on("change",function(){
				var rainSelect = $(this).val();
				if(rainSelect=="yesRain"){
					$(this).closest(".grouper").find(".rainPredictInput").show();
					
				} else if(rainSelect == "noRain"){
					$(this).closest(".grouper").find(".rainPredictInput").hide();
					$(this).closest(".grouper").find(".predictedNum1").val('0.00');
				}
			});
			
	        // predictedNum 영역 숫자 입력 시 제출하기/수정하기 버튼 활성화 및 비활성화
	        $(".predictedNum1,.predictedNum2, .rainSelect").on("change",function(){
	        	
	            var predictedNum1 = $(this).closest(".grouper").find(".predictedNum1").val();
	            var predictedNum2 = $(this).closest(".grouper").find(".predictedNum2").val();
	            var num1PlaceHolder = $(this).closest(".grouper").find(".predictedNum1").prop("placeholder");
	            var num2PlaceHolder = $(this).closest(".grouper").find(".predictedNum2").prop("placeholder");
	            var createdAt = $(this).closest(".grouper").data("createdat");
	            
	            var rainSelector = $(this).closest(".grouper").find(".rainSelect").val();
	            var weatherType = $(this).closest(".card").data("weathertype");

	            if(weatherType=="temp"){
	                if(predictedNum1!="" && predictedNum2!=""){
	                    $(this).closest(".grouper").find(".predictBtn").prop("disabled",false);
	                } else{
	                    $(this).closest(".grouper").find(".predictBtn").prop("disabled",true);
	                }
	                
	            	if(createdAt != "" && predictedNum1 != "" && predictedNum2 == ""){
	            		$(this).closest(".grouper").find(".predictedNum1").prop("placeholder",predictedNum1);
	            		$(this).closest(".grouper").find(".predictedNum2").prop("placeholder",predictedNum2);
	            		$(this).closest(".grouper").find(".predictedNum2").val(num2PlaceHolder);
	                    $(this).closest(".grouper").find(".predictEditSubmitBtn").prop("disabled",false);
	            	} 
	            	if(createdAt != "" && predictedNum2 != "" && predictedNum1 == ""){
	            		$(this).closest(".grouper").find(".predictedNum1").prop("placeholder",predictedNum1);
	            		$(this).closest(".grouper").find(".predictedNum2").prop("placeholder",predictedNum2);
	            		$(this).closest(".grouper").find(".predictedNum1").val(num1PlaceHolder);
	                    $(this).closest(".grouper").find(".predictEditSubmitBtn").prop("disabled",false);
	            	}
	            };
	            
	            if(weatherType=="rain"){
	                if(predictedNum1!=''){
	                    $(this).closest(".grouper").find(".predictBtn").prop("disabled",false);
	                } else{
	                    $(this).closest(".grouper").find(".predictBtn").prop("disabled",true);
	                }
	            };

	            
	        //응답 제출
            $(".predictSubmitBtn").off("click").on("click",function(){
            	
                var weatherType = $(this).closest(".grouper").data("weathertype");
                var predictedNum1 = $(this).closest(".grouper").find(".predictedNum1").val();
                var predictedNum2 = $(this).closest(".grouper").find(".predictedNum2").val();
                var predictOrder = $(this).closest(".grouper").data("order");
                var predictRGID = $(this).closest(".grouper").data("region");
                
                if(predictedNum1 > predictedNum2){
                	var temp = predictedNum1;
                	predictedNum1 = predictedNum2;
                	predictedNum2 = temp;
                }
                
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
                            location.reload();
                        } else{
                            alert("날씨 예측 정보를 다시 확인해 주세요");
                        }
                    }
                    ,error:function(){
                        alert("날씨 예측 업로드 중에 오류가 발생했어요.");
                    }
                })
            })
			
			 $(".predictEditSubmitBtn").off("click").on("click",function(){
				var predictUPID = $(this).closest(".card").data("eachupid");
				var predictedNum1 = $(this).closest(".card").find(".predictedNum1").val();
				var predictedNum2 = $(this).closest(".card").find(".predictedNum2").val();

                if(predictedNum1 > predictedNum2){
                	var temp = predictedNum1;
                	predictedNum1 = predictedNum2;
                	predictedNum2 = temp;
                }
				
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
		
		// group-1, 3, 5의 갯수를 기준으로, 3이 0개면 3을 리턴해서 addCard api 호출 시 order3,4 카드를 받아올 수 있도록.
		// 또한 각 그룹별로 모두 1개 이상 카드가 열려 있으면 카드 추가 버튼을 hide 함.

		function ifCardLimited(){
			
			var thirdCard = $("#predictCard-3").data("createdat");
			var fourthCard = $("#predictCard-4").data("createdat");
			var fifthCard = $("#predictCard-5").data("createdat");
			var sixthCard = $("#predictCard-6").data("createdat");
			if(thirdCard != "" || fourthCard!=""){
				$("#predictCard-3").removeClass("d-none");
				$("#predictCard-4").removeClass("d-none");
			};
			if(fifthCard != "" || sixthCard!=""){
				$("#predictCard-5").removeClass("d-none");
				$("#predictCard-6").removeClass("d-none");
			};

			var p1 = $("#predictCard-1").hasClass("d-none");
			var p2 = $("#predictCard-2").hasClass("d-none");
			var p3 = $("#predictCard-3").hasClass("d-none");
			var p4 = $("#predictCard-4").hasClass("d-none");
			var p5 = $("#predictCard-5").hasClass("d-none");
			var p6 = $("#predictCard-6").hasClass("d-none");
			
			if(!p3 && !p5){
				$("#newCardLimit").show();
				$("#openNewCard").hide();
			}
			if(p3 && p5){
				return 3;
			}
			if(p3 && !p5){
				return 3;
			}
			if(!p3 && p5){
				return 5;
			}
		};
		    
	});
	</script>

</body>
</html>