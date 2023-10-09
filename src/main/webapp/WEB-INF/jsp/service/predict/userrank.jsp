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
	<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=fclyxujz52"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
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
		<jsp:include page="/WEB-INF/jsp/global/header.jsp"/>
		
		<section id="content" class="container border-top">
			<div style="height:5px"></div>
			
			<div class="card">
				<div class="f-content m-3">지역별 순위</div>
				<div class="bg-secondary m-3">
					<div id="map" style="width:100%;height:50vh;"></div>
				</div>
			</div>
			<div>
				<div id="toggleArea" class="d-flex f-content m-3 bg-dark text-center text-white" style="border-radius:50px;">
					<div id="allRegionToggle" class="toggleBtn col-6 my-3 btn" style="border-right:solid; text-decoration:underline;">전국 순위</div>
					<div id="selectedRegionToggle" class="toggleBtn col-6 my-3 text-muted btn">지역 순위</div>
				</div>
			</div>
			<div id="changeLine"></div>
			<jsp:include page="/WEB-INF/jsp/service/predict/userrank-changearea.jsp"/>
			
			<div style="height:5px"></div>
		</section>

	</div>
	
	<jsp:include page="/WEB-INF/jsp/global/footer.jsp"/>

	<script src="https://code.jquery.com/jquery-3.6.4.min.js" integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@0.7.0"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	
	<script>
		$(document).ready(function(){
			// 새 정보 불러오기 및 채점 controller 호출
			$.ajax({
				url:"/rest/observation/newList/refresh"
				,type:"get"
			})
			
			$(".toggleBtn").on("click",function(){
				
				var goTo = $(this).prop("id");
				let UID = ${UID};
				
				if(goTo == "allRegionToggle"){
					$.ajax({
						url:"/main/userRank/toggleCame"
						,type:"get"
						,success:function(data){
							$(".changable").remove();
							$("#changeLine").append(data);
							$("#selectedRegionToggle").addClass("text-muted");
							$("#selectedRegionToggle").prop("style","");
							$("#allRegionToggle").prop("style","border-right:solid; text-decoration:underline;");
							$("#allRegionToggle").removeClass("text-muted");
						}
					})
				} else if(goTo == "selectedRegionToggle"){
					$.ajax({
						url:"/main/userRank/toggleCame"
						,data:{"UID":UID}
						,type:"get"
						,success:function(data){
							$(".changable").remove();
							$("#changeLine").append(data);
							$("#allRegionToggle").prop("style","");
							$("#selectedRegionToggle").prop("style","border-left:solid; text-decoration:underline;");
							$("#allRegionToggle").addClass("text-muted");
							$("#selectedRegionToggle").removeClass("text-muted");
						}
					})
					
				}
			})
			
			
			
		});
	</script>
	
   <script>
       
        var mapData = [];
    
        $.ajax({
        	url:"/rest/rank/map/byRegion"
        	,type:"get"
        	,success:function(data){
        		mapData = data;
        		initMap();
        	}
        })
        
        function initMap(){
            var areaArr = mapData;
    
            var map = new naver.maps.Map('map', {
                zoom: 6,
                mapTypeId: 'normal',
                center: new naver.maps.LatLng(36.34, 127.77)
            });
    
            var markersArray = [];
            var infoWindowsArray = [];
    
            for (var i = 0; i < areaArr.length; i++) {
                var marker = new naver.maps.Marker({
                    position: new naver.maps.LatLng(areaArr[i].lat, areaArr[i].lng),
                    map: map,
                    title: areaArr[i].regionName,
                    icon: {
                        content: areaArr[i].markerSVG,
                        size: new naver.maps.Size(38, 58),
                        anchor: new naver.maps.Point(19, 58),
                    },
                    draggable: false
                });
    
                var contentString = [
                    '<div id=areaArr[i].predictRGID class="f-small mx-1">'
                    ,   '<div class="mt-1 f-content text-center" style="text-decoration: underline;">',areaArr[i].regionName,'<span class="font-weight-bold"> #',areaArr[i].regionRank,'</span>','</div>'
                    ,   '<div>전체 예측 <span class="font-weight-bold">',areaArr[i].regionTotal,'회</span>','</div>'
                    ,   '<div>기온 예측 정확률 <span class="font-weight-bold">',areaArr[i].decimalFormatTempChance,'%</span>','</div>'
                    ,   '<div>강수 예측 정확률 <span  class="font-weight-bold">',areaArr[i].decimalFormatRainChance,'%</span>','</div>'
                    ,'</div>'
                ].join('');
    
                var infowindow = new naver.maps.InfoWindow({
                    content: contentString,
                    maxWidth: 200,
                    backgroundColor: "#eee",
                    borderColor: "#007BFF",
                    borderWidth: 1,
                    anchorSize: new naver.maps.Size(30, 30),
                    anchorSkew: true,
                    anchorColor: "#eee",
                    pixelOffset: new naver.maps.Point(0, 20)
                });
    
                markersArray.push(marker);
                infoWindowsArray.push(infowindow);
    
                (function(marker, infowindow) {
                    marker.addListener("click", function() {
                        if (infowindow.getMap()) {
                            infowindow.close();
                        } else {
                            infowindow.open(map, marker);
                        }
                    });
                })(marker, infowindow);
            }
        }
    </script>




	

</body>
</html>