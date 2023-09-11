<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
	<title>나도캐스터 - 캐스터 순위</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="/static/css/style.css" type="text/css">
</head>
<body>
	<div id="wrap">
		<jsp:include page="/WEB-INF/jsp/global/header.jsp"/>
		
		<section id="content" class="container border-top">
			<div style="height:5px"></div>
			
			<div class="card">
				<div class="f-content m-3">지역별 순위</div>
				<div class="bg-secondary m-3"> 지도 영역</div>
			</div>
			<div>
				<div class="d-flex f-content m-3 bg-dark text-center text-white" style="border-radius:50px;">
					<div class="col-6 my-3" style="border-right:solid; text-decoration:underline;">전국 순위</div>
					<div class="col-6 my-3 text-muted">내 지역 순위</div>
				</div>
			</div>
			<div class="card">
				<table class="f-content text-center m-3">
					<tr>
						<td class="font-weight-bold">나의 순위</td>
						<td>200위</td>
						<td class="font-weight-bold">정확 예측</td>
						<td>2회</td>
					</tr>
					<tr class="pt-2">
						<td class="font-weight-bold mt-2">예측 획수</td>
						<td>10회</td>
						<td class="font-weight-bold">정확도</td>
						<td>20%</td>
					</tr>
				</table>
			</div>
			
			<div class="card">
				<div class="f-content m-3">전국 캐스터 순위</div>
				<table class="table f-small text-center">
					<thead>
						<tr>
							<th>순위</th>
							<th>캐스터 이름</th>
							<th>지역</th>
							<th>예측횟수</th>
							<th>정확예측</th>
							<th>정확도</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="rank" items="${rankList }" varStatus="status">
						<tr>
							<td>${status.count}위</td>
							<td>${rank.nickname }</td>
							<td>${rank.regionName }</td>
							<td>${rank.sumCount }회</td>
							<td>${rank.correct }회</td>
							<td>${rank.accuracyString }</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			
			
			
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
	<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@0.7.0"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	

	<script>
		$(document).ready(function(){
			
			
		});

	</script>
	

</body>
</html>