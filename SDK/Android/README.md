ThingPlug oneM2M SDK for Android
===
본 챕터는 SKT ThingPlug Android oneM2M SDK API 및 사용법에 대하여 서술한다.

## Overview
ThingPlug oneM2M SDK for Android는 ThingPlug에 연동하고자 하는 device 개발자 및 application 개발자를 위한 Android기반 SDK이다. ThingPlug서버와의 통신에는 oneM2M 국제표준에 기반한 MQTT 프로토콜을 따르고 있으며, 보안강화를 위해 TLS를 사용할 수 있도록 구성되어 있다.

## Features
본 SDK에서 제공되는 API를 사용하면 oneM2M 국제표준의 세부 규격을 모르더라도 손쉽게 ThingPlug와의 통신을 구현할 수 있다. 모든 내용은 소스코드 형태로 배포되며, SDK 뿐만 아니라 SDK를 활용한 기본 기능 시험을 위한 Simple Test App, Device 역할을 하는 Test App, 그리고 Service Application 역할을 하는 Test App을 같이 배포하고 있다.

## Library
본 SDK에서의 ThingPlug와의 통신은 모두 MQTT를 통해서 이루어진다. 
MQTT client 기능은 오픈소스 프로젝트인 paho를 사용하고 있으며, XML Serialization 과 Parsing 을 위하여 Simple XML 을 사용한다.

