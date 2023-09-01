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
	<link rel="stylesheet" href="/static/css/style.css" type="text/css">
</head>
<body>
	<div id="wrap">
	
		<header>
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
			<nav class="f-small">	
				<ul class="nav justify-content-between border-bottom">
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
		
		<section>
			<div style="height:5px"></div>
			
			<div id="myRegionTemp" class="card m-3 py-3">
				<div class="mx-3">
					<div class="f-content font-weight-bold">나의 지역 기온 예측하기</div>
					<div class="f-content">내일 서울 지역의 기온을 예측해 주세요!</div>
				</div>
				<div class="d-flex justify-content-around mx-2">
					<div class="mx-3">
						<label class="f-small font-weight-bold">내일 최고 기온 예측</label>
						<div class="input-group">
							<input type="text" class="form-control" id="" placeholder="36.5">
							<div class="input-group-prepend">
								<div class="input-group-text">°C</div>
							</div>
						</div>
					</div>
					<div class="mx-3">
						<label class="f-small font-weight-bold">내일 최저 기온 예측</label>
						<div class="input-group">
							<input type="text" class="form-control" id="" placeholder="36.5">
							<div class="input-group-prepend">
								<div class="input-group-text">°C</div>
							</div>
						</div>
					</div>
				</div>
				<div class="mx-2 mt-4 d-flex align-items-center f-small justify-content-center text-white bg-success rounded">
					<div class="mr-1 font-weight-bold">Tip. 내일 서울 지역, 기상청 예보</div>
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
					<button class="btn btn-warning m-1">수정하기</button>
					<button class="btn btn-primary m-1">제출하기</button>
				</div>
			</div>
			<div id="myRegionRain" class="card m-3 py-3">
				<div class="mx-3">
					<div class="f-content font-weight-bold">나의 지역 기온 예측하기</div>
					<div class="f-content">내일 서울 지역의 강수량을 예측해 주세요!</div>
				</div>
				<div class="mx-2">
					<div class="mx-3">
						<label class="f-small font-weight-bold">내일 강수 예측</label>
						<select class="form-control">
							<option value="noRain">내일은 비 안와요!</option>
							<option value="yesRain">내일 비 와요!</option>
						</select>
					</div>
					<label class="f-small font-weight-bold mt-3 ml-3">강수량 예측</label>
					<div class="mx-3">
						<div class="input-group">
							<input type="text" class="form-control" id="" placeholder="36.5">
							<div class="input-group-prepend">
								<div class="input-group-text">mm</div>
							</div>
						</div>
					</div>
				</div>
				<div class="mx-2 mt-4 d-flex align-items-center f-small justify-content-center text-white bg-success rounded">
					<div class="mr-1 font-weight-bold">Tip. 내일 서울 지역, 기상청 예보</div>
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
					<button class="btn btn-warning m-1">수정하기</button>
					<button class="btn btn-primary m-1">제출하기</button>
				</div>
			</div>
			
			<div class="card m-3 py-3">
				<div class="my-3">
					<div class="my-2 text-center">다른 지역 날씨도 예측하기</div>
					<div class="my-2 text-center"><i class="bi-plus-circle"></i></div>
				</div>
			</div>
			
			<div style="height:5px"></div>
		</section>
	
<%-- 모달 스크립트 시작 --%>
		<!-- Button trigger modal -->
		<button id="loginFailModal" type="button" class="btn btn-primary d-none" data-toggle="modal" data-target="#loginDenied">
		  Launch demo modal
		</button>
		
		<!-- Modal -->
		<div class="modal fade px-5" id="loginDenied" tabindex="-1" aria-labelledby="loginDenied" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered">
		    <div class="modal-content">
		      <div class="modal-body text-center f-content">
		        <div><b>잘못된 로그인 정보입니다.</b></div>
		        <div>이메일주소 혹은 비밀번호를 확인해 주세요.</div>
		      </div>
		      <div class="modal-footer d-flex justify-content-center">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">확인</button>
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
			
			$("#loginBtn").on("click",function(){
				
				var emailInput = $("#emailInput").val();
				var passwordInput = $("#passwordInput").val();
				
				if(emailInput==""){
					alert("이메일 주소를 입력해 주세요.");
					return;
				}
				
				if(passwordInput==""){
					alert("비밀번호를 입력해 주세요.");
					return;
				}
				
				$.ajax({
					url:"/rest/login/submit"
					,type:"post"
					,data:{
						"email":emailInput
						,"password":passwordInput
					}
					,success:function(data){
						if(data.result=="success"){
							location.href="/main/predict";
						} else{
							$("#loginFailModal").click();
						}
					}
					,error:function(){
						alert("로그인 중 오류가 발생했어요.");
					}
				})
				
			})
			
			
			$("#regBtn").on("click",function(){
				location.href="/registration"
			});
			
			$("#forgortPWBtn").on("click",function(){
				location.href="/main/login/forgotPassword"
			});
		});
	</script>

</body>
</html>