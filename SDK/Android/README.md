ThingPlug oneM2M SDK for Android
===
본 챕터는 SKT ThingPlug Android oneM2M SDK API 및 사용법에 대하여 서술한다.

## Resources
* **[API Documentation](http://sobhamo.github.io/hello-world/)** - SDK API 다큐먼트
* **[Simple App Sample](https://github.com/sobhamo1/SDK)** - Simple SDK 사용 App
* **[Device App Sample](https://github.com/sobhamo1/SDK)** - 디바이스 등록, 주기보고, 제어처리 목적으로 동작하는 App 
* **[Service App Sample](https://github.com/sobhamo1/SDK)** - 상태조회, 제어요청을 목적으로 동작하는 App
* **[Device & Service App Guide](https://github.com/sobhamo/hello-world/blob/master/SDK/Android/Sample_App.md)** - Device & Service App 설명

## Requirements
* AndroidStudio
* **[API Level](https://developer.android.com/guide/topics/manifest/uses-sdk-element.html)**

	```
	    defaultConfig {
			minSdkVersion 18
			targetSdkVersion 24
		}
	```

## Library
다음 라이브러리들을 사용한다.

라이브러리 | 기능 | 홈페이지
------------ | ------------- | -------------
__paho__ | MQTT | **[paho Homepage](https://eclipse.org/paho/)**
__Simple XML__ | XML Serialization & Parsing | **[Simple XML Homepage](http://simple.sourceforge.net/)**

## Getting Started Using the SDK


