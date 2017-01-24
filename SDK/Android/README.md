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
__getInstance__ | Singletone 객체를 가져온다.
__tpRegisterDevice__ | 장치를 등록한다. (node 와 remoteCSE 를 등록한다.)
__tpRegisterContainer__ | 센서를 등록한다. (container 를 등록한다.)
__tpRegisterMgmtCmd__ | 제어를 등록한다. (mgmtCmd 를 등록한다.)
__tpAddData__ | 센서정보를 추가한다. (contentInstance 의 content(con) 에 담을 정보를 추가한다.)
__tpReport__ | 센서정보를 등록한다. (contentInstance 를 등록한다.)
__tpResult__ | 제어결과를 업데이트한다. (execInstance 를 업데이트한다.)
