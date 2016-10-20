FreeRTOS (+TLS)
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
	+ Binary file size : 111KB (TLS library 포함)
	+ Heap memory usage : 15KB (thread 종료 전 xPortGetMinimumEverFreeHeapSize() function으로 측정)

IDE
---
1. Atollic TrueSTUDIO for ARM
	+ http://atollic.com/resources/downloads/
	+ FreeRTOS homepage에서 추천하고 있음, Eclipse 기반의 embeded 환경 프로그램 개발 tool
2. STM32CubeMX
 1. Main board device driver와 FreeRTOS를 포함한 project templete을 생성할 수 있음
	+ download - http://www.st.com/content/st_com/en/products/development-tools/software-development-tools/stm32-software-development-tools/stm32-configurators-and-code-generators/stm32cubemx.html
	+ 설치 시 아래와 같은 오류 발생 시 command line에서 다음 명령으로 실행
			java -jar '설치파일명'
		![Java_Runtime_Environment_error.png](images/FreeRTOS/Java_Runtime_Environment_error.png "" "width:50%;")
 2. 설치 후 실행해서 New Project 선택
	![STM32CubeMX_excute.png](images/FreeRTOS/STM32CubeMX_excute.png "" "width:50%;")
	+ 실행 시 아래와 같은 오류 발생 시 command line에서 다음 명령으로 실행
			java -jar '실행파일명'
		![Java_Runtime_Environment_error2.png](images/FreeRTOS/Java_Runtime_Environment_error2.png "" "width:50%;")
 3. Board Selector 탭에서 Vendor:STMicroelectronics, Type of Board:Nucleo64, MCU Series:STM32F4로 설정한 다음 아래 오른쪽 목록에서 NUCLEO-F411RE 선택하고 아래 OK 버튼 클릭
	![STM32CubeMX_new_project.png](images/FreeRTOS/STM32CubeMX_new_project.png "" "width:50%;")
 4. 메뉴에서 Help > Install New Libraries를 선택한 후 STM32CubeF4 Releases의 Firmware Package for Family STM32F4를 선택한 다음 Install Now 버튼 클릭
	![STM32CubeMX_install_library.png](images/FreeRTOS/STM32CubeMX_install_library.png "" "width:50%;")
	+ library 설치 실패 시 수동 설치 방법
		+ download - http://www.st.com/content/st_com/en/products/embedded-software/mcus-embedded-software/stm32-embedded-software/stm32cube-embedded-software/stm32cubef4.html
		+ 위의 library 설치 팝업에서 하단의 From Local... 버튼 클릭 후 다운로드 받은 파일 선택
 5. 왼쪽 Configuration 리스트 수정
	+ '/Middlewares/FREERTOS'의 'Enabled' 체크
	+ '/Peripherals/SPI2'의 'Mode'를 'Full-Duplex Master'로 설정
	+ '/Peripherals/USART2'의 'Mode를 'Asynchronous'로 설정
	![STM32CubeMX_freeRTOS.png](images/FreeRTOS/STM32CubeMX_freeRTOS.png "" "width:50%;")
 6. 메뉴에서 Project > Generate Code를 선택한 후 'Project Name', 'Project Location'을 입력하고 'Toolchain / IDE'를 TrueSTUDIO로 설정 후 OK 버튼 클릭
	![STM32CubeMX_project_settings.png](images/FreeRTOS/STM32CubeMX_project_settings.png "" "width:50%;")
 7. 생성된 project template을 TrueSTUDIO에서 로드
 8. 참고 site
	+ http://igotit.tistory.com/entry/STM32-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC-%ED%99%95%EB%B3%B4%EB%B0%A9%EB%B2%95%EB%B0%8F-%ED%99%9C%EC%9A%A9%EB%B2%95-%EA%B0%80%EC%9D%B4%EB%93%9CSTM32CubeMX
	+ http://igotit.tistory.com/159

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
	+ __SPT_DEBUG_ENABLE__
		define 됐을 경우 debug log 활성화 됨

Sample
---
 1. Code import
	+ 'Src' directory에 sample code 추가
 2. Configuration.h 수정
	+ __MAC_ADDRESS__
		사용할 mac address 지정(다른 device와 겹치면 안됨)
	+ __MQTT_ENABLE_SERVER_CERT_AUTH__
		0:Normal socket 사용, 1:TLS socket 사용
	+ __THINGPLUG_USER_NAME__
		ThingPlug 계정의 UKEY
	+ __THINGPLUG_PASSWORD__
		ThingPlug 계정의 Password
	+ __ONEM2M_NODEID__
		ThingPlug 계정에 등록된 Device ID
 3. 'main.c' 파일 수정
	+ main() 함수에 WIZnet library 초기화 등의 code 추가
	+ serial port speed 변경
		main.c 파일에서 MX_USART2_UART_Init() 함수의 huart2.Init.BaudRate = 115200; 수정