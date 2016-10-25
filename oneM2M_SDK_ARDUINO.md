mbed (+TLS)
===

지원 사양
---
1. Main board
	+ Arduino 101 (USA ONLY) & Genuino 101 (OUTSIDE USA)
	![NUCLEO-F411RE.jpg](images/ "" "width:50%;")
		+ CPU : 32MHz Intel Curie
		+ RAM : 24KB
		+ Flash memory : 196KB
2. Ethernet board
	+ Arduino Ethernet Shield R3 
    ![w5500_ethernet_shield.jpg](images/ "" "width:50%;")
3. Footprint
	+ Binary file size : 93KB 
	+ Heap memory usage : 21KB

Source Tree
---
+ Arduino (project root)
	+ examples
		+ __ArduinoMiddleware__ (Middleware)
			+ ArduinoMiddleware.ino ( Arduino Sketch File )
			+ src
			  + __MA__ (ManagementAgent)
			  + __SRA__ (ServiceReadyAgent)
			  + __SMA__ (SensorManagementAgent)
		+ __SDKVerification__ (SDK example)
			+ SDKVerfication.ino (sdk test source)
			+ Configuration.h (config file)
	+ src (oneM2M SDK source)
		+ __MQTT__ (Arduino MQTT Library)
		+ include (header files)
		+ net (MQTT interface source)
		+ __oneM2M_V1__ (oneM2M v1 sdk source)
		+ __oneM2M_V1_12__ (oneM2M v1.12 sdk source)
	+ libraries (Essential Arduino Libraries)
		+ Time (Arduino Time library)
		+ LCD (Arduino LCD library)
	+ docs (SDK Documents) <now empty>

Project build
===

IDE 설정
---
1. IDE homepage
	+ https://www.arduino.cc/
	+ 사진
2. Download page
	+ Windowns
	+ Mac
	+ Linux
	+ 사진
3. Download & Install
	+ next
	+ 사진
4. Build & Monitoring
	+ 컴파일
	+ 업로드
	+ 모니터로 확인
5. Installing Additional Arduino Libraries
    + https://www.arduino.cc/en/Guide/Libraries
    + 라이브러리 설치에 대한 자세한 설명은 공식사이트를 참고한다.

SDK 설치하기
---
1. oneM2M 라이브러리를 다운로드 받는다.
2. 압축을 해제한다.
3. IDE가 설치되면, 각 피씨환경의 홈 폴더에 Arduino 폴더가 생긴다.
4. Arduino/libraries 에 oneM2M SDK 폴더를 복사한다.
5. oneM2M/libraries 에 있는 모든 라이브러리를 Arduino/libraries 폴더로 복사한다.
6. SDK 설치가 완료되었다.

SDK 빌드하기
---
1. Arduino IDE를 실행시킨다.
2. 상단 메뉴에 스케치->컴파일을 선택한다.
3. 상단 메뉴에 툴->보드, 포트를 Genuino 101/연결된 포트로 설정한다.
4. 상단 메뉴에 스케치-> 업로드를 선택한다.
5. 성공시 아래 콘솔창에 메시지가 출력된다
_______________________________________________
스케치는 프로그램 저장 공간 81,688 바이트(52%)를 사용. 최대 155,648 바이트.

Starting download script...

SUCCESS: Sketch will execute in about 5 seconds.
_______________________________________________
SDK 예제 코드 실행하기
---
1. Arduino IDE를 실행한다.
2. 상단 메뉴에 파일->예제->oneM2M->SDKVerification 선택한다.
3. 상단 메뉴에 스케치->컴파일을 선택한다.
4. 상단 메뉴에 툴->보드, 포트를 Genuino 101/연결된 포트로 설정한다.
5. 상단 메뉴에 스케치-> 업로드를 선택한다.
6. 업로드가 완료되면 상단 메뉴에 툴->시리얼 모니터를 선택한다.
사진
7. oneM2M 동작 테스트가 시작된다.
8. 결과

SDK를 이용한 ArduinoMiddleware 실행하기
---
1. Arduino IDE를 실행한다.
2. 상단 메뉴에 파일->예제->oneM2M->ArduinoMiddleware 선택한다.
3. 상단 메뉴에 스케치->컴파일을 선택한다.
4. 상단 메뉴에 툴->보드, 포트를 Genuino 101/연결된 포트로 설정한다.
5. 상단 메뉴에 스케치-> 업로드를 선택한다.
6. 업로드가 완료되면 상단 메뉴에 툴->시리얼 모니터를 선택한다.
사진
7. oneM2M 동작 테스트가 시작된다.
8. 결과
