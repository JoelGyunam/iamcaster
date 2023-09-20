<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header class="container">
			<nav id="ordinaryHeader">
				<div class="d-flex justify-content-around align-items-center">
					<div>
						<button id="hamburgerBtn" class="btn bg-white"><i class="bi-justify f-title"></i></button>
					</div>
					<div class="text-center mt-2" onclick="location.href='/main/predict'">
						<div class="f-small">모두가 기상국장이 되는 곳</div>
						<div class="f-content font-weight-bold">나도 캐스터</div>
					</div>
					<div>
						<div class="bg-white"><i class="bi-justify text-white"></i></div>
					</div>
				</div>
				<ul class="nav f-small justify-content-between border-top">
					<li class="nav-item">
						<a id="navPredictBtn" class="nav-link text-dark" href="/main/predict">날씨 예측하기</a>
					</li>
					<li class="nav-item">
						<a id="navMyscoreBtn" class="nav-link text-dark" href="/main/myscore">나의 정확도</a>
					</li>
					<li class="nav-item">
						<a id="navUserRankBtn" class="nav-link text-dark" href="/main/userRank">캐스터 순위</a>
					</li>
					<li class="nav-item">
						<a id="navShortForcastBtn" class="nav-link text-dark" href="/main/shortforecast">단기 예보</a>
					</li>
				</ul>
			</nav>
		</header>
		
	<script src="https://code.jquery.com/jquery-3.6.4.min.js" integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
  	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
   	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

	
	
	<script>
		$(document).ready(function(){
			
			var thisPath = $(location).attr("pathname");
			
			if(thisPath.startsWith("/main/predict")){
				$("#navPredictBtn").addClass("font-weight-bold");
			} else if(thisPath.startsWith("/main/myscore")){
				$("#navMyscoreBtn").addClass("font-weight-bold");
			} else if(thisPath.startsWith("/main/userRank")){
				$("#navUserRankBtn").addClass("font-weight-bold");
			} else if(thisPath.startsWith("/main/shortforecast")){
				$("#navShortForcastBtn").addClass("font-weight-bold");
			};
			
			$("#hamburgerBtn").on("click",function(){
				location.href="/main/menu";
			})
		
		
		});
	
	</script>