<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<input id="currentRegID" type="hidden" value="${shortForecastList[0].REG_ID }"/>
	<table id="forecastTable" class="table f-content text-center">
						<thead>
							<tr>
								<th></th>
								<th>날씨</th>
								<th>기온</th>
								<th>강수 확률</th>
							</tr>
						</thead>
						<tbody>
						<c:choose>
						<c:when test="${shortForecastList[0].TA ne -99}">
							<tr>
								<th>오늘 오전</th>
								<td>${shortForecastList[0].WF }</td>
								<td>${shortForecastList[0].TA }℃</td>
								<td>${shortForecastList[0].ST }%</td>
							</tr>
						</c:when>
						</c:choose>
							<tr>
								<th>오늘 오후</th>
								<td>${shortForecastList[1].WF }</td>
								<td>${shortForecastList[1].TA }℃</td>
								<td>${shortForecastList[1].ST }%</td>
							</tr>
							<tr>
								<th>내일 오전</th>
								<td>${shortForecastList[2].WF }</td>
								<td>${shortForecastList[2].TA }℃</td>
								<td>${shortForecastList[2].ST }%</td>
							</tr>
							<tr>
								<th>내일 오후</th>
								<td>${shortForecastList[3].WF }</td>
								<td>${shortForecastList[3].TA }℃</td>
								<td>${shortForecastList[3].ST }%</td>
							</tr>
							<tr>
								<th>모래 오전</th>
								<td>${shortForecastList[4].WF }</td>
								<td>${shortForecastList[4].TA }℃</td>
								<td>${shortForecastList[4].ST }%</td>
							</tr>
							<tr>
								<th>모래 오후</th>
								<td>${shortForecastList[5].WF }</td>
								<td>${shortForecastList[5].TA }℃</td>
								<td>${shortForecastList[5].ST }%</td>
							</tr>
						</tbody>
					</table>