<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no">
<title>나도캐스터, 로그인</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<link rel="stylesheet" href="/static/css/style.css" type="text/css">
</head>
<body>
	<div id="wrap">
		<div class="my-5">
			<div class="text-center f-content">모두가 기상국장이 되는 곳</div>
			<div class="text-center f-title font-weight-bold">나도 캐스터</div>
		</div>
		<hr>
		<div>
			<div class="m-3">
				<label class="f-content font-weight-bold">이메일 주소</label> <input
					id="emailInput" type="text" class="form-control">
			</div>
			<div class="m-3">
				<label class="f-content font-weight-bold">비밀번호</label> <input
					id="passwordInput" type="password" class="form-control">
			</div>
			<div class="pt-3">
				<div class="m-3 d-flex justify-content-center">
					<button id="loginBtn" class="btn btn-success text-white col-6">로그인</button>
				</div>
				<div id="forgortPWBtn" class="f-small text-center text-dark">비밀번호를
					잊으셨나요?</div>
			</div>
		</div>
		<div class="d-flex justify-content-center m-3">
			<img id="kakaoLoginBtn" class="btn" alt=""
				src="/static/images/kakao_login_medium_wide.png">
		</div>
		<hr>
		<div class="m-3">
			<div class="f-content font-weight-bold">로그인 없이 나도캐스터를 알아볼까요?</div>
			<div class="d-flex justify-content-center mt-4">
				<button id="testLoginBtn" class="btn btn-dark text-white">로그인 없이 둘러보기</button>
			</div>
		</div>
		<hr>
		<div class="m-3">
			<div class="f-content font-weight-bold">나도캐스터가 처음이신가요?</div>
			<div class="d-flex justify-content-center mt-4">
				<button id="regBtn" class="btn btn-primary ">5초만에 나도캐스터 등록하기</button>
			</div>
		</div>

		<%-- 모달 스크립트 시작 --%>
		<!-- Button trigger modal -->
		<button id="loginFailModal" type="button"
			class="btn btn-primary d-none" data-toggle="modal"
			data-target="#loginDenied">Launch demo modal</button>

		<!-- Modal -->
		<div class="modal fade px-5" id="loginDenied" tabindex="-1"
			aria-labelledby="loginDenied" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-body text-center f-content">
						<div>
							<b>잘못된 로그인 정보입니다.</b>
						</div>
						<div>이메일주소 혹은 비밀번호를 확인해 주세요.</div>
					</div>
					<div class="modal-footer d-flex justify-content-center">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">확인</button>
					</div>
				</div>
			</div>
		</div>
		<%-- 모달 스크립트 끝--%>

		<%-- 모달 스크립트 시작 --%>
		<!-- Button trigger modal -->
		<button id="kakaoRegiBtn" type="button" class="btn btn-primary d-none" 
			data-toggle="modal" data-target="#kakaoRegiModal">Launch
			demo modal</button>

		<!-- Modal -->
		<div class="modal fade px-5" id="kakaoRegiModal" tabindex="-1"
			aria-labelledby="kakaoRegiModal" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-body f-content">
						<h4 class="text-center">카카오 간편회원가입</h4>
						<hr>
						<div>
							<div class="m-1 font-weight-bold">사용할 캐스터이름을 입력해 주세요</div>
							<input id="nicknameInput" type="text" class="form-control">
							<div class="m-1 text-secondary">한글, 영문, 숫자를 최대 16자까지 입력할 수
								있어요.</div>
							<div id="duplicatedNicknameNoti" class="m-1 text-danger">사용할수 없는 캐스터이름 이에요.</div>
							<div id="nicknameConfirmedNoti" class="ml-3 f-content text-success">사용 가능한 캐스터 이름이에요.</div>
						</div>
						<hr>
						<div>
							<div class="font-weight-bold">주 활동 지역을 선택해 주세요!</div>
							<select id="regionSelect" class="form-control m-1">
								<option selected disabled>지역을 선택해주세요!</option>
								<c:forEach var="region" items="${regionList }">
									<option value="${region.RGID}">${region.regionName}</option>
								</c:forEach>
							</select>
							<div>지역은 언제든 변경 가능해요!</div>
						</div>

					</div>
					<div class="modal-footer d-flex justify-content-center">
						<button id="kakaoRegSubmitBtn" type="button"
							class="btn btn-primary">시작하기</button>
						<button id="closeNicknameModalBtn" type="button"
							class="btn btn-secondary" data-dismiss="modal">닫기</button>
					</div>
				</div>
			</div>
		</div>
		<%-- 모달 스크립트 끝--%>

		<%-- 모달 스크립트 시작 --%>
		<!-- Button trigger modal -->
		<button id="alphaNotiBtn" type="button" class="btn btn-primary d-none" 
			data-toggle="modal" data-target="#alphaNotiModal">Launch
			demo modal</button>

		<!-- Modal -->
		<div class="modal fade px-5" id="alphaNotiModal" tabindex="-1"
			aria-labelledby="kakaoRegiModal" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-body f-content">
						<h4 class="text-center">카카오 간편회원가입</h4>
						<hr>
						<div>
							<p class="m-1 text-dark text-center">현재는 개인이 제공하는 알파서비스이며,<br> 제한된 인원만 카카오로그인을 이용할 수 있습니다.</p>
							<p class="m-1 text-dark text-center">아래 메일로 카카오계정을 공유해 주시면<br> 테스터 등록을 해드리겠습니다.</p>
							<div class="f-small text-center">(gynpark@gmail.com)</div>
							<div class="m-1 font-weight-bold text-dark text-center">이미 테스터로 등록되어 있다면, 시작하기를 눌러주세요.</div>
						</div>
					</div>
					<div class="modal-footer d-flex justify-content-center">
						<button id="alphaNotiModalSubmitBtn" type="button"
							class="btn btn-primary">시작하기</button>
						<button id="closeNicknameModalBtn" type="button"
							class="btn btn-secondary" data-dismiss="modal">닫기</button>
					</div>
				</div>
			</div>
		</div>
		<%-- 모달 스크립트 끝--%>
		
		<!-- Button trigger modal -->
		<button id="regSuccessModal" type="button" class="btn btn-primary d-none" data-toggle="modal" data-target="#regFinished">
		  Launch demo modal
		</button>
		
		<!-- Modal -->
		<div class="modal fade px-5" id="regFinished" tabindex="-1" aria-labelledby="loginDenied" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered">
		    <div class="modal-content">
		      <div class="modal-body text-center f-content">
		        <div>환영합니다!</div>
		        <div><b id="nickModdal">닉네임</b>캐스터 님,</div>
		        <div>내일의 날씨를 예측해 볼까요?</div>
		      </div>
		      <div class="modal-footer d-flex justify-content-center">
		        <button id="finalBtn" type="button" class="btn btn-secondary" data-dismiss="modal">확인</button>
		      </div>
		    </div>
		  </div>
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
	<script>
		$(document).ready(function() {
							
			var kakaoResult = "${kakaoResult.result}";
			var kakaoEmail = "${kakaoResult.email}";
			var nickname = "";
			var NickID = "";
			var UID = "${UID}";
			
			if(kakaoResult == "success"){
				location.href = "/main/predict";
			};
			
			$("#kakaoLoginBtn").on("click", function(){
				$("#alphaNotiBtn").click();
			});
			
			
			$("#duplicatedNicknameNoti").hide();
			$("#nicknameConfirmedNoti").hide();
			
			$("#nicknameInput").on("change",function(){
				
				nickname = $("#nicknameInput").val();
				if(nicknameRegCheck(nickname)){
					$.ajax({
						url:"/rest/nickname/ifDuplicated"
						,type:"post"
						,data:{"nickname":nickname}
						,success:function(data){
							if(data.ifDuplicated){
								$("#duplicatedNicknameNoti").show();
								$("#nicknameConfirmedNoti").hide();
							}else{
								$("#duplicatedNicknameNoti").hide();
								$("#nicknameConfirmedNoti").show();
								NickID = data.NickID;
							}
						}
					})
				}
			})
							
			$("#nicknameInput").on("click",function(){
				if(NickID!=""){
					$.ajax({
						url:"/rest/nickname/removeNickname"
						,type:"post"
						,data:{"NickID":NickID}
						,success:function(data){
							$("#nicknameConfirmedNoti").hide();
							NickID="";
							}
					})
				} else{
					nickname = $("#nicknameInput").val();
					if(nicknameRegCheck(nickname)){
						$.ajax({
							url:"/rest/nickname/ifDuplicated"
							,type:"post"
							,data:{"nickname":nickname}
							,success:function(data){
								if(data.ifDuplicated){
									$("#duplicatedNicknameNoti").show();
									$("#nicknameConfirmedNoti").hide();
								}else{
									$("#duplicatedNicknameNoti").hide();
									$("#nicknameConfirmedNoti").show();
									NickID = data.NickID;
								}
							}
						})
					} else{
						$("#duplicatedNicknameNoti").show();
						$("#nicknameConfirmedNoti").hide();
					}
				}
			})
							
	 		$("#kakaoRegSubmitBtn").on("click",function(){
				
				var RGID = $("#regionSelect").val();
				
				if(RGID==""){
					alert("지역을 선택해 주세요");
					return;
				}
				if(NickID==""){
					alert("닉네임을 입력해 주세요.");
					return;
				}
				
				$.ajax({
					url:"/rest/reg/submit"
					,type:"post"
					,data:{
						"email":kakaoEmail
						,"password":"kakao"
						,"NickID":NickID
						,"RGID":RGID
						,"optionalTerms":false
						,"ifKakao":true
					}
					,success:function(data){
						if(data.result=="success"){
							$("#nickModdal").text(nickname);
							$("#kakaoRegiModal").modal("hide");							
							$("#regSuccessModal").click();
						} else{
							alert("회원가입에 실패했어요\n다시 시도해 주세요");
						}
					}
					,error:function(){
						alert("회원가입 중 오류가 발생했어요\n다시 시도해 주세요");
						}
					})
				})

				if(kakaoResult == "newUser"){
					$("#kakaoRegiBtn").click();
					kakaoEmail = "${kakaoResult.email}";
				}

				$("#alphaNotiModalSubmitBtn").on("click",function() {
					location.href = "https://kauth.kakao.com/oauth/authorize?client_id=c549a77834b5765bcedd1aedb20e046c&redirect_uri="+window.location.origin+"/Oauth/authorize/callback&response_type=code";
				});

				$("#passwordInput").keydown(function(e) {
					if (e.which == 13) {
						e.preventDefault();
						$("#loginBtn").click();
					}
				});

				$("#loginBtn").on("click", function() {

					var emailInput = $("#emailInput").val();
					var passwordInput = $("#passwordInput").val();

					if (emailInput == "") {
						alert("이메일 주소를 입력해 주세요.");
						return;
					}

					if (passwordInput == "") {
						alert("비밀번호를 입력해 주세요.");
						return;
					}

					$.ajax({
						url : "/rest/login/submit",
						type : "post",
						data : {
							"email" : emailInput,
							"password" : passwordInput
						},
						success : function(data) {
							if (data.result == "success") {
								location.href = "/main/predict";
							} else {
								$("#loginFailModal").click();
							}
						},
						error : function() {
							alert("로그인 중 오류가 발생했어요.");
						}
					})
				});

				$("#testLoginBtn").on("click", function() {

					$.ajax({
						url : "/rest/login/tester",
						type : "post",
						success : function(data) {
							if (data.result == "success") {
								location.href = "/main/predict";
							} else {
								$("#loginFailModal").click();
							}
						},
						error : function() {
							alert("로그인 중 오류가 발생했어요.");
						}
					})
				});

				$("#regBtn").on("click", function() {
					location.href = "/registration"
				});

				$("#forgortPWBtn").on("click", function() {
					location.href = "/main/login/forgotPassword"
				});
				
				$("#finalBtn").on("click",function(){
					location.href="/main/predict"
				})
							
		        function nicknameRegCheck(nickname) {
		        	var regex = /^[a-zA-Z0-9가-힣]{1,16}$/;
					return (regex.test(nickname));
		        };
							
	    });
	</script>

</body>
</html>