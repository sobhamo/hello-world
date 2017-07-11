# FreeRTOS (+TLS)

## 지원 사양

1. Footprint
	+ CPU : 50MHz 이상
	+ RAM : 64KB 이상
	+ Flash memory : 256KB 이상
2. 테스트 환경
 1. Main board
	+ STMicroelectronics Nucleo-64 F411RE
	![NUCLEO-F411RE.jpg](/SDK_V1_14/images/NUCLEO-F411RE.jpg)
		+ CPU : 100MHz ARM 32-bit Cortex-M4 with FPU
		+ RAM : 128KB
		+ Flash memory : 512KB
 2. Ethernet board
	+ WIZnet W5500
    ![w5500_ethernet_shield.jpg](/SDK_V1_14/images/w5500_ethernet_shield.jpg)
    
## Source Tree

* [Source](https://github.com/SKT-ThingPlug/thingplug-device-sdk-C/tree/master/FreeRTOS)
+ __FreeRTOS__ (project root)
	+ __Drivers__
		+ __CMSIS__
		+ __STM32F4xx_HAL_Driver__ (Nucleo F411RE driver)
	+ __Inc__ (FreeRTOS 및 WIZnet driver configuration headers)
	+ __Library__ (libraries)
		+ __Ethernet_WIZnet__ (WIZnet library)
			+ __Ethernet__ (WIZnet driver 및 socket source)
			+ __Internet__ (internet 통신 source)
				+ __DHCP__ (DHCP 통신 처리)
				+ __DNS__ (DNS 통신 처리)
				+ __SNTP__ (SNTP 통신 처리)
		+ __mbedtls_2_3_0__ (mbed TLS library)
			+ __include__ (library headers)
			+ __library__ (TLS library source)
		+ __Mqtt_Paho__ (mqtt library, TLS library 및 WIZnet socket 연결)
			+ __MQTTPacket__ (MQTT packet 처리 source)
		+ __oneM2M__ (oneM2M library)
			+ __include__ (library headers)
			+ __src__ (oneM2M library source)
				+ __net__ (MQTT handler)
				+ __oneM2M_V1_14__ (ThingPlug oneM2M v1.14 API)
	+ __Middlewares__
		+ __Third_Party__
			+ __FreeRTOS__ (FreeRTOS source)
	+ __Src__ (FreeRTOS main)
		+ __oneM2M__ (test sample)
			+ __MA__ (ManagementAgent)
			+ __SMA__ (SensorManagementAgent)
			+ __SRA__ (ServiceReadyAgent)

# Project build

## IDE 설정
	
	+ Atollic TrueSTUDIO for ARM
		+ http://atollic.com/resources/downloads/
		+ FreeRTOS homepage에서 추천하고 있음, Eclipse 기반의 embeded 환경 프로그램 개발 tool

## Sample code import

1. Code Download
-> git clone 하거나 다운로드 진행
2. TrueSTUDIO를 실행한다.
3. 위 메뉴바 -> 파일 -> 가져오기
4. 일반->기존 프로젝트를 작업공간으로->루트 디렉토리(압축해제한 폴더 선택)->프로젝트 선택->완료
5. 메뉴바->프로젝트->모두빌드 (경고 화면이 나올 수 있으나 무시)
6. 보드를 연결
7. 실행->디버그 구성->Embedded C/C++ Application->FreeRTOS.elf(확인후)->오른쪽 하단에 디버그 버튼 클릭
8. 메뉴바->실행->재게를 누르면 프로그램이 동작한다.
9. 실행 로그 확인
	+ Serial 통신 프로그램(ex. Putty, Tera Term 등)을 사용해서 프로그램 로그 확인
	
	(※ sample code의 main.c 파일에서 지정한 bit rate 맞춰 환경설정해야 글자가 깨져 보이지 않음)
	
	https://github.com/sobhamo/hello-world/blob/master/SDK_V1_14/FreeRTOS/SDKVerification.log

Copyright (c) 2017 SK Telecom Co., Ltd. All Rights Reserved.
