<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
	<title>나도캐스터, 로그인</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link rel="stylesheet" href="/static/css/style.css" type="text/css">
</head>
<body>
	<div id="wrap">
		<div class="my-5">
			<div class="text-center f-content">모두가 기상국장이 되는 곳</div>
			<div class="text-center f-title font-weight-bold">나도 캐스터</div>
		</div>
		<hr>
		<div class="mt-5">
			<div class="m-3">
				<label class="f-content font-weight-bold">이메일 주소</label>
				<input type="text" class="form-control">
			</div>
			<div class="m-3">
				<label class="f-content font-weight-bold">비밀번호</label>
				<input type="text" class="form-control">
			</div>
			<div class="pt-3">
				<div class="m-3 d-flex justify-content-center">
					<button class="btn btn-success col-6 text-white">로그인</button>
				</div>
				<div class="f-small text-center text-dark">비밀번호를 잊으셨나요?</div>
			</div>
		</div>
		<hr>
		<div class="m-3">
			<div class="f-content font-weight-bold">나도캐스터가 처음이신가요?</div>
			<div class="d-flex justify-content-center mt-4">
				<button class="btn btn-primary">5초만에 나도캐스터 등록하기</button>
			</div>
		</div>



<%-- 모달 스크립트 --%>

		<!-- Button trigger modal -->
		<button type="button" class="btn btn-primary d-none" data-toggle="modal" data-target="#loginDenied">
		  Launch demo modal
		</button>
		
		<!-- Modal -->
		<div class="modal fade px-5" id="loginDenied" tabindex="-1" aria-labelledby="loginDenied" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered">
		    <div class="modal-content">
		      <div class="modal-body text-center f-content">
		        <div><b>잘못된 로그인 정보입니다.</b></div>
		        <div>이메일주소 혹은 비밀번호를 확인해 주세요.</div>
		      </div>
		      <div class="modal-footer d-flex justify-content-center">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">확인</button>
		      </div>
		    </div>
		  </div>
		</div>
		
	</div>
	
	<jsp:include page="/WEB-INF/jsp/global/footer.jsp"/>

	<script src="https://code.jquery.com/jquery-3.6.4.min.js" integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>


</body>
</html>