Android SDK Sample App.
===

감지 센서 목록
---
1. Battery : temperature(℃), charge level(％)
2. Temperature(℃)
3. Humidity(％)
4. Noise(㏈)
5. GPS : latitude(˚), longitude(˚), altitude(˚)
6. Air pressure(h㎩)
7. Brightness(㏓)
8. Proximity(㎝)
9. Accelerometer : X(㎨), Y(㎨), Z(㎨)
10. Orientation : azimuth(˚), pitch(˚), roll(˚)
11. Gravity : X(㎨), Y(㎨), Z(㎨)
12. Gyroscope : X(˚), Y(˚), Z(˚)
13. Magnetic field : X(µT), Y(µT), Z(µT)
14. Step detector(detection)
15. Step count(steps)

동작 센서 목록
---
1. Buzzer : ringtone, notification, alarm
2. LED : off, red, green, blue, magenta, cyan, yellow, white
3. Camera : back, front

Project에 oneM2M SDK 연결
---
1. Project에 oneM2M 모듈 추가
	- Menu > File > Project Structure... 선택  
	<img src="images/sample_app/oneM2M_import.png">
	- Popup 좌상단의 '+' 버튼 클릭
		- <img src="images/sample_app/oneM2M_import1.png">
	- 'Import Gradle Project' 선택
		- <img src="images/sample_app/oneM2M_import2.png">
	- SDK folder에 있는 onem2m folder 선택 후 'Finish' 버튼 클릭
		- <img src="images/sample_app/oneM2M_import3.png">
2. SDK를 사용할 모듈에 onem2m 모듈 연결
	- 'Dependencies' tab에서 우측 '+' 버튼 클릭 후 'Module dependency' 선택
		- <img src="images/sample_app/oneM2M_import4.png">
	- 'onem2m' 모듈 선택 후 'OK' 버튼 클릭
		- <img src="images/sample_app/oneM2M_import5.png">
	- 'onem2m' 모듈 이 추가됐는지 확인
		- <img src="images/sample_app/oneM2M_import6.png">

Device App.
---
1. 동작 환경
	- Android OS 4.3(Jelly Bean, API Level 18) 이상
2. App. 권한 승인
	- Android OS 6.0(Marshmallow, API Level 23) 이상의 단말에서는 GPS 및 카메라 사용, Noise 센서 표시에 필요한 권한 허용 팝업이 표시된다.
		- <img src="images/sample_app/permission1.png" width="25%"> <img src="images/sample_app/permission2.png" width="25%"> <img src="images/sample_app/permission3.png" width="25%">
3. 로그인
	- <img src="images/sample_app/login.png" width="25%">
    - ThingPlug portal ID/PW를 입력한다.
    - 'Join to be a ThingPlug member'를 선택하면 Web browser에 ThingPlug portal 메인 페이지가 표시된다.
	- __서버 환경 설정__(Change Server Info.)
		- <img src="images/sample_app/device_server_setting.png" width="25%">
    	- ThingPlug portal 정보
    		- Login : 로그인 API URL
    		- Register : 디바이스 등록 API URL
    	- ThingPlug server 정보
    		- URL : ThingPlug server URL
    		- AppEUI : CSE 생성 접두어
    		- Use TLS : TLS 사용 여부
    		- TLV/TDV : 센서 데이터 포맷
    	- LOAD DEFAULT : 기본 설정값 불러오기
4. 약관 동의
	- <img src="images/sample_app/terms.png" width="25%">
	- AGREE : 위치 정보 사용 동의
5. Google account 선택
	- <img src="images/sample_app/device_google_account.png" width="25%">
    - Camera 촬영 이미지를 업로드할 Google account를 선택한다.
6. 로컬 센서 목록
	- <img src="images/sample_app/local_sensor_list.png" width="25%">
	- 동작 단말에서 지원하는 센서 목록만 표시된다.
	- 센서 켜기(ENABLED)/끄기(DISABLED) 기능 : 센서 정보 전송 및 Service App.에서 원격 조작 가능 상태 설정
	- 센서 활성화(ACTIVATED)/비활성화(DEACTIVATED) 상태 표시 : Service App.에서 센서 활성화 조작 상태 표시
	- 센서 선택 시 상태 상세 화면으로 이동한다.
7. 센서 상태 상세
	- <img src="images/sample_app/sensor_detail.png" width="25%">
    - 센서 상태값을 그래프로 표시한다.
8. 환경 설정
	- <img src="images/sample_app/device_setting.png" width="25%">
    - 센서 감지 간격(Sensor read interval, msec) : 센서에서 값을 읽어오는 간격
    - 센서 정보 전송 간격(Transfer interval, msec) : 센서 정보를 서버에 전송하는 간격
    - 센서 목록 갱신 간격(List update interval) : 센서 목록 화면을 갱신하는 간격
    - 센서 상태 그래프 갱신 간격(Graph update interval) : 센서 상태 상세 화면의 그래프를 갱신하는 간격
    - LOGOUT : ThingPlug portal/server에서 로그아웃

Servie App.
---
1. 동작 환경
	- Android OS 4.3(Jelly Bean, API Level 18) 이상
2. 로그인
	- <img src="images/sample_app/login.png" width="25%">
    - ThingPlug portal ID/PW를 입력한다.
    - 'Join to be a ThingPlug member'를 선택하면 Web browser에 ThingPlug portal 메인 페이지가 표시된다.
	- __서버 환경 설정__(Change Server Info.)
		- <img src="images/sample_app/service_server_setting.png" width="25%">
    	- ThingPlug portal 정보
    		- Login : 로그인 API URL
    		- Search : 디바이스 목록 조회 API URL
    	- ThingPlug server 정보
    		- URL : ThingPlug server URL
    		- AppEUI : CSE 생성 접두어
    		- Use TLS : TLS 사용 여부
    		- TLV/TDV : 센서 데이터 포맷
    	- LOAD DEFAULT : 기본 설정값 불러오기
3. 디바이스 선택
	- <img src="images/sample_app/device_list.png" width="25%">
	- ThingPlug portal에 등록된 디바이스 목록을 표시한다.
4. Google account 선택
	- <img src="images/sample_app/service_google_account.png" width="25%">
    - Device App.에서 Camera로 촬영한 이미지를 다운로드할 Google account를 선택한다.
5. 원격 센서 목록
	- <img src="images/sample_app/remote_sensor_list.png" width="25%">
	- 전체 센서 목록이 나열되며, Device App.에서 미지원 하거나 꺼진(DISABLED) 센서의 경우 비활성화 된 상태로 표시된다.
	- 센서 활성화(ACTIVATED)/비활성화(DEACTIVATED) 기능 : Device App.의 센서 활성화 상태 설정
	- 센서 선택 시 상태 상세 화면으로 이동한다.
6. 센서 상태 상세
	- <img src="images/sample_app/sensor_detail.png" width="25%">
    - 센서 상태값을 그래프로 표시한다.
7. 환경 설정
	- <img src="images/sample_app/service_setting.png" width="25%">
    - 센서 정보 조회 간격(Transfer interval, msec) : 센서 정보를 서버에서 조회하는 간격
    - 센서 목록 갱신 간격(List update interval) : 센서 목록 화면을 갱신하는 간격
    - 센서 상태 그래프 갱신 간격(Graph update interval) : 센서 상태 상세 화면의 그래프를 갱신하는 간격
    - LOGOUT : ThingPlug portal/server에서 로그아웃