라이브러리 | 기능 | 홈페이지
------------ | ------------- | -------------
__paho__ | MQTT | **[paho Homepage](https://eclipse.org/paho/)**
__Simple XML__ | XML Serialization & Parsing | **[Simple XML Homepage](http://simple.sourceforge.net/)**

## Getting Started Using the SDK
SDK 사용을 위하여 다음 설명된 과정을 순서대로 진행한다.

### Setting for MQTT connection
MQTT server 와의 연결을 위한 정보를 설정한다.
```java
MQTTClient.Builder builder = new MQTTClient.Builder(context)
	.baseUrl(server)
	.clientId(id)
	.userName(user)
	.password(pass)
	.setLog(true);
MQTTClient mqttClient = builder.build();
```
함수 | 파라미터
------------ | -------------
__baseUrl__ | 서버 주소
__clientId__ | MQTT 연결 ID
__userName__ | MQTT 연결 user name
__password__ | MQTT 연결 password
__setLog__ | SDK 로그 Enable or Disable

### Using TLS
TLS 사용을 위해서는 서버주소앞에 `ssl://` 을 포함하면 된다. 포트번호가 `8883`인 경우 생략 가능하다.

사용예시는 다음과 같다.
```
ssl://thingplugtest.skitiot.com
```
TLS 사용하지 않을 경우 서버주소앞에 `tcp://` 을 포함하면 된다. 포트번호가 `1883`인 경우 생략 가능하다.

사용예시는 다음과 같다.
```
tcp://thingplugtest.skitiot.com
```


### Configuration for Message
통신 메시지에 사용되어질 정보를 설정한다.
```java
MQTTConfiguration config = new MQTTConfiguration(appEUI,
                toBase,
                deviceResourceID,
                clientId);
```
파라미터 | 설명
------------ | -------------
__appEUI__ | AppEUI
__toBase__ | to 필드 시작 URL
__deviceResourceID__ | 장치 고유 ID (ex : MAC-address) 
__clientId__ | MQTT 연결 ID

### Connects to an MQTT server
생성된 **[`MQTTClient`](http://sobhamo.github.io/hello-world/tp/skt/onem2m/net/mqtt/MQTTClient.html)** 를 통하여 MQTT 서버에 연결하고 각종 이벤트 처리를 위한 **[`MQTTProcessor.MQTTListener`](http://sobhamo.github.io/hello-world/tp/skt/onem2m/api/MQTTProcessor.MQTTListener.html)**를 등록한다.
```java
IMQTT mqttService = mqttClient.connect(IMQTT.class, config, new Binder(), new MQTTProcessor.MQTTListener() {
                    @Override
                    public void onPush(execInstanceControl control) {
                        Log.e(TAG, "push!");
                    }

                    @Override
                    public void onDisconnected() {
                        Log.e(TAG, "disconnect success!");
                    }

                    @Override
                    public void onDisconnectFailure() {
                        Log.e(TAG, "disconnect failure!");
                    }

                    @Override
                    public void onSubscribed() {
                        Log.e(TAG, "subscribe success!");
                    }

                    @Override
                    public void onSubscribeFailure() {
                        Log.e(TAG, "subscribe failure!");
                    }

                    @Override
                    public void onConnected() {
                        Log.e(TAG, "connect success!");
                    }

                    @Override
                    public void onConnectFailure() {
                        Log.e(TAG, "connect fail!");
                    }

                    @Override
                    public void onConnectionLost() {
                        Log.e(TAG, "connection lost!");
                    }

                    @Override
                    public void onDelivered() {
                        Log.e(TAG, "message delivered!");
                    }
                }
        );
```
파라미터 | 설명
------------ | -------------
__IMQTT.class__ | MQTT 서비스 객체
__config__ | 생성된 [`MQTTConfiguration`](http://sobhamo.github.io/hello-world/tp/skt/onem2m/net/mqtt/MQTTConfiguration.html) 객체
__Binder__ | 메시지 바인딩을 위한 [`Binder`](http://sobhamo.github.io/hello-world/tp/skt/onem2m/binder/mqtt_v1_1/Binder.html) 객체
__MQTTProcessor.MQTTListener__ | MQTT 이벤트 리스너

### oneM2M API 
SKT ThingPlug 서버와 oneM2M 통신을 위한 API 는 **[`tp.skt.onem2m.api.oneM2MAPI.java`](http://sobhamo.github.io/hello-world/tp/skt/onem2m/api/oneM2MAPI.html)** 파일에 주로 정의되어있다.
해당 클래스는 Java Singletone 패턴으로 되어있어서 `oneM2MAPI.getInstance()` 형태로 객체를 가져와 사용한다.

함수 | 설명
------------ | -------------
__getInstance()__ | Singletone 객체를 가져온다.
__tpRegisterDevice__ | 장치를 등록한다. (node 와 remoteCSE 를 등록한다.)
__tpRegisterContainer__ | 센서를 등록한다. (container 를 등록한다.)
__tpRegisterMgmtCmd__ | 제어를 등록한다. (mgmtCmd 를 등록한다.)
__tpAddData__ | 센서정보를 추가한다. (contentInstance 의 content(con) 에 담을 정보를 추가한다.)
__tpReport__ | 센서정보를 등록한다. (contentInstance 를 등록한다.)
__tpResult__ | 제어결과를 업데이트한다. (execInstance 를 업데이트한다.)
> 각 함수별 파라미터 설명은 **[`tp.skt.onem2m.api.oneM2MAPI.java`](http://sobhamo.github.io/hello-world/tp/skt/onem2m/api/oneM2MAPI.html)** 에서 확인

### 기기 등록
기기등록을 위한 `tpRegisterDevice` 함수의 사용예시는 다음과 같으며, 성공 실패 여부는 **[`MQTTCallback`](http://sobhamo.github.io/hello-world/tp/skt/onem2m/net/mqtt/MQTTCallback.html)**에 등록된 `onResponse` 와 `onFailure` 이벤트 함수로 확인할 수 있다.

```java
/**
 * register device
 * 
 * @param passcode           : passcode
 * @param cseType            : cseType
 * @param requestRechability : requestRechability
 */
public void registerDevice(String passcode, String cseType, String requestRechability) {
	oneM2MAPI.getInstance().tpRegisterDevice(mqttService, passcode,
			cseType, requestRechability, new MQTTCallback<remoteCSEResponse>() {
				@Override
				public void onResponse(remoteCSEResponse response) {
					Log.e(TAG, "node & remoteCSE CREATE success!");
				}

				@Override
				public void onFailure(int errorCode, String message) {
					Log.e(TAG, "fail!");
				}
			});
}
```

### 센서 등록
센서등록을 위한 `tpRegisterContainer` 함수의 사용예시는 다음과 같다.

```java
/**
 * register sensor
 * 
 * @param containerName : container name
 */
private void registerSensor(String containerName) {
	oneM2MAPI.getInstance().tpRegisterContainer(mqttService, containerName,
			deviceKey, new MQTTCallback<containerResponse>() {
				@Override
				public void onResponse(containerResponse response) {
					Log.e(TAG, "success!");
				}

				@Override
				public void onFailure(int errorCode, String message) {
					Log.e(TAG, "fail!");
				}
			});
}	
```

### 액츄에이터 등록
제어가 가능한 액츄에이터등록을 위한 `tpRegisterMgmtCmd` 함수의 사용예시는 다음과 같다.

```java
/**
 * register control
 * 
 * @param mgmtCmdName : mgmtCmd name
 * @param cmdType     : cmdType
 * @param execEnable  : execute enable
 * @param execTarget  : node link
 */
private void registerControl(String mgmtCmdName, String cmdType, String execEnable, String execTarget) {
	oneM2MAPI.getInstance().tpRegisterMgmtCmd(mqttService, mgmtCmdName,
			deviceKey, cmdType, execEnable, execTarget, new MQTTCallback<mgmtCmdResponse>() {
				@Override
				public void onResponse(mgmtCmdResponse response) {
					Log.e(TAG, "success!");
				}

				@Override
				public void onFailure(int errorCode, String message) {
					Log.e(TAG, "fail!");
				}
			});
}
```

### 센서 상태 보고
센서 상태 보고를 위한 `tpAddData`와 `tpReport` 함수의 사용예시는 다음과 같다.

```java
/**
 * conent value add
 * 
 * @param value : sensor status
 */
private void addStatus(String value) {
	oneM2MAPI.getInstance().tpAddData(value);
}

/**
 * report conent values
 * 
 * @param containerName : container Name
 * @param contentInfo   : content type
 * @param content       : content
 * @param useAddedData  : use Added data flag
 */
private void report(String containerName, String contentInfo, String content, boolean useAddedData) {
	oneM2MAPI.getInstance().tpReport(mqttService, containerName,
			deviceKey, contentInfo, content, useAddedData, new MQTTCallback<contentInstanceResponse>() {
				@Override
				public void onResponse(contentInstanceResponse response) {
					Log.e(TAG, "success!");
				}

				@Override
				public void onFailure(int errorCode, String message) {
					Log.e(TAG, "fail!");
				}
			});
}
```
> `tpAddData` 함수를 통하여 여러 센서의 정보를 수집할 수 있다.
> 이 경우 `tpReport` 함수의 useAddedData 파라미터를 true 로 설정하고, content 파라미터를 null 로 설정하면 그동안 `tpAddData` 함수를 통하여 수집된 content 정보가 서버로 전달된다.
> `tpAddData` 함수를 사용하지 않을 경우 useAddedData 파라미터를 false 로 설정하고, content 파라미터에 값을 입력하면 된다.

### 제어 결과 보고
제어 결과 보고를 위한 `tpResult` 함수의 사용예시는 다음과 같다.

```java
/**
 * control result
 * 
 * @param mgmtCmdName : mgmtCmd Name
 * @param resourceId  : execInstance resource Id
 * @param execResult  : execute result code
 * @param execStatus  : execute status code
 */
public void controlResult(String mgmtCmdName, String resourceId, String execResult, String execStatus) {
	oneM2MAPI.getInstance().tpResult(mqttService, mgmtCmdName,
			deviceKey, resourceId, execResult, execStatus, new MQTTCallback<execInstanceResponse>() {
				@Override
				public void onResponse(execInstanceResponse response) {
					Log.e(TAG, "success!");
				}

				@Override
				public void onFailure(int errorCode, String message) {
					Log.e(TAG, "fail!");
				}
			});
}
```
> execResult 와 execStatus 코드는 **[ThingPlug_API_Document_v1_2.pdf](https://lora.sktiot.com/api/common/file/download?fileId=00EHVA8TRRAME2403FEA)** 문서 6.5.3 절에서 확인 가능하다.

### Error Code
**[`MQTTCallback`](http://sobhamo.github.io/hello-world/tp/skt/onem2m/net/mqtt/MQTTCallback.html)** 을 통해 발생한 응답의 성공 실패 여부를 확인하는 코드는 **[`tp.skt.onem2m.binder.mqtt_v1_1.Definitions.java`](http://sobhamo.github.io/hello-world/tp/skt/onem2m/binder/mqtt_v1_1/Definitions.html)** 에 정의되어 있으며 다음과 같다.

서버와의 커뮤니케이션관련 오류는 paho 라이브러리내 **[`org.eclipse.paho.client.mqttv3.MqttException.java`](https://www.eclipse.org/paho/files/javadoc/org/eclipse/paho/client/mqttv3/MqttException.html)** 에 정의되어 있으며 본 문서에는 포함하지 않는다.
```java
public @interface ResponseStatusCode {
	int ACCEPTED = 1000;
	int OK = 2000;
	int CREATED = 2001;
	int DELETED = 2002;
	int CHANGED = 2004;
	int BAD_REQUEST = 4000;
	int NOT_FOUND = 4004;
	int OPERATION_NOT_ALLOWED = 4005;
	int REQUEST_TIMEOUT = 4008;
	int SUBSCRIPTION_CREATOR_HAS_NO_PRIVILEGE = 4101;
	int CONTENTS_UNACCEPTABLE = 4102;
	int ACCESS_DENIED = 4103;
	int GROUP_REQUEST_IDENTIFIER_EXISTS = 4104;
	int CONFLICT = 4105;
	int INTERNAL_SERVER_ERROR = 5000;
	int NOT_IMPLEMENTED = 5001;
	int TARGET_NOT_REACHABLE = 5103;
	int NO_PRIVILEGE = 5105;
	int ALREADY_EXISTS = 5106;
	int TARGET_NOT_SUBSCRIBABLE = 5203;
	int SUBSCRIPTION_VERIFICATION_INITIATION_FAILED = 5204;
	int SUBSCRIPTION_HOST_HAS_NO_PRIVILEGE = 5205;
	int NON_BLOCKING_REQUEST_NOT_SUPPORTED = 5206;
	int EXTENAL_OBJECT_NOT_REACHABLE = 6003;
	int EXTENAL_OBJECT_NOT_FOUND = 6005;
	int MAX_NUMBERF_OF_MEMBER_EXCEEDED = 6010;
	int MEMBER_TYPE_INCONSISTENT = 6011;
	int MGMT_SESSION_CANNOT_BE_ESTABLISHED = 6020;
	int MGMT_SESSION_ESTABLISHMENT_TIMEOUT = 6021;
	int INVALID_CMDTYPE = 6022;
	int INSUFFICIENT_ARGUMENTS = 6023;
	int MGMT_CONVERSION_ERROR = 6024;
	int MGMT_CANCELATION_FAILURE = 6025;
	int ALREADY_COMPLETE = 6028;
	int COMMAND_NOT_CANCELLABLE = 6029;
	int INTERNAL_SDK_ERROR = 9999;
}
```

## Resources
* **[API Documentation](http://sobhamo.github.io/hello-world/)** - SDK API 다큐먼트
* **[Simple App Sample](https://github.com/sobhamo1/SDK)** - Simple SDK 사용 App
* **[Device App Sample](https://github.com/sobhamo1/SDK)** - 디바이스 등록, 주기보고, 제어처리 목적으로 동작하는 App 
* **[Service App Sample](https://github.com/sobhamo1/SDK)** - 상태조회, 제어요청을 목적으로 동작하는 App
* **[Device & Service App Guide](https://github.com/sobhamo/hello-world/blob/master/SDK/Android/Sample_App.md)** - Device & Service App 설명

## Requirements
* **[AndroidStudio](https://developer.android.com/studio/index.html)**
* **[API Level](https://developer.android.com/guide/topics/manifest/uses-sdk-element.html)**

```
defaultConfig {
	minSdkVersion 18
	targetSdkVersion 24
}
```
