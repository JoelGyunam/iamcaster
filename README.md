# iamcaster

### 서비스 URL : http://iamcaster.com
### 개발자 contact : gynpark@gmail.com
### notion : https://www.notion.so/614d0ddb7bd8415687a08064d6ff3f5a
### ① 서비스 개요

> **나도캐스터 란?:** 
최근 이상 기후 변화로 인해, 예상치 못한 비 혹은 폭염등에 대비하지 못하고, 피해를 입는 사람들이 증가하고 있습니다.
>
> 이에, **날씨에 재미요소**를 가미하여, 서비스 사용자들이 날씨에 보다 관심을 갖도록 하는 것이 본 서비스의 핵심입니다.
> 
>**날씨를 맞추는 재미**와, 지역·사용자 별 순위에서 **높은 순위를 얻고자 하는 동기부여**를 통해, 강수량, 기온 등, 날씨 수치에 대한 감각을 함양시키고, 기상청의 날씨예보에 관심을 증가시키고자 합니다.
>

### ② 핵심 기능

> **내일 날씨 (기온 및 강수) 를 맞추는 기능.**
> 

![image](https://github.com/JoelGyunam/iamcaster/assets/49266474/bceda0d5-d12d-4bba-8423-2fc1c5f883ac)

> **채점 된 결과를 기반으로 통계 및 순위정보 제공.**

### ③ 사용한 기술
| 서버사이드          | view           | 라이브러리             | 형상관리   |
|:-------------------:|:--------------:|:----------------------:|:--------:|
| java 17             | JSP            | 기상청API              | Git            |
| Spring Framework    | javascript     | 카카오로그인           | GitHub Actions  |
| mySQL                | jQuery         | 네이버지도              | Docker        |
| JPA, MyBatis        | html/css       | jackson 라이브러리     |                |
| gradle              |                | webflux                |               |
| Tomcat               |                | springboot-mail        |                |
| AWS EC2, RDS, Route53|                | chart-js               |                |

 - 아키텍쳐

![image](https://github.com/JoelGyunam/iamcaster/assets/49266474/5c7a99d3-54da-4258-864d-7bf042cdcfb9)

### ⑤ 데이터베이스 설계 및 구조

> **10개 테이블 구성**

 - ERD다이어그램

![image](https://github.com/JoelGyunam/iamcaster/assets/49266474/4824e54c-b195-4a6c-ae0a-2d003e150a33)

 - 테이블 명세

| 테이블 명 | 내용 |
| --- | --- |
| userInfo | 계정이메일, 비밀번호, salt, 활동지역ID, 닉네임ID 등 기본정보를 포함하는 테이블 |
| userNickname | 닉네임ID 기반, 닉네임을 포함하는 테이블. (닉네임 중복을 피할 수 있도록 별도 테이블 구성됨.) |
| region | 지역의 날씨 예측을 위한 지역 메인 테이블 |
| SFCRegion | 단기예측정보를 제공하는 관측소정보 테이블 (기상청 단기예보지역API 를 기반으로 구성) |
| OBVRegion | 육상관측정보를 제공하는 관측소정보 테이블 (기상청 육상관측지역API를 기반으로 구성) |
| shortForecast | 사용자의 날씨퀴즈 제공 시 힌트로 제공되는 기상청 단기예측정보 테이블 |
| observation | 사용자의 날씨퀴즈 정답/오답 확인을 위한 기상청 육상관측정보 테이블 |
| userPredict | 사용자의 날씨퀴즈 응답값 저장 테이블 |
| emailVerification | 이메일 인증코드를 저장하는 테이블 |
| notice | 공지사항을 저장하는 테이블 |

# ⑥ 기능 명세

> **[1. 회원가입 및 로그인 기능](https://www.notion.so/614d0ddb7bd8415687a08064d6ff3f5a?pvs=21)**
> 

1-1. 이메일 및 비밀번호 기반 회원가입

1-2. session 통한 로그인 관리.

1-3. 인증코드 이메일 발송

1-4. 카카오 로그인

1-5. 비밀번호 암호화 (sha-256 및 salt 사용)

1-6. 이용약 (필수) 및 혜택 수신동의(선택)

> [2. **활동지역 세팅 (기상청 API 기준)**](https://www.notion.so/614d0ddb7bd8415687a08064d6ff3f5a?pvs=21)
> 

2-1. 관측소 정보 2개 API 정보 매핑 (단기예보 관측소 & 육상관측 관측소)

2-2. 단기예보API 파싱 및 인서트

2-3. 육상관측API 파싱 및 인서트

2-4. 단기예보 관측소&육상관측 관측소 중첩되는 86개 관측소를 활동지역으로 설정

> [3. **사용자로부터 날씨퀴즈 정보 획득**](https://www.notion.so/614d0ddb7bd8415687a08064d6ff3f5a?pvs=21)
> 

3-1. 퀴즈 제출 여부에 따른 insert / update

3-2. 퀴즈를 제출한 지역의 기상청 예보정보 제공

3-3. 지역 별 고득점자의 퀴즈 정답정보 제공

> [4. **날씨퀴즈 채점**](https://www.notion.so/614d0ddb7bd8415687a08064d6ff3f5a?pvs=21)
> 

4-1. 기상청 육상관측 정보 파싱 및 insert

4-2. 날씨퀴즈 채점 : 기상청 관측데이터와 각각 10% 이내의 응답 제출 시 정답.

> [5. **chart.js 통한 통계 시각화**](https://www.notion.so/614d0ddb7bd8415687a08064d6ff3f5a?pvs=21)
> 

5-1. chart.js 의 도넛차트, 바차트 활용, 로그인 한 사용자의 전체예측 성공률, 기온예측 성공률, 강수예측 성공률 시각화.

> [6. **네이버 web dynamic map 통한 순위 시각화**](https://www.notion.so/614d0ddb7bd8415687a08064d6ff3f5a?pvs=21)
> 

6-1. 전국, 지역 단위 별 성공률 rank 부여.

6-2. 부여 된 rank 기준, 지역의 위도/경도값 통해 네이버 Web dynamic Map 내 POI 출력.
