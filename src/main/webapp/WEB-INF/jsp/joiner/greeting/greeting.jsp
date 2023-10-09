<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
	<title>나도캐스터, 모두가 기상국장이 되는 곳</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link rel="stylesheet" href="/static/css/style.css" type="text/css">

<!-- Google tag (gtag.js) -->
<script async src="https://www.googletagmanager.com/gtag/js?id=G-XSSQ6SECB8"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'G-XSSQ6SECB8');
</script>

</head>
<body>
	<div id="wrap">
		<div class="my-5 pt-5">
			<div class="text-center f-content">모두가 기상국장이 되는 곳</div>
			<div class="text-center f-title font-weight-bold">나도 캐스터</div>
		</div>
		<hr>
		<div class="f-content text-center mt-5 mx-2">
			<div>갑작스런 비, 폭염 등으로</div>
			<div>날씨 피해가 발생되고 있어요!</div>
			<div>날씨가 좀 더 친숙해 지고, 재밌어진다면</div>
			<div>미리 대비할 수 있을까요?</div>
			<br>
			<div>비가 와도, 바람이 불어도</div>
			<div>매일매일 내일의 날씨가 기다려지는</div>
			<div>여기는 <b>나도캐스터</b> 입니다.</div>
		</div>
		<div class="d-flex justify-content-center mt-5 pt-5 mx-2">
			<button id="loginBtn" class="btn btn-primary col-6">날씨 맞추러 가기</button>
		</div>
	</div>
	
	<jsp:include page="/WEB-INF/jsp/global/footer.jsp"/>

	<script src="https://code.jquery.com/jquery-3.6.4.min.js" integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	<script>
		$(document).ready(function(){
			$("#loginBtn").on("click",function(){
				location.href = "/main/login";
			});
		});
	</script>
</body>
</html>