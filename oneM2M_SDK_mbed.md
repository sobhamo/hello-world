mbed (+TLS)
===

지원 사양
---
1. Main board
	+ STMicroelectronics Nucleo-64 F411RE
	![NUCLEO-F411RE.jpg](images/NUCLEO-F411RE.jpg "" "width:50%;")
2. Ethernet board
	+ WIZnet W5500
    ![w5500_ethernet_shield.jpg](images/w5500_ethernet_shield.jpg "" "width:50%;")
3. CPU 최소 사양
	+ 100MHz ARM 32-bit Cortex-M4 with FPU 이상
4. RAM & Flash memory 최소 사양
	+ RAM : 128KB 이상
	+ Flash memory : 512KB 이상
5. Footprint
	+ 110KB (TLS library 포함)

IDE
---
1. mbed compiler
	+ https://developer.mbed.org

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
	+ SPT_DEBUG_ENABLE
		define 됐을 경우 debug log 활성화 됨

Sample
---
 1. Code import
	+ sample code를 zip 파일로 압축
	+ Import Wizard의 Upload tab에서 화면 아래의 '파일 선택' 버튼을 눌러 zip 파일 선택 후 'Import!' 버튼 클릭
	+ import 된 library 파일을 project root에 위치 시킴
	+ MiddlewareMini.cpp 파일 삭제
 2. Configuration.h 수정
	+ MAC_ADDRESS
		사용할 mac address 지정(다른 device와 겹치면 안됨)
	+ MQTT_ENABLE_SERVER_CERT_AUTH
		0:Normal socket 사용, 1:TLS socket 사용
 3. serial port speed 변경
	+ SDKVerification.cpp 파일에서 main() 함수의 pc.baud(115200); 수정
