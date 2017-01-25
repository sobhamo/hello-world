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
* **[AndroidStudio](https://developer.android.com/studio/index.html)**
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
SDK 사용을 위하여 다음 설명된 과정을 순서대로 진행한다.

### Setting for MQTT connection
MQTT server 와의 연결을 위한 정보를 설정한다.
```
MQTTClient mqttClient = MQTTClient.Builder builder = new MQTTClient.Builder(context)
	.baseUrl(server)
	.clientId(id)
	.userName(user)
	.password(pass)
	.setLog(true).build();
```
함수 | 파라미터
------------ | -------------
__baseUrl__ | 서버 주소
__clientId__ | MQTT 연결 ID
__userName__ | MQTT 연결 user name
__password__ | MQTT 연결 password
__setLog__ | SDK 로그 Enable or Disable

### Configuration for Message
통신 메시지에 사용되어질 정보를 설정한다.
```
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
생성된 `MQTTClient` 를 통하여 MQTT 서버에 연결하고 각종 이벤트 처리를 위한 리스너를 등록한다.
```
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
__config__ | 생성된 `MQTTConfiguration` 객체
__Binder__ | 메시지 바인딩을 위한 `Binder` 객체
__MQTTProcessor.MQTTListener__ | MQTT 이벤트 리스너

### oneM2M API 
SKT ThingPlug 서버와 oneM2M 통신을 위한 API 는 `tp.skt.onem2m.api.oneM2MAPI.java` 파일에 주로 정의되어있다.
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

`tpRegisterDevice` 사용예시는 다음과 같으며, 성공 실패 여부는 `MQTTCallback`에 등록된 `onResponse` 와 `onFailure' 이벤트 함수로 확인할 수 있다.
다른 oneM2M API 함수들도 비슷한 형태로 사용 가능하다.
```
public void registerDevice() {
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

### Error Code
`MQTTCallback` 을 통해 발생한 응답의 성공 실패 여부를 확인하는 코드는 `tp.skt.onem2m.binder.mqtt_v1_1.Definitions.java` 에 정의되어 있으며 다음과 같다.
서버와의 커뮤니케이션관련 오류는 paho 라이브러리내 `org.eclipse.paho.client.mqttv3.MqtttException.java` 에 정의되어 있으며 본 문서에는 포함하지 않는다.
```
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
