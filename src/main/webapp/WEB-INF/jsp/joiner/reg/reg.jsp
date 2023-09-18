<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
	<title>나도캐스터, 회원가입</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link rel="stylesheet" href="/static/css/style.css" type="text/css">
</head>
<body>
	<div id="wrap">
		<div class="my-3">
			<div class="text-center f-content">모두가 기상국장이 되는 곳</div>
			<div class="text-center f-title font-weight-bold">나도 캐스터</div>
		</div>
		<hr>
		<div>
			<div class="text-center f-title font-weight-bold">회원가입</div>
			<div id="emailArea" class="m-3">
				<label class="f-content font-weight-bold">이메일 주소를 입력해 주세요.</label>
				<div class="d-flex">
					<input id="emailInput" type="text" class="form-control">
					<button id="emailVerifBtn" class="btn btn-primary small">이메일 인증</button>
				</div>
				<div id="duplicatedEmailNoti" class="f-content text-danger">사용중인 이메일이에요.</div>
				<div id="emailConfirmedNoti" class="f-content text-success">인증 완료</div>
				<div id="emailFormatErrorNoti" class="f-content text-danger">이메일 형식을 확인해 주세요.</div>
				<div id="emailVeriSuccess" class="f-content text-success">이메일이 인증되었어요.</div>
			</div>
			<div id="emailVerifArea" class="emailVerifArea d-flex align-items-center m-3">
				<input id="codeInput" type="text" class="emailVerifArea form-control f-content" placeholder="인증번호 입력">
				<i class="emailVerifArea bi-clock f-content ml-3"></i><div id="timerValue" class="emailVerifArea f-content ml-2 mr-2">03:00</div>
				<button id="verifCodeSubmitBtn" class="emailVerifArea btn btn-primary small">인증 하기</button>
			</div>
			<div id="pwArea" class="m-3">
				<label class="f-content font-weight-bold">비밀번호를 설정해 주세요.</label>
				<input id="pwInput" type="password" class="form-control">
				<div id="pwFormatErrorNoti" class="ml-3 f-content text-danger">비밀번호 형식을 확인해 주세요.</div>
				<div id="pwFormatOkNoti" class="ml-3 f-content text-success">사용 가능한 비밀번호 형식이에요!</div>
				<div class="ml-3 f-content text-secondary">8 ~ 16자의 대소문자, 숫자, 특수문자로 조합할 수 있어요.</div>
			</div>
			<div id="pwDoublecheckArea" class="m-3">
				<label class="f-content font-weight-bold">비밀번호 확인</label>
				<input id="pwDoublecheckInput" type="password" class="form-control">
				<div id="pwDoublecheckErrorNoti" class="ml-3 f-content text-danger">비밀번호가 일치하지 않아요.</div>
				<div id="pwDoublecheckOkNoti" class="ml-3 f-content text-success">비밀번호가 일치해요!</div>
			</div>
			<div id="nicknameArea" class="m-3">
				<label class="f-content font-weight-bold">사용할 캐스터 이름을 입력해 주세요.</label>
				<input id="nicknameInput" type="text" class="form-control" maxlength="16">
				<div id="duplicatedNicknameNoti" class="ml-3 f-content text-danger">이미 사용중인 캐스터 이름이에요.</div>
				<div id="nicknameConfirmedNoti" class="ml-3 f-content text-success">사용 가능한 캐스터 이름이에요.</div>
				<div class="ml-3 f-content text-secondary">한글, 영문, 숫자를 최대 16자 까지 입력할 수 있어요.</div>
			</div>
			<div id="regionArea" class="m-3">
				<label class="f-content font-weight-bold">나의 지역을 선택해 주세요</label>
				<select id="regionSelect" class="form-control">
					<option selected disabled>지역을 선택해주세요!</option>
					<c:forEach var="region" items="${regionList }">
						<option value="${region.RGID}">${region.regionName}</option>
					</c:forEach>
				</select>
				<div class="ml-3 f-content text-secondary">가입 후에도 언제든지 수정 가능해요.</div>
			</div>
			
			<div class="card p-3">
				<div class="f-content font-weight-bold">나도캐스터 회원 약관</div>
				<div class="d-flex">
					<label class="f-content mt-4 font-weight-bold"><input id="termsAll" type="checkbox">      전체 동의하기</label>
					<div id="openMore" class="ml-5 f-content mt-4 font-weight-bold">↓</div>
				</div>
				<div id="termsLines" class="ml-2 d-none">
					<hr class="mr-2">
					<label class="f-content"><input id="ageOver" class="terms" type="checkbox">  만 14세 이상입니다.</label><br>
					<label class="f-content"><input id="defaultTerms" class="terms" type="checkbox">  [필수] 나도캐스터 이용약관 <a href="/policies/terms"> (약관 상세) ></a></label><br>
					<label class="f-content"><input id="optionalTerms" class="terms" type="checkbox">  [선택] 이벤트 및 혜택 정보 수신</label>
				</div>
			</div>
			
			
			<div class="pt-3">
				<div class="m-3 d-flex justify-content-center">
					<button id="regSubmitBtn" class="btn btn-success col-6 text-white">완료하기</button>
				</div>
			</div>
		</div>

