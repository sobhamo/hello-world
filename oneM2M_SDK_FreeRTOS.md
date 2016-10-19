FreeRTOS (+TLS)
===

지원 사양
---
1. Main board
	+ STMicroelectronics Nucleo-64 F411RE
	![NUCLEO-F411RE.jpg](images/NUCLEO-F411RE.jpg "" "width:50%;height:50%;")
2. Ethernet board
	+ WIZnet W5500
    ![w5500_ethernet_shield.jpg](images/w5500_ethernet_shield.jpg "" "width:50%;height:50%;")
3. CPU 최소 사양
	+ 100MHz ARM 32-bit Cortex-M4 with FPU 이상
4. RAM & Flash memory 최소 사양
	+ RAM : 128KB 이상
	+ Flash memory : 512KB 이상
5. Footprint
	+ 111KB (TLS library 포함)

IDE
---
1. Atollic TrueSTUDIO for ARM
	+ http://atollic.com/resources/downloads/
2. STM32CubeMX
	+ http://www.st.com/content/st_com/en/products/development-tools/software-development-tools/stm32-software-development-tools/stm32-configurators-and-code-generators/stm32cubemx.html

Library
---
1. FreeRTOS
 1. Library download
	+ STM32CubeMX tool에서 생성된 code 사용
2. WIZnet ioLibraryEdit
 1. Library download
	+ https://github.com/Wiznet/ioLibrary_Driver
 2. 사용 code 추출
	+ 'Ethernet' directory
	+ 'Internet' directory
		+ 'DHCP', 'DNS', 'SNTP' directory 사용
 3. handling 파일 추가
	+ 'Board.h', 'W5500HardwareDriver.h', 'W5500HardwareDriver.c', 'wizSystem.h', 'wizSystem.c' 파일 추가
 4. DNS 환경 설정 변경
	+ 'Internet/DNS' directory의 'dns.h' 파일의 MAX_DOMAIN_NAME 값을 '16'에서 '32'로 변경
3. MQTT
 1. Library download
	+ https://github.com/eclipse/paho.mqtt.embedded-c
 2. 사용 code 추출
	+ 'MQTTPacket' directory
	+ 'MQTTClient-C' directory
		+ 'src' directory의 'MQTTClient.h', 'MQTTClient.c' 파일 사용
		+ 'src/FreeRTOS' directory의 'MQTTFreeRTOS.h', 'MQTTFreeRTOS.c' 파일 사용
 3. FreeRTOS 연동 수정
	+ 'MQTTFreeRTOS.h' 파일에 USE_ETHERNET_LIB define 추가
	+ 'MQTTFreeRTOS.h', 'MQTTFreeRTOS.c' 파일의 FreeRTOS+TCP library 사용 부분을 WIZnet ioLibrary 사용으로 변경
 4. TLS 지원 파일 추가
	+ 'MQTTFreeRTOSTLS.h', 'MQTTFreeRTOSTLS.c' 파일 추가
	+ 'MQTTClient.h' 파일에 '#include "MQTTFreeRTOSTLS.h"' 추가
 5. 중복 정의 제거
	+ 'MQTTClient.h' 파일의 enum returnCode 중 'SUCCESS'가 chip driver에 이미 정의돼 있음
		+ /Drivers/CMSIS/Device/ST/STM32F4xx/Include directory에 'stm32f4xx.h' 파일의 ErrorStatus enum 정의 제거(사용되는 곳이 없다.)
4. mbedtls
 1. Library download
	+ https://tls.mbed.org/
 2. 사용 code 추출
	+ 'include' directory
	+ 'library' directory
 3. config.h 변경
	+ SSL_Config.h 파일을 mbedtls library directory 아래의 include directory에 config.h 파일에 덮어씀
5. oneM2M
 1. Library import
	+ project root에 oneM2M library 추가
 2. oneM2MConfig.h 수정
	+ SPT_DEBUG_ENABLE
		define 됐을 경우 debug log 활성화 됨

Sample
---
 1. Code import
	+ 'Src' directory에 sample code 추가
 2. Configuration.h 수정
	+ MAC_ADDRESS
		사용할 mac address 지정(다른 device와 겹치면 안됨)
	+ MQTT_ENABLE_SERVER_CERT_AUTH
		0:Normal socket 사용, 1:TLS socket 사용
 3. 'main.c' 파일 수정
	+ main() 함수에 WIZnet library 초기화 등의 code 추가
	+ serial port speed 변경
		main.c 파일에서 MX_USART2_UART_Init() 함수의 huart2.Init.BaudRate = 115200; 수정
