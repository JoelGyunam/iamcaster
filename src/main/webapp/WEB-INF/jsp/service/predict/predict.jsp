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
			<%-- 
					<jsp:include page="/WEB-INF/jsp/service/predict/predict-card.jsp"/>
			 --%>		
					<c:import url="/WEB-INF/jsp/service/predict/predict-card.jsp" ></c:import>
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
			
			// 지역 선택 시 새로운카드 append 하는 api 호출 및 성공 시 ifCardLimited 함수 호출 
			$("#regionSelectBtn").on("click",function(){
				var selectedRGID = $("#whichRegion").val();
				$.ajax({
					url:"/main/predict/addCard"
					,type:"get"
					,data:{
						"RGID":selectedRGID
						,"order":ifCardLimited()}
					,success:function(data){
						$("#predictCards").append(data);
						ifCardLimited();
					}
				})
			})
			
			// 비옴 선택 시 강수량 예측 필드 show, 안옴 선택시 hide
			$(".rainSelect").on("change",function(){
				var rainSelect = $(this).val();
				console.log(rainSelect);
				if(rainSelect=="yesRain"){
					$(this).closest(".card").find(".rainPredictInput").show();
					
				} else if(rainSelect == "noRain"){
					$(this).closest(".card").find(".rainPredictInput").hide();
					$(this).closest(".card").find("#predictedNum1").val('0.0');
	                $(this).closest(".card").find("#predictSubmit").prop("disabled",false);
	                $(this).closest(".card").find("#predictEditSubmit").prop("disabled",false);
				}
			});
			
	        // predictedNum 영역 숫자 입력 시 제출하기/수정하기 버튼 활성화 및 비활성화
	        $(".predictedNum1,.predictedNum2").on("change",function(){
	            var predictedNum1 = $(this).closest(".card").find("#predictedNum1").val();
	            var predictedNum2 = $(this).closest(".card").find("#predictedNum2").val();
	            var weatherType = $(this).closest(".card").data("weathertype");
				console.log(predictedNum1);
				console.log(predictedNum2);
	            
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

	            $("#predictSubmitBtn").on("click",function(){
	                
	             	alert("asdf");
	            	
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
			
			 $(".predictEditSubmitBtn").on("click",function(){
				var predictUPID = $(this).closest(".card").data("eachupid");
				var predictedNum1 = $(this).closest(".card").find("#predictedNum1").val();
				var predictedNum2 = $(this).closest(".card").find("#predictedNum2").val();
				console.log(predictedNum1);
				console.log(predictedNum2);
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
			var group1openedNumber = $(".group-1").length;
			var group3openedNumber = $(".group-3").length;
			var group5openedNumber = $(".group-5").length;
			if(group1openedNumber != 0 && group5openedNumber != 0 && group5openedNumber != 0 ){
				$("#newCardLimit").show();
				$("#openNewCard").hide();
			}
			if(group3openedNumber == 0){
				return 3;
			}
			if(group5openedNumber == 0){
				return 5;
			}
		};
		
		

		
		    
		});

	</script>

</body>
</html>