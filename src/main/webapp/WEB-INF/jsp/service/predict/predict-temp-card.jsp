<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
			<div id="tempCard" class="group-${predictValue.predictOrder } card m-3 py-3" data-eachupid="${predictValue.UPID }" data-region="${predictValue.predictRGID }" data-weathertype="${predictValue.weatherType }" data-opened="false" data-order="${predictValue.predictOrder }" data-createdat=${predictValue.createdAt }>
				<div class="mx-3">
					<c:choose>
						<c:when test="${predictValue.predictOrder == 1 || predict.predictOrder == 2}">
							<div class="f-content font-weight-bold"><span class="material-icons text-dark">thermostat</span>나의 지역 기온 예측하기</div>
						</c:when>
						<c:otherwise>
							<div class="f-content font-weight-bold"><span class="material-icons text-dark">thermostat</span>다른 지역 기온 예측하기</div>
						</c:otherwise>					
					</c:choose>
					<div class="f-content">내일 <span id="regionName" class="group-region-${predictValue.predictOrder }">${predictValue.regionName}</span>지역의 기온을 예측해 주세요!</div>
				</div>
				<div class="d-flex justify-content-around mx-2">
					<div class="mx-3">
						<label class="f-small font-weight-bold">내일 최저 기온 예측</label>
						<div class="input-group">
							<input id="predictedNum1" type="text" class="predictedNum1 form-control" id="" placeholder="${predictValue.predictedNum1 }">
							<div class="input-group-prepend">
								<div class="input-group-text">°C</div>
							</div>
						</div>
					</div>
					<div class="mx-3">
						<label class="f-small font-weight-bold">내일 최고 기온 예측</label>
						<div class="input-group">
							<input id="predictedNum2" type="text" class="predictedNum2 form-control" placeholder="${predictValue.predictedNum2 }">
							<div class="input-group-prepend">
								<div class="input-group-text">°C</div>
							</div>
						</div>
					</div>
				</div>
				<div class="mx-2 mt-4 d-flex align-items-center f-small justify-content-center text-white bg-success rounded">
					<div class="mr-1 font-weight-bold">Tip. 내일 <span class="group-region-${predictValue.predictOrder }">${predictValue.regionName}</span> 지역, 기상청 예보</div>
					<div class="mr-1">오전</div>
					<div id="shortTempAM" class="shortAM">${predictValue.shortForecastAMString }</div>
					<div class="mx-1">오후</div>
					<div id="shortRainPM" class="shortAM">${predictValue.shortForecastPMString }</div>
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
						<c:when test="${predictValue.createdAt == null}">
							<button id="predictSubmit" class="predictSubmitBtn btn btn-warning m-1" disabled>제출하기</button>
						</c:when>
						<c:otherwise>
							<button id="predictEditSubmit" class="predictEditSubmitBtn btn btn-primary m-1" disabled>수정하기</button>
						</c:otherwise>
					</c:choose>
				</div>
			</div>