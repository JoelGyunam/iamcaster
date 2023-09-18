<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
			<div id="rainCard" class="group-${predictValue.predictOrder-1 } card m-3 py-3" data-eachupid="${predictValue.UPID }" data-region="${predictValue.predictRGID }" data-weathertype="${predict.weatherType }" data-predict1="${predict.predictedNum1 }" data-opened="false" data-order="${predict.predictOrder }" data-createdat=${predict.createdAt }>
				<div class="mx-3">
				<c:choose>
					<c:when test="${predictValue.predictOrder == 1 || predictValue.predictOrder == 2}">
						<div class="f-content font-weight-bold"><span class="material-icons text-dark">water_drop</span>나의 지역 강수 예측하기</div>
					</c:when>
					<c:otherwise>
						<div class="f-content font-weight-bold"><span class="material-icons text-dark">water_drop</span>다른 지역 강수 예측하기</div>
					</c:otherwise>					
				</c:choose>
					<div class="f-content">내일 <span id="regionName" class="group-region-${predictValue.predictOrder-1 }">${predictValue.regionName}</span> 지역의 강수량을 예측해 주세요!</div>
				</div>
				<div class="mx-2">
					<div class="mx-3">
						<label class="f-small font-weight-bold">내일 강수 예측</label>
						<select class="rainSelect form-control">
							<c:choose>
								<c:when test="${predictValue.createdAt != null && predictValue.predictedNum1 <= 0.0}">
									<option value="noRain" selected>내일은 비 안와요!</option>
									<option value="yesRain" >내일 비 와요!</option>
								</c:when>
								<c:when test="${predictValue.createdAt != null && predictValue.predictedNum1 > 0.0}">
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
								<input id="predictedNum1" type="text" class="predictedNum1 form-control" placeholder="${predictValue.predictedNum1 }">
								<div class="input-group-prepend">
									<div class="input-group-text">mm</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="mx-2 mt-4 d-flex align-items-center f-small justify-content-center text-white bg-success rounded">
					<div class="mr-1 font-weight-bold">Tip. 내일 <span id="regionName" class="group-region-${predictValue.predictOrder-1 }">${predictValue.regionName}</span> 지역, 기상청 예보</div>
					<div class="mr-1">오전 강수확률</div>
					<div id="shortRainAM" class="shortAM">${predictValue.shortForecastAMString }</div>
					<div class="mx-1">오후 강수확률</div>
					<div id="shortRainPM" class="shortPM">${predictValue.shortForecastPMString }</div>
				</div>
				<div class="mx-2 mt-3 d-flex align-items-center f-small justify-content-center text-white bg-success rounded">
					<div class="mr-1 font-weight-bold">Tip. 높은 적중률 사용자의 예측</div>
					<div class="mr-1">강수확률</div>
					<div>36.5</div>
					<div>%</div>
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