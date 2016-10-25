mbed (+TLS)
===

지원 사양
---
1. Main board
	+ STMicroelectronics Nucleo-64 F411RE
	![NUCLEO-F411RE.jpg](images/NUCLEO-F411RE.jpg "" "width:50%;")
		+ CPU : 100MHz ARM 32-bit Cortex-M4 with FPU
		+ RAM : 128KB
		+ Flash memory : 512KB
2. Ethernet board
	+ WIZnet W5500
    ![w5500_ethernet_shield.jpg](images/w5500_ethernet_shield.jpg "" "width:50%;")
3. Footprint
	+ Binary file size : 110KB (TLS library 포함)
	+ Heap memory usage : 72KB (heap 전체 size를 128KB로 보고 main function 종료 직전 남아있는 heap을 malloc으로 측정)

IDE
---
1. IDE homepage
	+ https://developer.mbed.org
2. 회원 가입 및 로그인
3. 단말 조회
	+ 오른쪽 상단의 입력상자에 __'F411RE'__ 입력
	![mbed_search_device.png](images/mbed/mbed_search_device.png "" "width:50%;")
	+ 검색 결과의 'NUCLEO-F411RE | mbed' 선택
	+ 우측의 'Add to your mbed Compiler' 클릭
	![mbed_add_device.png](images/mbed/mbed_add_device.png "" "width:50%;")
	+ 버튼이 'Open mbed Compiler'로 바뀌면 클릭
4. Project 생성
	+ Template을 'Empty Program'으로 선택하고 Program Name에 project명 입력
	![mbed_create_new_program.png](images/mbed/mbed_create_new_program.png "" "width:50%;")

Directory
---
+ __mbed__ (project root)
	+ __Library__ (libraries)
		+ __mbedtls_2_3_0__ (mbed TLS library)
			+ __include__ (library headers)
			+ __library__ (TLS library source)
		+ __MQTT__ (mqtt library)
			+ __Extend__ (mqtt library 확장, TLS library 및 WIZnet socket 연결)
			+ __FP__
			+ __MQTTPacket__ (MQTT packet 처리 source)
		+ __oneM2M__ (oneM2M library)
			+ __include__ (library headers)
			+ __src__ (oneM2M library source)
				+ __net__ (MQTT handler)
				+ __oneM2M_V1__ (ThingPlug oneM2M v1 API)
				+ __oneM2M_V1_12__ (ThingPlug oneM2M v1.12 API)
		+ __WIZnet_Library__ (WIZnet library)
			+ __WIZnetInterface__ (WIZnet board interface)
				+ __DHCPClient__ (DHCP 통신 처리)
				+ __DNSClient__ (DNS 통신 처리)
				+ __Socket__ (socket source)
				+ __WIZnet__ (WIZnet driver)
	+ __mbed__ (mbed source)
		+ __TARGET_NUCLEO_F411RE__ (Nucleo F411RE driver)
	+ __Src__ (test sample)
		+ __MA__ (test MA module)
		+ __SMA__ (test SMA module)
		+ __SRA__ (test SRA module)

Library
---
1. mbed
 1. Library import
	+ Import Wizard의 Library tab에서 'mbed' 검색
	+ Author가 'mbed official'인 mbed library import
2. WIZnet_Library
 1. Library import
	+ Import Wizard의 Library tab에서 'wiznet' 검색
	+ Author가 'Team WIZnet'인 WIZnet_Library library import
3. MQTT
 1. Library import
	+ Import Wizard의 Library tab에서 'mqtt' 검색
	+ Author가 'Team MQTT'인 MQTT library import
 2. Extend code 추가
	+ Extend code를 zip 파일로 압축
	+ Import Wizard의 Upload tab에서 화면 아래의 '파일 선택' 버튼을 눌러 zip 파일 선택 후 'Import!' 버튼 클릭
	+ import 된 Extend code를 MQTT directory의 하위로 이동
4. mbedtls
 1. Library download
	+ https://tls.mbed.org/
 2. Library import
	+ mbedtls library에서 'include', 'library' directory를 zip 파일로 압축
	+ Import Wizard의 Upload tab에서 화면 아래의 '파일 선택' 버튼을 눌러 zip 파일 선택 후 'Import!' 버튼 클릭
	+ import 된 library 파일을 project root에 위치 시킴
 3. config.h 변경
	+ SSL_Config.h 파일을 mbedtls library directory 아래의 include directory에 config.h 파일에 덮어씀
5. oneM2M
 1. Library import
	+ oneM2M library code를 zip 파일로 압축
	+ Import Wizard의 Upload tab에서 화면 아래의 '파일 선택' 버튼을 눌러 zip 파일 선택 후 'Import!' 버튼 클릭
	+ import 된 library 파일을 project root에 위치 시킴
 2. oneM2MConfig.h 수정
	+ __SPT_DEBUG_ENABLE__ : define 됐을 경우 debug log 활성화 됨

Sample
---
 1. Code import
	+ sample code를 zip 파일로 압축
	+ Import Wizard의 Upload tab에서 화면 아래의 '파일 선택' 버튼을 눌러 zip 파일 선택 후 'Import!' 버튼 클릭
	+ import 된 library 파일을 project root에 위치 시킴
	+ MiddlewareMini.cpp 파일 삭제
 2. Configuration.h 수정
	+ __MAC_ADDRESS__ : 사용할 mac address 지정(다른 device와 겹치면 안됨)
	+ __MQTT_ENABLE_SERVER_CERT_AUTH__ : 0(Normal socket 사용), 1(TLS socket 사용)
	+ __THINGPLUG_USER_NAME__ : ThingPlug 계정의 UKEY
	+ __THINGPLUG_PASSWORD__ : ThingPlug 계정의 Password
	+ __ONEM2M_NODEID__ : ThingPlug 계정에 등록된 Device ID
 3. serial port speed 변경
	+ SDKVerification.cpp 파일에서 main() 함수의 pc.baud(115200); 수정