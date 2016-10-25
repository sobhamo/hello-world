## ThingPlug oneM2M SDK 사 가이드
본 챕터는 Device 를 oneM2M 방식으로 등록하는 방법을 서술한다.


 SDK 디렉토리 구조
==================

구조
----

> **|–oneM2M** (ThingPlug oneM2M SDK root folder)
>
> **|–docs** (Developer guide & API documentation)
>
> **|–samples** (Samples including makefiles for build)
>
> **|–lib** (external libraries - paho, TLS, )
>
> **|–include** (Header files of the ThingPlug oneM2M SDK)
>
> **|–src** (Source files of the ThingPlug oneM2M SDK)
>
> **|–oneM2M\_V1** (Source files of the ThingPlug oneM2M v1 API)
>
> **|–oneM2M\_V1\_12** (Source files of the ThingPlug oneM2M v1.12 API)
>
> **|–net** (Source files of the MQTTS)

상세 설명
---------

| Directory          | Description                           |
|--------------------|---------------------------------------|
| **oneM2M**         | ThingPlug oneM2M SDK 루트 폴더        |
| **docs**           | 개발 가이드와 API document 폴더       |
| **samples**        | SDK 를 이용한 샘플 Application들 폴더 |
| **lib**            | 외부 라이브러리나 소스들 폴더         |
| **Include**        | 동작에 필요한 header 파일들 폴더      |
| **Src**            | 전체 소스 폴더                        |
| **oneM2M\_V1**     | ThingPlug oneM2M v1 관련 소스 폴더    |
| **oneM2M\_V1\_12** | ThingPlug oneM2M v1.12 관련 소스 폴더 |
| **net**            | MQTT/TLS 관련 소스들 폴더             |
