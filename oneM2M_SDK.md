
ThingPlug onM2M SDK
===
본 챕터는 SKT ThingPlug oneM2M SDK 구조와 API 및 사용법에 대하여 서술한다.

Architect
---
![](images/sdk_architect.png)

SDK Source Tree
---
+ __oneM2M__ (ThingPlug oneM2M SDK root folder)
	+ __docs__ (Developer guide & API documentation)
	+ __samples__ (Samples including makefiles for build)
	+ __lib__ (external libraries - paho, TLS, )
	+ __include__ (Header files of the ThingPlug oneM2M SDK)
	+ __src__ (Source files of the ThingPlug oneM2M SDK)
		+ __oneM2M_V1__ (Source files of the ThingPlug oneM2M v1)
		+ __oneM2M_V1_12__ (Source files of the ThingPlug oneM2M v1.12)
		+ __net__ (Source files of the MQTTS)
			
Description
---
Directory | Description
------------ | -------------
oneM2M | ThingPlug oneM2M SDK 루트 폴더
docs | 개발 가이드와 API document 폴더
samples |	SDK 를 이용한 샘플 Application들 폴더
lib | 외부 라이브러리나 소스들 폴더
include | 동작에 필요한 header 파일들 폴더
src | 전체 소스 폴더
oneM2M_V1 | ThingPlug oneM2M v1 관련 소스 폴더
oneM2M_V1_12 | ThingPlug oneM2M v1.12 관련 소스 폴더
net | MQTT/TLS 관련 소스들 폴더

API Documentation
---
* [Download](https://github.com/sobhamo/hello-world/raw/master/SDK/docs/ThingPlug_oneM2M_SDK_API-20161025.docx)

