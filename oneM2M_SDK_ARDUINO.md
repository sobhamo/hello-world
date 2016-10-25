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
	+ Binary(HEX) file size : 225KB 
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
2. Download page
	+ Windowns
	+ Mac
	+ Linux
![arduino download](path "" "width:50%;")
3. Download & Install
	+ next
4. Project 생성
	+ Template을 'Empty Program'으로 선택하고 Program Name에 project명 입력
	![mbed_create_new_program.png](images/mbed/mbed_create_new_program.png "" "width:50%;")

Library import
---
1. mbed
 1. Library import
	+ Import Wizard의 Library tab에서 'mbed' 검색
	+ Author가 'mbed official'인 mbed library import
2. project root에 'Library' directory 생성
3. WIZnet_Library
 1. Library import
	+ Import Wizard의 Library tab에서 'wiznet' 검색
	+ Author가 'Team WIZnet'인 WIZnet_Library library를 Library directory 하위에 import
 2. MQTT library와 중복되는 code 수정
	+ 'WIZnet' directory의 W5500.h 파일의 enum Command의 각 항목 앞에 __CMD___를 붙임 (ex. OPEN -> CMD_OPEN)
	+ 위의 enum Command를 사용하는 code 부분 모두 수정
4. MQTT
 1. Library import
	+ Import Wizard의 Library tab에서 'mqtt' 검색
	+ Author가 'Team MQTT'인 MQTT library를 Library directory 하위에 import
 2. Extend code 추가
	+ Source Tree의 'Library/MQTT/Extend' directory를 zip 파일로 압축
	+ Import Wizard의 Upload tab에서 화면 아래의 '파일 선택' 버튼을 눌러 zip 파일 선택 후 'Import!' 버튼 클릭
	+ import 된 Extend directory를 MQTT directory의 하위로 이동
5. mbedtls
 1. Library download
	+ https://tls.mbed.org/
 2. Library import
	+ mbedtls library에서 'include', 'library' directory를 'mbedtls_2_3_0.zip' 파일로 압축
	+ Import Wizard의 Upload tab에서 화면 아래의 '파일 선택' 버튼을 눌러 zip 파일 선택 후 'Import!' 버튼 클릭
	+ import 된 library 파일을 Library directory 하위에 위치시킴
 3. config.h 변경
	+ Source Tree의 '/Library/mbedtls_2_3_0/SSL_Config.h' 파일을 mbedtls library directory 아래의 include directory에 있는 config.h 파일에 덮어씀
6. oneM2M
 1. Library import
	+ Source Tree의 '/Library/oneM2M' directory를 zip 파일로 압축
	+ Import Wizard의 Upload tab에서 화면 아래의 '파일 선택' 버튼을 눌러 zip 파일 선택 후 'Import!' 버튼 클릭
	+ import 된 library 파일을 Library directory 하위에 위치시킴
 2. oneM2MConfig.h 수정
	+ __DEBUG_ENABLE__ : define 됐을 경우 debug log 활성화 됨

Sample code import
---
1. Code import
	+ Source Tree의 'Src' directory를 zip 파일로 압축
	+ Import Wizard의 Upload tab에서 화면 아래의 '파일 선택' 버튼을 눌러 zip 파일 선택 후 'Import!' 버튼 클릭
	+ import 된 sample 파일을 project root에 위치시킴
2. '/Src/Configuration.h' 수정
	+ __MAC_ADDRESS__ : 사용할 mac address 지정(다른 device와 겹치면 안됨)
	+ __ONEM2M_V1_12__ : define 됐을 경우 oneM2M v1.12가 활성화 됨
	+ __MQTT_ENABLE_SERVER_CERT_AUTH__ : 0(Normal socket 사용), 1(TLS socket 사용)
	+ __ACCOUNT_USER__ : ThingPlug 계정의 UKEY
	+ __ACCOUNT_PASSWORD__ : ThingPlug 계정의 Password
	+ __ONEM2M_NODEID__ : ThingPlug 계정에 등록된 Device ID
3. '/Src/oneM2M_main.cpp' 수정
 1. serial port speed 변경
	+ SDKVerification.cpp 파일에서 main() 함수의 pc.baud(115200); 수정
 2. 실행 대상 변경
	1. SDK verification을 실행할 경우
		+ main() function 내의 SDKVerificationMain(); 활성화
	2. MA를 실행할 경우
		+ main() function 내의 MARun(); 활성화

Build & Run
---
1. Build
	+ 화면 상단의 'Compile' 버튼 클릭
	![mbed_compile.png](images/mbed/mbed_compile.png "" "width:50%;")
2. Run
	+ build 후 다운로드 된 .bin 파일을 윈도우 탐색기에서 'NODE_F411RE'로 연결된 device drive에 복사
	![mbed_run.png](images/mbed/mbed_run.png "" "width:50%;")
3. 실행 로그 확인
	+ Serial 통신 프로그램(ex. Putty, Tera Term 등)을 사용해서 프로그램 로그 확인
	(※ sample code의 oneM2M_main.cpp 파일에서 지정한 speed와 맞춰 환경을 설정해야 글자가 깨져 보이지 않음)
	![serial_log.png](images/serial_log.png "" "width:50%;")
