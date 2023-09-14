<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
			<div class="card changable">
				<c:choose>
				<c:when test="${not empty rankList.myInfo}">
				<table class="f-content text-center m-3">
					<tr>
						<td class="font-weight-bold">나의 순위</td>
						<td>${rankList.myInfo[0].rank }위</td>
						<td class="font-weight-bold">정확 예측</td>
						<td>${rankList.myInfo[0].correct }회</td>
					</tr>
					<tr class="pt-2">
						<td class="font-weight-bold mt-2">예측 획수</td>
						<td>${rankList.myInfo[0].sumCount }회</td>
						<td class="font-weight-bold">정확도</td>
						<td>${rankList.myInfo[0].accuracyString }</td>
					</tr>
				</table>
				</c:when>
				</c:choose>
			</div>
			
			<div class="card changable mt-2">
			<c:choose>
			<c:when test="${rankList.regionType[0].regionName == null}">
				<div class="f-content m-3"><span class="font-weight-bold">전국</span> 캐스터 순위</div>
			</c:when>
			<c:otherwise>
				<div class="f-content m-3"><span class="font-weight-bold">${rankList.regionType[0].regionName}</span> 지역 캐스터 순위</div>
			</c:otherwise>
			</c:choose>
			
			<c:choose>
			<c:when test="${empty rankList.rankInfo}">
				<div class="f-content text-center p-3 my-5" onclick="location.href='/main/predict';">
					<div>아직 집계 가능한 예측이 없어요!</div>
					<div class="font-weight-bold m-1">지금 바로 내일 날씨를 예측해 볼까요?</div>
					<div class="m-3 p-3 bg-dark text-center text-white f-content" style="border-radius:50px;">내일 날씨 예측하기</div>
				</div>
			</c:when>
			<c:otherwise>
				<table class="table f-small text-center">
					<thead>
						<tr>
							<th>순위</th>
							<th>캐스터 이름</th>
							<th>예측횟수</th>
							<th>정확예측</th>
							<th>정확도</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="rank" items="${rankList.rankInfo }" varStatus="status">
						<tr>
							<td>${rank.rank}위</td>
							<c:choose>
							<c:when test="${rank.UID eq UID }"><td><span class="font-weight-bold">${rank.nickname }</span> (나)</td></c:when>
							<c:otherwise><td>${rank.nickname }</td></c:otherwise>
							</c:choose>							<td>${rank.sumCount }회</td>
							<td>${rank.correct }회</td>
							<td>${rank.accuracyString }</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
			</c:choose>
			</div>