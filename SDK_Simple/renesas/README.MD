Renesas (+TLS)
===

지원 사양
---
1. 테스트 환경
	+ Renesas Synergy™ Starter Kit SK-S7G2

2. 최소 동작 환경
	+ CPU : 50MHz 이상
	+ RAM : 64KB 이상
	+ Flash memory : 256KB 이상

Library
---
다음 라이브러리들을 사용합니다.

라이브러리 | 용도 | 홈페이지
------------ | ------------- | -------------
__cJSON__ | JSON parser | [cJSON Homepage](https://github.com/DaveGamble/cJSON)
__paho__ | MQTT Embedded-c | [paho Homepage](https://eclipse.org/paho/)
__mbedtls__ | Mbed Tls | [mbed tls Homepage](https://tls.mbed.org/)

IDE 설정
---
1. IDE 설치하기
	+ https://synergygallery.renesas.com/
	+ Synergy Software Package
	+ 개발툴 -> e²studio
	+ 개발툴 -> Renesas SSC
	+ 라이센스
	+ https://synergygallery.renesas.com/media/products/1/224/en-US/r01us0315eu0100-synergy-ssp-v130.pdf
	+ User Manual 확인하여 설치 (Chapter3 Starting Development)
	
2. IDE에 프로젝스 import 하기
	+ 메뉴바 : File -> import -> Existing Projects into Workspace -> git clone 폴더 선택 -> threadX 프로젝트 선택
	![renesas_ide_01.png](../images/renesas_ide_01.png)
	![renesas_ide_02.png](../images/renesas_ide_02.png)
	+ configuration.xml 선택 -> Generate Project Content 
	![renesas_ide_03.png](../images/renesas_ide_03.png)
	+ src/synergy_gen/user_thread.c 의 g_sf_comms0_err_callback_internal 소스코드 수정
	```
	void g_sf_comms0_err_callback_internal(void * p_instance, void * p_data);
	extern char gStopConsole;
	void g_sf_comms0_err_callback_internal(void * p_instance, void * p_data)
	{
	    gStopConsole = 1;
	}
	```
	
Sample build
===

Configuration 설정(src/SimpleAPI/middleware/Configuration.h)
---
MQTT broker 와의 연결을 위한 정보 및 디바이스 정보를 설정해야 합니다.
```c
#define MQTT_HOST                           ""
#define MQTT_SECURE_HOST                    ""
#define MQTT_PORT                           1883
#define MQTT_SECURE_PORT                    8883						
#define MQTT_KEEP_ALIVE                     120
#define MQTT_ENABLE_SERVER_CERT_AUTH        0
#define LOGIN_NAME                          ""
#define SIMPLE_SERVICE_NAME                 ""
#define SIMPLE_DEVICE_NAME                  ""
```

변수 | 값 | 용도 
------------ | ------------- | -------------
__LOGIN_NAME__ | ThingPlug 포털을 통해 발급받은 디바이스 토큰 | MQTT 로그인 사용자명으로 사용
__SIMPLE_SERVICE_NAME__ | ThingPlug 포털을 통해 등록한 서비스명 | MQTT Topic 에 사용
__SIMPLE_DEVICE_NAME__ | ThingPlug 포털을 통해 등록한 디바이스명 | MQTT Topic 에 사용


디바이스 디스크립터와 Attribute, Telemetry
---
각 디바이스의 고유의 특성을 전달하는 Attribute 변경 통지와 센서를 통해 측정된 값을 전달하는 Telemetry 전송 데이터는 ThingPlug 포털에 등록한 디바이스 디스크립터의 내용과 1:1 매칭되어야 합니다.
다음은 포털에 등록된 디바이스 디스크립터와 매칭된 소스코드 예시입니다.

```json
"Airconditioner": {
     "telemetries": [{"name":"temperature","type":"number"}, {"name":"humidity","type":"int"}],
     "attribute": [{"name":"control","type":"string"}]
 }
```

```c
void telemetry() {
    double temp = 27.05;
    int humi = 75;

    ArrayElement* arrayElement = calloc(1, sizeof(ArrayElement));    
    arrayElement->capacity = 2;
    arrayElement->element = calloc(1, sizeof(Element) * arrayElement->capacity);

    Element* item = arrayElement->element + arrayElement->total;
    item->type = JSON_TYPE_DOUBLE;
    item->name = "temperature";	
    item->value = &temp;
    arrayElement->total++;

    item = arrayElement->element + arrayElement->total;
    item->type = JSON_TYPE_LONG;
    item->name = "humidity";
    item->value = &humi;
    arrayElement->total++;
    
    tpSimpleTelemetry(arrayElement, 0);
    free(arrayElement->element);
    free(arrayElement);
}

void attribute() {
    char *status = "stopped";
	
    ArrayElement* arrayElement = calloc(1, sizeof(ArrayElement));    
    arrayElement->capacity = 1;
    arrayElement->element = calloc(1, sizeof(Element) * arrayElement->capacity);
    
    Element* item = arrayElement->element + arrayElement->total;
    item->type = JSON_TYPE_STRING;
    item->name = "control";
    item->value = status;
    arrayElement->total++;

    tpSimpleAttribute(arrayElement);
    free(arrayElement->element);
    free(arrayElement);
}

```

ThingPlug_Simple_SDK 빌드
---
1. 설정
	+ wifi 사용시 ssid 및 password 설정 (threadX/src/user_thread_entry.c 파일 수정)
	+ !!주의!! 5G 지원 하지 않음.
	```
	#define MY_SSID_FOR_AP ""    /* Change this to your Wi-Fi AP/Router’s SSID */
	#define MY_PASSWORD_FOR_AP "" 
	```
	
2. 빌드	
	```
	Project -> Build Project
	```
	![renesas_ide_04.png](../images/renesas_ide_04.png)
2. 실행
	```
	Run -> Debug -> Renesas GDB Hardware Debugging -> J-Link ARM ->  R7FS7G27G, R7FS7G27H 
	Run -> Resume
	```
	![renesas_ide_05.png](../images/renesas_ide_05.png)
	![renesas_ide_06.png](../images/renesas_ide_06.png)
	![renesas_ide_07.png](../images/renesas_ide_07.png)

