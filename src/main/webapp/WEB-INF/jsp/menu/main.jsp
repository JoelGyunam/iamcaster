<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no">
<title>나도캐스터</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link rel="stylesheet" href="/static/css/style.css" type="text/css">
</head>
<body>
	<div id="wrap">

		<section id="content" class="container border-top f-content">
			<div style="height: 5px"></div>
			<div>
				<h3 class="text-right m-3 font-weight-bold" onclick="history.back()">x</h3>
				<div class="ml-2">
					<span class="font-weight-bold">${userinfo.nickname }</span> 님,<br>오늘
					날씨는 어떤가요?
				</div>
			</div>
			<div style="height: 5px"></div>
			<div class="card my-2">
				<div class="mt-3 ml-3 font-weight-bold">나의 정보</div>
				<div class="d-flex align-items-center m-1 ml-3">
					<div class="font-weight-bold col-4">캐스터 이름</div>
					<div class="col-5">${userinfo.nickname }</div>
					<button id="changeNicknameBtn"
						class="btn btn-dark text-white btn-sm">변경 하기</button>
				</div>
				<div class="d-flex align-items-center m-1 ml-3">
					<div class="font-weight-bold col-4">계정 이메일</div>
					<div class="col-5">
						<c:choose>
						<c:when test="${userinfo.ifKakao }">
						<img width="30px" src="https://t1.kakaocdn.net/kakaocorp/kakaocorp/admin/5f9c58c2017800001.png">
						</c:when>
						</c:choose>
						${userinfo.email }
					</div>
				</div>
				<div class="d-flex align-items-center m-1 ml-3">
					<div class="font-weight-bold col-4">나의 지역</div>
					<div class="col-5">${userinfo.regionName }</div>
					<button id="changeRegionBtn" class="btn btn-dark text-white btn-sm">지역변경</button>
				</div>
				<div class="d-flex align-items-center m-1 ml-3 mb-3">
					<div class="font-weight-bold col-4">나의 적중률</div>
					<div class="col-5">${userinfo.accuracyRate }</div>
				</div>
			</div>
			<div class="card my-2 ">
				<div class="mt-3 ml-3 font-weight-bold">서비스 카테고리</div>
				<div class="m-1 ml-3">
					<div class="my-2 ml-3">
						<a href="/main/predict" class="text-dark">날씨 예측하기</a>
					</div>
					<div class="my-2 ml-3">
						<a href="/main/myscore" class="text-dark">나의 적중률</a>
					</div>
					<div class="my-2 ml-3">
						<a href="/main/userRank" class="text-dark">캐스터 순위</a>
					</div>
					<div class="my-2 ml-3">
						<a href="/main/shortforecast" class="text-dark">단기예보 확인하기</a>
					</div>
				</div>
			</div>
			<div class="card my-2 ">
				<div class="mt-3 ml-3 font-weight-bold">커뮤니티</div>
				<div class="m-1 ml-3">
					<div class="my-2 ml-3">
						<a href="/main/notice" class="text-dark">공지 사항</a>
					</div>
					<div class="my-2 ml-3">오류 제보 및 문의</div>
				</div>
			</div>
			<div class="card my-2 ">
				<div class="m-1 ml-3">
					<div class="my-2 ml-3">
						<a href="/policies/terms" class="text-dark">이용 약관</a>
					</div>
					<div class="my-2 ml-3">
						<a href="/policies/privacy" class="text-dark">개인정보 처리 방침</a>
					</div>
					<div class="d-flex align-items-center justify-content-between">
						<div class="my-2 ml-3">혜택 수신동의</div>
						<c:choose>
							<c:when test="${userinfo.optionalTerms eq null }">
								<button id="optionalTermsBtn"
									class="btn btn-primary text-white btn-sm mr-3">이벤트 및
									혜택정보 받기</button>
							</c:when>
							<c:otherwise>
								<div id="optionalTermsAgreedDate" class="mr-3"
									style="font-size: 8px">
									수신동의 일자<span class="font-weight-bold"><br>
									<c:set var="localDate"
											value="${userinfo.optionalTerms.toLocalDate() }" />${localDate}</span>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					<div id="changePW" class="my-2 ml-3">비밀번호 변경</div>
					<div id="logout" class="my-2 ml-3">로그아웃</div>
				</div>
			</div>
			<div id="withdrawal" class="f-small text-center"
				style="color: #66666666;">회원탈퇴</div>
			<div style="height: 20px"></div>

		</section>

		<%-- 선택 수신 동의 모달 태그 시작 --%>
		<button id="toDisOptionalTermsBtn" type="button"
			class="btn btn-primary d-none" data-toggle="modal"
			data-target="#toDisOptionalTerms">Launch demo modal</button>

		<!-- Modal -->
		<div class="modal fade px-5" id="toDisOptionalTerms" tabindex="-1"
			aria-labelledby="optionalTerms" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-body text-center f-content">
						<div class="text-center">
							<div>철회를 하면 혜택 및 이벤트에 대해서 알려드릴 수 없어요.</div>
							<div>혜택 수신동의를 철회할까요?</div>
						</div>
					</div>
					<div class="modal-footer d-flex justify-content-center">
						<button id="optinalDisagreeBtn" type="button"
							class="btn btn-primary" data-dismiss="modal">철회하기</button>
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">닫기</button>
					</div>
				</div>
			</div>
		</div>
		<%-- 선택 수신 동의 태그 끝--%>

		<%-- 닉네임 변경 모달 태그 시작 --%>
		<button id="toChangeNicknameBtn" type="button"
			class="btn btn-primary d-none" data-toggle="modal"
			data-target="#nicknameChangeModal">Launch demo modal</button>

		<!-- Modal -->
		<div class="modal fade px-5" id="nicknameChangeModal" tabindex="-1"
			aria-labelledby="optionalTerms" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-body f-content">
						<div>
							<div class="m-1">변경할 캐스터이름을 입력해 주세요</div>
							<input id="changeNicknameInput" type="text" class="form-control">
							<div class="m-1 text-secondary">한글, 영문, 숫자를 최대 16자까지 입력할 수
								있어요.</div>
							<div id="dupNicknameAlert" class="m-1 text-danger">이미 사용중인
								캐스터이름 이에요.</div>
						</div>
					</div>
					<div class="modal-footer d-flex justify-content-center">
						<button id="changeNicknameModalBtn" type="button"
							class="btn btn-primary">변경하기</button>
						<button id="closeNicknameModalBtn" type="button"
							class="btn btn-secondary" data-dismiss="modal">닫기</button>
					</div>
				</div>
			</div>
		</div>
		<%-- 닉네임 변경 모달 태그 끝--%>

		<%-- 지역변경 모달 태그 시작 --%>
		<button id="toChangeRegionBtn" type="button"
			class="btn btn-primary d-none" data-toggle="modal"
			data-target="#changeRegionModelBtn">Launch demo modal</button>

		<!-- Modal -->
		<div class="modal fade px-5" id="changeRegionModelBtn" tabindex="-1"
			aria-labelledby="optionalTerms" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-body f-content">
						<div>
							<div class="m-1">변경할 지역을 선택해 주세요</div>
							<select id="regionSelect" class="form-control">
								<option selected disabled>지역을 선택해주세요!</option>
								<c:forEach var="region" items="${regionList }">
									<option value="${region.RGID}">${region.regionName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="modal-footer d-flex justify-content-center">
						<button id="changeRegionModalBtn" type="button"
							class="btn btn-primary" data-dismiss="modal" disabled>변경하기</button>
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">닫기</button>
					</div>
				</div>
			</div>
		</div>
		<%-- 지역변경 모달 태그 끝--%>

		<%-- 현재 비밀번호 확인 모달 태그 시작 --%>
		<button id="toChangePWBtn" type="button"
			class="btn btn-primary d-none" data-toggle="modal"
			data-target="#changePWModal">Launch demo modal</button>

		<!-- Modal -->
		<div class="modal fade px-5" id="changePWModal" tabindex="-1"
			aria-labelledby="optionalTerms" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-body f-content">
						<div>
							<label class="m-1">비밀번호를 변경하기 위해, 현재 비밀번호를 입력해 주세요.</label> <input
								id="changePWinput" type="password" class="form-control">
							<div class="mt-3 text-secondary">비밀번호를 잊으셨나요?</div>
							<a class="font-weight-bold text-secondary text-right mr-auto"
								href="/main/login/forgotPassword">임시 비밀번호 발급받기</a>
						</div>
					</div>
					<div class="modal-footer d-flex justify-content-center">
						<button id="changePWModalBtn" type="button"
							class="btn btn-primary">변경하기</button>
						<button id="changePWModalCloseBtn" type="button"
							class="btn btn-secondary" data-dismiss="modal">닫기</button>
					</div>
				</div>
			</div>
		</div>
		<%-- 현재 비밀번호 확인 모달 태그 끝--%>

		<%-- 비밀번호 변경 모달 태그 시작 --%>
		<button id="newPWBtn" type="button" class="btn btn-primary d-none"
			data-toggle="modal" data-target="#newPWModal">Launch demo
			modal</button>

		<!-- Modal -->
		<div class="modal fade px-5" id="newPWModal" tabindex="-1"
			aria-labelledby="optionalTerms" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-body f-content">
						<div>
							<label class="m-1">변경할 새로운 비밀번호를 입력해 주세요.</label> <input
								id="newPWinput" type="password" class="form-control">
							<div id="pwFormatErrorNoti" class="ml-3 text-danger">비밀번호
								형식을 확인해 주세요.</div>

							<label class="m-1">새로운 비밀번호를 한번 더 입력해 주세요.</label> <input
								id="newPWCheckInput" type="password" class="form-control">
							<div class="mt-3 text-secondary">8~16자의 대소문자,숫자,특수문자로 조합할 수
								있어요.</div>
							<div id="pwDoublecheckErrorNoti" class="ml-3 text-danger">비밀번호가
								일치하지 않아요.</div>
						</div>
					</div>
					<div class="modal-footer d-flex justify-content-center">
						<button id="newPWModalBtn" type="button" class="btn btn-primary"
							disabled>변경하기</button>
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">닫기</button>
					</div>
				</div>
			</div>
		</div>
		<%-- 비밀번호 변경 모달 태그 끝--%>

		<%-- 로그아웃 모달 태그 시작 --%>
		<button id="logoutBtn" type="button" class="btn btn-primary d-none"
			data-toggle="modal" data-target="#logoutModal">Launch demo
			modal</button>

		<!-- Modal -->
		<div class="modal fade px-5" id="logoutModal" tabindex="-1"
			aria-labelledby="optionalTerms" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-body">
						<div class="text-center">로그아웃 할까요?</div>
					</div>
					<div class="modal-footer d-flex justify-content-center">
						<button id="logoutModalBtn" type="button" class="btn btn-primary">로그아웃</button>
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">닫기</button>
					</div>
				</div>
			</div>
		</div>
		<%-- 로그아웃 모달 태그 끝--%>

		<%-- 회원탈퇴 비밀번호 확인 모달 태그 시작 --%>
		<button id="withdrawalBtn" type="button"
			class="btn btn-primary d-none" data-toggle="modal"
			data-target="#withdrawalModal">Launch demo modal</button>

		<!-- Modal -->
		<div class="modal fade px-5" id="withdrawalModal" tabindex="-1"
			aria-labelledby="optionalTerms" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-body f-content">
						<label class="m-1">회원탈퇴를 위해, 현재 비밀번호를 입력해 주세요.</label> <input
							id="withdrawalPWinput" type="password" class="form-control">
						<div id="withdrawalPWinputErrorNoti" class="ml-3 text-danger">비밀번호가
							다릅니다.</div>
						<div class="modal-footer d-flex justify-content-center">
							<button id="withdrawalModalBtn" type="button"
								class="btn btn-danger" disabled>탈퇴하기</button>
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">닫기</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%-- 회원탈퇴 비밀번호 확인 모달 태그 끝--%>

		<%-- 회원탈퇴 확인 모달 태그 시작 --%>
		<button id="withdrawalConfirmBtn" type="button"
			class="btn btn-primary d-none" data-toggle="modal"
			data-target="#withdrawalConfirmModal">Launch demo modal</button>

		<!-- Modal -->
		<div class="modal fade px-5" id="withdrawalConfirmModal" tabindex="-1"
			aria-labelledby="optionalTerms" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-body text-center f-content">
						<div class="m-1">
							탈퇴 시 활동 기록 및 모든 정보가 삭제되며,<br>복구할 수 없습니다.
						</div>
						<div class="m-1 font-weight-bold">정말로 탈퇴하시겠습니까?</div>
						<div class="modal-footer d-flex justify-content-center">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">취소</button>
							<button id="withdrawalConfirmModalBtn" type="button"
								class="btn btn-danger">탈퇴하기</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%-- 회원탈퇴 확인 모달 태그 끝--%>

		<%-- 회원탈퇴 확인 모달 태그 시작 --%>
		<button id="byebyeBtn" type="button" class="btn btn-primary d-none"
			data-toggle="modal" data-target="#byebyeModal">Launch demo
			modal</button>

		<!-- Modal -->
		<div class="modal fade px-5" id="byebyeModal" tabindex="-1"
			aria-labelledby="optionalTerms" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-body text-center f-content">
						<div class="m-1">
							언제든 다시 찾아주세요.<br>더 재미있고 의미있는 콘텐츠들을 준비하고 있을게요!
						</div>
						<div class="m-1 font-weight-bold">진심으로 고맙습니다.</div>
						<div class="modal-footer d-flex justify-content-center">
							<button id="lastBtn" type="button" class="btn btn-secondary"
								data-dismiss="modal">안녕</button>
						</div>
					</div>
				</div>
			</div>
			<%-- 회원탈퇴 확인 모달 태그 끝--%>





		</div>
	</div>

	<jsp:include page="/WEB-INF/jsp/global/footer.jsp" />

	<script src="https://code.jquery.com/jquery-3.6.4.min.js"
		integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8="
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"
		integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@0.7.0"></script>
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>


	<script>
		$(document).ready(function(){
			$("#dupNicknameAlert").hide();
			$("#pwFormatErrorNoti").hide();
			$("#pwDoublecheckErrorNoti").hide();
			$("#withdrawalPWinputErrorNoti").hide();
			
			$("#lastBtn").on("click",function(){
				location.href="/greeting";
			});
			
			$("#withdrawalConfirmModalBtn").on("click",function(){
				$.ajax({
					url:"/rest/withdrawal"
					,type:"post"
					,success:function(data){
						if(data.result=="bye"){
							$("#withdrawalConfirmModal").modal("hide");
							$("#byebyeBtn").click();
						} else{
							alert("탈퇴 중에 오류가 발생했어요.");
						}
					}
				})
			});
			
			$("#withdrawalPWinput").on("keyup",function(){
				if(!pwRegCheck($(this).val())){
					$("#withdrawalModalBtn").prop("disabled",true);
				}else{
					$("#withdrawalModalBtn").prop("disabled",false);
				}
			});
			
			$("#withdrawalModalBtn").on("click",function(){
				$.ajax({
					url:"/rest/login/submit"
					,type:"post"
					,data:{
						"email":"${userinfo.email}"
						,"password":$("#withdrawalPWinput").val()
					}
					,success:function(data){
						if(data.result=="success"){
							$("#withdrawalModal").modal("hide");
							$("#withdrawalConfirmBtn").click();
						} else{
							alert("비밀번호가 일치하지 않습니다.");
							$("#withdrawalPWinputErrorNoti").show();
						}
					}
				})
			});
			
			$("#withdrawal").on("click",function(){
				if(${userinfo.ifKakao}){
					$("#withdrawalConfirmBtn").click();
				}else{
					$("#withdrawalBtn").click();
				}
			});
			
			$("#logoutModalBtn").on("click",function(){
				$.ajax({
					url:"/rest/logout"
					,type:"post"
					,success:function(data){
						if(data.result=="success"){
							location.href="/greeting";
						} else{
							alert("로그아웃에 실패했어요.");
						}
					}
				})
			});
			
			$("#logout").on("click",function(){
				$("#logoutBtn").click();
			});
			
			
			$("#newPWinput").on("keyup",function(){
				var newPW = $(this).val();
				
				$("#newPWCheckInput").val("");
				$("#newPWModalBtn").prop("disabled",true);
				
				if(!pwRegCheck(newPW)){
					$("#pwFormatErrorNoti").show();
				} else{
					$("#pwFormatErrorNoti").hide();
				}
			});
			
			$("#newPWModalBtn").on("click",function(){
				var newPW = $("#newPWinput").val();
				if(pwRegCheck(newPW)){
					$.ajax({
						url:"/rest/info/changePW"
						,type:"post"
						,data:{"newPassword":newPW}
						,success:function(data){
							if(data.result == "success"){
								$("#newPWModal").modal("hide");
								alert("비밀번호 변경을 성공했어요!");
								location.reload();
							} else{
								alert("비밀번호 변경에 실패했어요.");
							}
						}
					})
				}
			})
			
			$("#newPWCheckInput").on("keyup",function(){
				var newPW = $("#newPWinput").val();
				var newPWCheckInput = $(this).val();
				
				if(newPW!=newPWCheckInput){
					$("#pwDoublecheckErrorNoti").show();
					$("#newPWModalBtn").prop("disabled",true);
				} else{
					$("#pwDoublecheckErrorNoti").hide();
					$("#newPWModalBtn").prop("disabled",false);
				}
			});
			
			$("#changePW").on("click",function(){
				$("#toChangePWBtn").click();
			})
			
			$("#changePWModalBtn").on("click",function(){
				$.ajax({
					url:"/rest/login/submit"
					,type:"post"
					,data:{
						"email":"${userinfo.email}"
						,"password":$("#changePWinput").val()
					}
					,success:function(data){
						if(data.result=="success"){
							$("#changePWModalCloseBtn").click();
							$("#newPWBtn").click();
						} else{
							alert("잘못된 비밀번호 입니다.");
						}
					}
				})
			});
			
			$("#changeRegionBtn").on("click",function(){
				$("#toChangeRegionBtn").click();
			})
			
			$("#regionSelect").on("change",function(){
				var regionSelect = $("#regionSelect").val();
				if(regionSelect != "${userinfo.RGID}"){
					$("#changeRegionModalBtn").prop("disabled",false);
				} else{
					$("#changeRegionModalBtn").prop("disabled",true);
				}
			});
			
			$("#changeRegionModalBtn").on("click",function(){
				var regionSelect = $("#regionSelect").val();
				$.ajax({
					url:"/rest/userinfo/edit/rgid"
					,type:"get"
					,data:{"RGID":regionSelect}
					,success:function(data){
						if(data.result == "success"){
							alert("나의 지역을 변경했어요!");
							location.reload();
						} else{
							alert("지역변경에 실패했어요.\n다시 시도해 주세요.");
						}
					}
				})
					
			})
			
			$("#optionalTermsBtn").on("click",function(){
				$.ajax({
					url:"/rest/terms/optionalAgreed"
					,type:"get"
					,data:{
						"UID" : ${UID}
						,"ifAgreed" : true
						}
					,success:function(data){
						if(data.result == "success"){
							alert("이제부터 혜택을 알려드릴게요.");
							location.reload();
						}
					}
				})
			});
			
			$("#optionalTermsAgreedDate").on("click",function(){
				$("#toDisOptionalTermsBtn").click();
			});
			
			$("#optinalDisagreeBtn").on("click",function(){
				$.ajax({
					url:"/rest/terms/optionalAgreed"
					,type:"get"
					,data:{
						"UID" : ${UID}
						,"ifAgreed" : false
						}
					,success:function(data){
						if(data.result == "success"){
							alert("철회되었어요.");
							location.reload();
						} else{
							alert("오류가 발생했습니다.\n잠시후 다시 시도해 주세요");
						}
					}
				});
			});
			
			$("#changeNicknameBtn").on("click",function(){
				$("#toChangeNicknameBtn").click();
				
			})
			
			$("#changeNicknameModalBtn").on("click",function(){
				var nickname = $("#changeNicknameInput").val();
				$.ajax({
					url:"/rest/nickname/changeNickname"
					,type:"post"
					,data:{
						"nickname":nickname
					}
					,success:function(data){
						if(data.result=="success" || data.result=="deleteError" ){
							alert("성공적으로 변경되었어요.");
							$("#closeNicknameModalBtn").click();
							location.reload();
						} else if(data.result == "duplicatedNickname"){
							$("#dupNicknameAlert").show();
						} 
					}
				})
			})
			
			$("#changeNicknameInput").on("click",function(){
				$("#dupNicknameAlert").hide();
			})
			
		});
		
        function pwRegCheck(password) {
        	var regex = /^[A-Za-z\d@$!%*?&]{8,16}$/;
			return (regex.test(password));
        };

	</script>


</body>
</html>