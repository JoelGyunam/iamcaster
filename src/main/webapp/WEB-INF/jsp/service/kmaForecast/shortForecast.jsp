<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
	<title>나도캐스터 - 단기 예보</title>
	<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=fclyxujz52"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="/static/css/style.css" type="text/css">
</head>
<body>
	<div id="wrap">
		<jsp:include page="/WEB-INF/jsp/global/header.jsp"/>
		
		<section id="content" class="container border-top">
			<div style="height:5px"></div>

			<div class="d-flex container ml-2 align-items-center mt-2">
				<div class="bg-primary text-white f-small px-3 rounded">안내</div>
				<div class=" f-small ml-3 d-flex align-items-center font-weight-bold">기상청에서 제공받은 단기예보 정보입니다.</div>
			</div>

			
			<div class="card mt-3">
				<div class="d-flex align-items-center ">
					<div class="f-content m-3 col-6"><span id="selectedRegionName"class="font-weight-bold">${myRegionName }</span> 지역 단기예보</div>
					<select id="regionSelect" class="form-control text-center">
						<option selected disabled>다른 지역 날씨 확인하기</option>
						<c:forEach var="eachRegion" items="${regionList }">
						<option value="${eachRegion.RGID }">${eachRegion.regionName }</option>
						</c:forEach>
					</select>
				</div>
				
				<div id="reloadLine"></div>
				
				<div id="reloadTableHere">
					<jsp:include page="/WEB-INF/jsp/service/kmaForecast/reload-table.jsp"/>
				</div>

			</div>
			
			<div style="height:5px"></div>
		</section>

	</div>
	
	<jsp:include page="/WEB-INF/jsp/global/footer.jsp"/>

	<script src="https://code.jquery.com/jquery-3.6.4.min.js" integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

	<script>
		$(document).ready(function(){
			$("#regionSelect").on("change",function(){
				
				var RGID = $(this).val();
				var regionName = $("#regionSelect option:selected").text();
				$.ajax({
					url:"/main/shortforecast/byRegion"
					,type:"get"
					,data:{
						"RGID":RGID
					}
					,success:function(data){
						$("#forecastTable").remove();
						$("#reloadLine").append(data);
						$("#selectedRegionName").text(regionName);
					}
				})
			})
		});
	</script>

</body>
</html>