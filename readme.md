
## Logo
<img src = "./PPT/logo2.png" width="">

## 프로젝트 동영상
<img src = "./PPT/video.png" width="">

## 개발과제[지정과제]
생활관 병사 스마트폰 비대면 반납 앱 [제안처 : 군수전산소]


## Phonevar란?
장병들의 주도적인 스마트폰 사용 및 관리를 자동으로 도와주는 서비스 앱입니다.


## 아이디어
스마트폰의 끄고 켜짐을 기준으로 사용기록을 기록해 사용자가 정상적인 사용을 하고 있는지 비정상적인 사용을 하고 있는지 관리자가 사용기록을 확인하여 사용위한 사용자를 식별하는 아이디어입니다.


## 프로젝트 기능 설계
프로젝트는 크게 3가지 구분으로 개발 되었고, 각 구분은 다음과 같은 기능이 있습니다.
* 사용자 앱 / 관리자 앱
  * 사용자 등록
  * 사용기록 작성
	  * 기준 1
	  ![Alt text](/PPT/1.png)
	  * 기준 2
	  ![Alt text](/PPT/2.png)
	  * 기준 3
	  ![Alt text](/PPT/3.png)
  * 스마트폰 제출 푸시 알림
  * 사용기록 서버 전송

* 서버
  * 사용기록 검증
  * 관리자 푸시알림

* 웹페이지
  * 사용기록 열람
  * 스마트폰 사용시간 수정
  * 사용자상태 변경(정상/비정상)

## 기술 스택 (Technique Used)
* Server(back-end)
  * Spring Framework 4
  * MySQL 5.6
  * Firebase
  * Swagger 2
  * Mybatis
  * OkHttp 3
  * JWT
  * Jackson 2.11

* front-end
  * javascript
  * html5
  * jquery-3.5.1
  * tablesorter-2.9.1
 
* App
  * Intent Service
  * firebase cloud messaging

## 흐름도
![Alt text](/PPT/흐름도.png)


## 장점
 * 경제성<br>
반납을 하기위해 앱만 설치하면되고, 부가적인 반납시스템 구축 비용도 없다. 인원추가에 대한 물리적제한과 비용도 없다. 또한 유지보수 수요가 적으며, 이에 대한 부가적인 비용도 없습니다.   

 * 즉각성<br>
 사용자에게 사용시간 준수를 위해 푸시알림을 통해 미리방지하며,만약 서버에서 비정상적인 사용이 감지 될 경우, 즉시 관리자앱이 설치된 스마트폰으로 알림이 가기 때문에 빠른조치를 취할 수 있습니다.
 
 * 보완성<br>
 스마트폰에 직접 설치되는 앱서비스이기 때문에 다른 스마트폰을 이용해 반납하는 반납위조의 행위를 막을 수 있다. 자동으로 스마트폰의 끄고 켜짐이 기록되기 때문에 기록위조도 불가능합니다.

## 개선 방향
* 데이터가 없는 사용자 확인 : <br>
앱과 서버 간 통신이 가능한지 확인하기 위해서 하루에 한번씩 송수신 상태를 확인하여 실행현황을 최신화합니다.

* 출타자 관련 사용시간 관리 : <br>
출타 시 관리자 웹을 통해서 사용시간 제한이 해제되거나 수정 될 수 있도록 개선합니다.

* 강제종료 방지 : <br>
국방부 모바일앱처럼 사용자가 끄지 못하도록 해당 앱과 연계합니다.

* 시간조작 방지 : <br>
사용자가 시간조작을 하지 못하도록 서버시간으로 동기화합니다.

* 아이폰 사용자 : <br>
아이폰용도 BackgroundTasks Framework(App Refresh Task)를 이용해 추후 개발 가능합니다.

## 발전 가능성
 * 1차적 시장 <br>
 지정과제를 낸 군수전산소를 주 타겟으로 하여 해당 서비스의 보안성과 보편성을 더욱 높이기 위해 기술개선을 합니다.
 * 2차적 시장 <br>
해당 서비스의 기록작성 기준을 스마트폰 전원 on/off가 아닌 블루투스 비콘의 신호여부로 전환하여 기숙사, 도서관의 인원출입 관리나 대중교통의 승하차 승객 관리서비스 앱으로 발전할 수 있다고 전망하고 있습니다.
## 컴퓨터 구성 / 필수 조건 안내 (Prerequisites)
* ECMAScript 6 지원 브라우저 사용
* 권장: Google Chrome 버젼 77 이상


## 설치 안내 (Installation Process)
```bash
$ git clone git주소
$ 추후 추가
```
## 프로젝트 사용법 (Getting Started)

## 개발일정
![Alt text](/PPT/일정.png)





## 팀 정보 (Team Information)
- kim dae yu (dea0323@gmail.com), Github Id: kimdaeyu
- jeong jong woo (knight7024@naver.com), Githun Id: knight7024
- junBeul (yhs06280@gmail.com), Githun Id: JunBeul


## 저작권 및 사용권 정보 (Copyleft / End User License)
 * [MIT](https://github.com/osam2020-WEB/Sample-ProjectName-TeamName/blob/master/license.md)
 

<img src = "./PPT/logo.png" width="100px">
더 자세한 개발현황은 [https://www.notion.so/inhaunivkdy/2020-bctp-668f236ca2b142698428b55c9dd8812e](https://www.notion.so/inhaunivkdy/2020-bctp-668f236ca2b142698428b55c9dd8812e)에서 확인하시면 됩니다.