<%-- 모달 스크립트 시작 --%>
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
		
		<!-- Button trigger modal -->
		<button id="wrongVerifModal" type="button" class="btn btn-primary d-none" data-toggle="modal" data-target="#wrongVerif">
		  Launch demo modal
		</button>
		
		<!-- Modal -->
		<div class="modal fade px-5" id="wrongVerif" tabindex="-1" aria-labelledby="wrongVerif" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered">
		    <div class="modal-content">
		      <div class="modal-body text-center f-content">
		        <div>잘못된 인증번호 입니다.</div>
		        <div>다시 입력해 주세요!</div>
		      </div>
		      <div class="modal-footer d-flex justify-content-center">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">확인</button>
		      </div>
		    </div>
		  </div>
		</div>
		
		<!-- Button trigger modal -->
		<button id="confirmVerifModal" type="button" class="btn btn-primary d-none" data-toggle="modal" data-target="#confirmVerif">
		  Launch demo modal
		</button>
		
		<!-- Modal -->
		<div class="modal fade px-5" id="confirmVerif" tabindex="-1" aria-labelledby="loginDenied" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered">
		    <div class="modal-content">
		      <div class="modal-body text-center f-content">
		        <div>인증번호가 확인되었어요.</div>
		        <div>계속 진행해 주세요!</div>
		      </div>
		      <div class="modal-footer d-flex justify-content-center">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">확인</button>
		      </div>
		    </div>
		  </div>
		</div>
		
		<!-- Button trigger modal -->
		<button id="sendVerifModal" type="button" class="btn btn-primary d-none" data-toggle="modal" data-target="#sendVerif">
		  Launch demo modal
		</button>
		
		<!-- Modal -->
		<div class="modal fade px-5" id="sendVerif" tabindex="-1" aria-labelledby="sendVerif" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered">
		    <div class="modal-content">
		      <div class="modal-body text-center f-content">
		        <div>인증번호가 발송되었어요!</div>
		        <div>메일함에서 확인해 주세요.</div>
		      </div>
		      <div class="modal-footer d-flex justify-content-center">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">확인</button>
		      </div>
		    </div>
		  </div>
		</div>
		
		<!-- Button trigger modal -->
		<button id="emailDupModal" type="button" class="btn btn-primary d-none" data-toggle="modal" data-target="#emailDupAlert">
		  Launch demo modal
		</button>
		
		<!-- Modal -->
		<div class="modal fade px-5" id="emailDupAlert" tabindex="-1" aria-labelledby="emailDupAlert" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered">
		    <div class="modal-content">
		      <div class="modal-body text-center f-content">
		        <div>이미 가입된 이메일이에요.</div>
		        <div>다시 확인해 주세요.</div>
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
			$("#emailFormatErrorNoti").hide();
			$("#duplicatedEmailNoti").hide();
			$(".emailVerifArea").hide();
			$("#emailConfirmedNoti").hide();
			$("#pwFormatOkNoti").hide();
			$("#pwFormatErrorNoti").hide();
			$("#pwDoublecheckOkNoti").hide();
			$("#pwDoublecheckErrorNoti").hide();
			$("#duplicatedNicknameNoti").hide();
			$("#nicknameConfirmedNoti").hide();
			$("#emailVeriSuccess").hide();
			var timer = 0;
			var emailInput = "";
			var ifCodeVerified = false;
			var pwInput = "";
			var NickID = "";
			var nickname = "";
			var nicknameDupCheck = false;
			var ageOver = false;
			var defaultTerms = false;
			var optionalTerms = false;
			
			$("#openMore").on("click",function(){
				$("#termsLines").toggleClass("d-none");
			})
			
			$("#termsAll").on("click",function(){
				
				var result = $(this).is(":checked");
				if(result==true){
					$("#ageOver").prop("checked",true);
					ageOver = $("#ageOver").is(":checked");

					$("#defaultTerms").prop("checked",true);
					defaultTerms = $("#defaultTerms").is(":checked");

					$("#optionalTerms").prop("checked",true);
					optionalTerms = $("#optionalTerms").is(":checked");

				} else{
					$("#ageOver").prop("checked",false);
					ageOver = $("#ageOver").is(":checked");

					$("#defaultTerms").prop("checked",false);
					defaultTerms = $("#defaultTerms").is(":checked");

					$("#optionalTerms").prop("checked",false);
					optionalTerms = $("#optionalTerms").is(":checked");
				}

			});
			
			$(".terms").on("click",function(){
				ageOver = $("#ageOver").is(":checked");
				defaultTerms = $("#defaultTerms").is(":checked");
				optionalTerms = $("#optionalTerms").is(":checked");

				
				if(ageOver == false || defaultTerms == false || optionalTerms == false){
					$("#termsAll").prop("checked",false);
				};
				if(ageOver == true && defaultTerms == true && optionalTerms == true){
					$("#termsAll").prop("checked",true);
				}
			})

			
			$("#regSubmitBtn").on("click",function(){
				var RGID = $("#regionSelect").val();
				
				if(!ifCodeVerified){
					alert("이메일 인증을 해주세요");
					return;
				}
				if($("#pwInput").val()==""){
					alert("비밀번호를 입력해 주세요");
					return;
				}
				if(!pwRegCheck(pwInput)){
					alert("사용할 수 없는 비밀번호에요.");
					return;
				}
				if($("#pwInput").val()!=$("#pwDoublecheckInput").val()){
					alert("비밀번호가 일치하지 않아요");
					return;
				}
				if(RGID==""){
					alert("지역을 선택해 주세요");
					return;
				}
				if(ageOver==false){
					alert("만 14세 미만은 서비스 이용이 불가합니다.\n14세 이상 여부를 알려주세요.");
					return;
				}
				if(termsAll==false){
					alert("필수 약관에 동의해 주세요");
					return;
				}
				if(NickID == ""){
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
									alert("이미 사용중인 캐스터이름이에요.");
								}else{
									NickID = data.NickID;
									$("#duplicatedNicknameNoti").hide();
									$("#nicknameConfirmedNoti").show();
								}
							}
						})
					}
				}
				
				$.ajax({
					url:"/rest/reg/submit"
					,type:"post"
					,data:{
						"email":emailInput
						,"password":pwInput
						,"NickID":NickID
						,"RGID":RGID
						,"optionalTerms":optionalTerms
					}
					,success:function(data){
						if(data.result=="success"){
							$("#nickModdal").text(nickname);
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
			
			
			$("#emailVerifBtn").on("click",function(){
				
				emailInput = $("#emailInput").val();
				timer = 180;
				if(!IsEmail(emailInput)){
					$("#emailFormatErrorNoti").show();
					$(".emailVerifArea").hide();
					return;
				}
				if(IsEmail(emailInput)){
					$("#emailFormatErrorNoti").hide();
				}
				
				$.ajax({
					url:"/rest/reg/emailverify/ifDuplicated"
					,type:"get"
					,data:{
						"email":emailInput
					}
					,success:function(data){
						if(!data.ifDuplicated){
							$("#duplicatedEmailNoti").hide();
							$.ajax({
								url:"/rest/emailVerify/send"
								,type:"post"
								,data:{
									"email":emailInput
								}
								,success:function(data){
									if(data.result=="success"){
										$(".emailVerifArea").show();
										$("#emailVerifBtn").prop("disabled",true);
										$("#emailInput").prop("disabled",true);
										$("#sendVerifModal").click();
										timerSection = setInterval(function(){
											if(timer > 0){
												verifyTimer();
												}else{
													clearInterval(timerSection);
													$("#emailVerifBtn").prop("disabled",false);
													$("#emailInput").prop("disabled",false);
													$(".emailVerifArea").hide();
													}
											}
										,1000);}
								}
								,error:function(){
									alert("인증코드 발송 중 오류가 발생헀어요!\n다시 시도해 주세요.");
								}
							})
						} else{
							$("#emailDupModal").click();
							$("#duplicatedEmailNoti").show();
							return;
						}
					}
					,error:function(){
						alert("이메일 확인 중에 문제가 발생했어요!\n다시 시도해 주세요.");
					}
				})
			});
			
			$("#verifCodeSubmitBtn").on("click",function(){
				var verifCode = $("#codeInput").val();
				if(verifCode=="" || timer <= 0){
					return;
				}else{
					$.ajax({
						url:"/rest/emailVerify/verify"
						,type:"post"
						,data:{
							"email":emailInput
							,"code":verifCode
						}
						,success:function(data){
							if(data.ifMatched){
								$("#confirmVerifModal").click();
								$(".emailVerifArea").hide();
								$("#emailVeriSuccess").show();
								clearInterval(timerSection);
								ifCodeVerified=true;
							}else{
								$("#wrongVerifModal").click();
							}
						}
						,error:function(){
							alert("인증 확인 중 오류가 발생하했어요.");
						}
					})
				}
			})
			
			$("#pwInput").on("keyup",function(){
				pwInput = $("#pwInput").val();
				if(pwRegCheck(pwInput)){
					$("#pwFormatOkNoti").show();
					$("#pwFormatErrorNoti").hide();
					pwDoublecheckInput = "";
					$("#pwDoublecheckInput").val("");
				} else{
					$("#pwFormatOkNoti").hide();
					$("#pwFormatErrorNoti").show();
					pwDoublecheckInput = "";
					$("#pwDoublecheckInput").val("");
				}
				if(pwInput==""){
					$("#pwFormatOkNoti").hide();
					$("#pwFormatErrorNoti").hide();	
				}
			})
			
			$("#pwDoublecheckInput").on("keyup",function(){
				pwDoublecheckInput = $("#pwDoublecheckInput").val();
				if(pwDoublecheckInput == pwInput && pwRegCheck(pwInput)){
					$("#pwDoublecheckOkNoti").show();
					$("#pwDoublecheckErrorNoti").hide();
				} else{
					$("#pwDoublecheckOkNoti").hide();
					$("#pwDoublecheckErrorNoti").show();
				}
			})
			
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
					}
				}
			})
			
			$("#finalBtn").on("click",function(){
				location.href="/main/predict"
			})
			
			function verifyTimer(){
				timer -= 1;
				console.log(timer);
				var timerM = Math.floor(timer / 60);
				var timerS = timer % 60;
				var returnTimer = timerM + ":" + timerS.toString().padStart(2,"0");
				$("#timerValue").text(returnTimer);
			}
			
	        function pwRegCheck(password) {
	        	var regex = /^[A-Za-z\d@$!%*?&]{8,16}$/;
				return (regex.test(password));
	        };
	        
	        function nicknameRegCheck(nickname) {
	        	var regex = /^[a-zA-Z0-9가-힣]{1,16}$/;
				return (regex.test(nickname));
	        };
			
	        function IsEmail(emailValue) {
	            var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	            if (!regex.test(emailValue)) {
	                return false;
	            }
	            else {
	                return true;
	            }
	        };
	        
		});
	</script>

</body>
</html>