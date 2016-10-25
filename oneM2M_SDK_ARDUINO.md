ARDUINO
===

지원 사양
---
1. Main board
	+ Arduino 101 (USA ONLY) & Genuino 101 (OUTSIDE USA) ![genuino101.jpg](images/genuino101.jpg "" "width:50%;")
		+ CPU : 32MHz Intel Curie
		+ RAM : 24KB
		+ Flash memory : 196KB
2. Ethernet board
	+ Arduino Ethernet Shield R3 ![ArduinoEthernetShield_R3_Front_450px.jpg](images/ArduinoEthernetShield_R3_Front_450px.jpg "" "width:50%;")
3. Footprint
	+ Binary file size : 93KB 
	+ Heap memory usage : 21KB

Source Tree
---
+ __Arduino__ (project root)
	+ __examples__
		+ __ArduinoMiddleware__ (Middleware)
			+ ArduinoMiddleware.ino ( Arduino Sketch File )
			+ __src__
			  + __MA__ (ManagementAgent)
			  + __SRA__ (ServiceReadyAgent)
			  + __SMA__ (SensorManagementAgent)
		+ __SDKVerification__ (SDK example)
			+ SDKVerfication.ino (sdk test source)
			+ Configuration.h (config file)
	+ __src__ (oneM2M SDK source)
		+ __MQTT__ (Arduino MQTT Library)
		+ __include__ (header files)
		+ __net__ (MQTT interface source)
		+ __oneM2M_V1__ (oneM2M v1 sdk source)
		+ __oneM2M_V1_12__ (oneM2M v1.12 sdk source)
	+ __libraries__ (Essential Arduino Libraries)
		+ __Time__ (Arduino Time library)
		+ __LCD__ (Arduino LCD library)
	+ __docs__ (SDK Documents) <now empty>

Project build
===

IDE 설정
---
1. IDE homepage
	+ https://www.arduino.cc/
	![arduinocchomepage.png](images/arduinocchomepage.png "" "width:50%;")
2. Download & Install
	![arduinodownload.png](images/arduinodownload.png "" "width:50%;")
3. Installing Additional Arduino Libraries
    + https://www.arduino.cc/en/Guide/Libraries
    + 라이브러리 설치에 대한 자세한 설명은 공식사이트를 참고한다.

SDK 설치하기
---
1. oneM2M 라이브러리를 다운로드 받는다.
2. 압축을 해제한다.
3. IDE가 설치되면, 각 피씨환경의 홈 폴더에 Arduino 폴더가 생긴다.
4. Arduino/libraries 에 oneM2M SDK 폴더를 복사한다.
5. oneM2M/libraries 에 있는 모든 라이브러리를 Arduino/libraries 폴더로 복사한다.
6. SDK 설치가 완료되었다.

SDK 빌드하기
---
1. Arduino IDE를 실행시킨다.
2. 상단 메뉴에 스케치->컴파일을 선택한다.
3. 상단 메뉴에 툴->보드, 포트를 Genuino 101/연결된 포트로 설정한다.
4. 상단 메뉴에 스케치-> 업로드를 선택한다.
5. 성공시 아래 콘솔창에 메시지가 출력된다
```
스케치는 프로그램 저장 공간 81,688 바이트(52%)를 사용. 최대 155,648 바이트.
Starting download script...
SUCCESS: Sketch will execute in about 5 seconds.
```

SDK 예제 코드 실행하기
---
1. Arduino IDE를 실행한다.
2. 상단 메뉴에 파일->예제->oneM2M->SDKVerification 선택한다.
3. 상단 메뉴에 스케치->컴파일을 선택한다.
4. 상단 메뉴에 툴->보드, 포트를 Genuino 101/연결된 포트로 설정한다.
5. 상단 메뉴에 스케치-> 업로드를 선택한다.
6. 업로드가 완료되면 상단 메뉴에 툴->시리얼 모니터를 선택한다.
사진
7. oneM2M 동작 테스트가 시작된다.
8. 결과
```
Transmit NTP Request























Receive NTP Response
tpMQTTSetCallbacks
MQTTCreate
21048 << randNubmer
arduino_client_id_21048 << ClientID
MQTTConnected : 0
MQTT Request subscibe topic : /oneM2M/req/+/1.2.481.1.999.198.000101
OnSubscribe
MQTT Request subscibe topic : /oneM2M/resp/1.2.481.1.999.198.000101/+
OnSubscribe
MQTTSubscribed : 0
>>>>>>>>>>>>>>>>> step = 0 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>2</op><to>/ThingPlug/v1_0</to><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr></m2m:req>
MQTT publish Success
MQTTMessageDelivered : 0
tpSDKCreate result : 0
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>2000</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><pc><cb><ty>5</ty><ri>CB00000000000000000007</ri><rn>v1_0</rn><ct>2016-06-10T19:48:56+09:00</ct><lt>2016-07-08T14:07:52+09:00</lt><et>30</et><csi>/ThingPlug</csi><srt>1 2 3 4 5 8 10 12 16 14 23</srt><poa>http://61.250.21.53:9000/ThingPlug/v1_0</poa></cb></pc></m2m:rsp>
name : rsc
value : 2000
=============================================================================
>>>>>>>>>>>>>>>>> step = 1 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>1</op><to>/ThingPlug/v1_0</to><ty>14</ty><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><nm>1.2.481.1.999.198.000101</nm><pc><nod><ni>1.2.481.1.999.198.000101</ni><mga>MQTT|1.2.481.1.999.198.000101</mga></nod></pc></m2m:req>
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>4105</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><RSM>Already registered node.</RSM><pc><nod><ty>14</ty><ri>ND00000000000000001397</ri><rn>1.2.481.1.999.198.000101</rn><pi>CB00000000000000000007</pi><ct>2016-10-25T17:10:14+09:00</ct><lt>2016-10-25T17:10:21+09:00</lt><ni>1.2.481.1.999.198.000101</ni><hcl>RC00000000000000003895</hcl><mga>MQTT|1.2.481.1.999.198.000101</mga></nod></pc></m2m:rsp>
name : rsc
value : 4105
name : RSM
value : Already registered node.
name : ri
value : ND00000000000000001397
name : hcl
value : RC00000000000000003895
=============================================================================
>>>>>>>>>>>>>>>>> step = 2 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>1</op><to>/ThingPlug/v1_0</to><ty>16</ty><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><passCode>123456</passCode><nm>1.2.481.1.999.198.000101</nm><pc><csr><cst>3</cst><csi>1.2.481.1.999.198.000101</csi><poa>MQTT|1.2.481.1.999.198.000101</poa><rr>true</rr><nl>ND00000000000000001397</nl></csr></pc></m2m:req>
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>4105</rsc><dKey>T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==</dKey><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><RSM>Already registered device.</RSM><pc><csr><ty>16</ty><ri>RC00000000000000003895</ri><rn>1.2.481.1.999.198.000101</rn><pi>CB00000000000000000007</pi><ct>2016-10-25T17:10:20+09:00</ct><lt>2016-10-25T17:10:20+09:00</lt><acpi>AP00000000000000001799</acpi><cst>3</cst><poa>MQTT|1.2.481.1.999.198.000101</poa><csi>1.2.481.1.999.198.000101</csi><rr>true</rr><nl>ND00000000000000001397</nl></csr></pc></m2m:rsp>
name : rsc
value : 4105
name : RSM
value : Already registered device.
name : dKey
value : T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==
name : rn
value : 1.2.481.1.999.198.000101
=============================================================================
>>>>>>>>>>>>>>>>> step = 3 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>1</op><to>/ThingPlug/v1_0/remoteCSE-1.2.481.1.999.198.000101</to><ty>3</ty><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><nm>1.2.481.1.999.198.000101_container_01</nm><dKey>T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==</dKey><pc><cnt><lbl>con</lbl></cnt></pc></m2m:req>
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>4105</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><RSM>Already registered container.</RSM><pc><cnt><ty>3</ty><ri>CT00000000000000002399</ri><rn>1.2.481.1.999.198.000101_container_01</rn><pi>RC00000000000000003895</pi><ct>2016-10-25T17:10:27+09:00</ct><lt>2016-10-25T18:41:48+09:00</lt><lbl>con</lbl><st>10</st><cr>RC00000000000000003895</cr><cni>10</cni><cbs>342</cbs></cnt></pc></m2m:rsp>
name : rsc
value : 4105
name : RSM
value : Already registered container.
name : rn
value : 1.2.481.1.999.198.000101_container_01
=============================================================================
>>>>>>>>>>>>>>>>> step = 4 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>1</op><to>/ThingPlug/v1_0</to><ty>12</ty><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><nm>1.2.481.1.999.198.000101_mgmtCmd_01</nm><dKey>T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==</dKey><pc><mgc><cmt>sensor_1</cmt><exe>false</exe><ext>ND00000000000000001397</ext><lbl>1.2.481.1.999.198.000101</lbl></mgc></pc></m2m:req>
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>4105</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><RSM>Already registered MgmtCmd.</RSM><pc><mgc><ty>12</ty><ri>MC00000000000000003639</ri><rn>1.2.481.1.999.198.000101_mgmtCmd_01</rn><pi>CB00000000000000000007</pi><ct>2016-10-25T18:41:16+09:00</ct><lt>2016-10-25T18:41:16+09:00</lt><lbl>1.2.481.1.999.198.000101</lbl><cmt>sensor_1</cmt><exe>false</exe><ext>ND00000000000000001397</ext></mgc></pc></m2m:rsp>
name : rsc
value : 4105
name : RSM
value : Already registered MgmtCmd.
name : rn
value : 1.2.481.1.999.198.000101_mgmtCmd_01
=============================================================================
>>>>>>>>>>>>>>>>> step = 5 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>1</op><to>/ThingPlug/v1_0/remoteCSE-1.2.481.1.999.198.000101/container-1.2.481.1.999.198.000101_container_01</to><ty>4</ty><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><dKey>T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==</dKey><pc><cin><cnf>text</cnf><con>45</con></cin></pc></m2m:req>
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>2001</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><pc><cin><ty>4</ty><ri>CI00000000000013144405</ri><rn>CI00000000000013144405</rn><pi>CT00000000000000002399</pi><ct>2016-10-25T19:25:23+09:00</ct><lt>2016-10-25T19:25:23+09:00</lt><et>2016-11-24T19:25:23+09:00</et><st>11</st><cr>RC00000000000000003895</cr><cnf>text</cnf><cs>2</cs><con>45</con></cin></pc></m2m:rsp>
name : rsc
value : 2001
name : rn
value : CI00000000000013144405
=============================================================================
>>>>>>>>>>>>>>>>> step = 6 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>1</op><to>/ThingPlug/v1_0</to><ty>10</ty><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><dKey>T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==</dKey><nm>1.2.481.1.999.198.000101_locationPolicy_01</nm><pc><lcp><los>2</los><lbl>1.2.481.1.999.198.000101</lbl></lcp></pc></m2m:req>
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>2001</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><pc><lcp><ty>10</ty><ri>LP00000000000000000238</ri><rn>1.2.481.1.999.198.000101_locationPolicy_01</rn><pi>CB00000000000000000007</pi><ct>2016-10-25T19:25:25+09:00</ct><lt>2016-10-25T19:25:25+09:00</lt><lbl>1.2.481.1.999.198.000101</lbl><los>2</los></lcp></pc></m2m:rsp>
name : rsc
value : 2001
name : rn
value : 1.2.481.1.999.198.000101_locationPolicy_01
=============================================================================
>>>>>>>>>>>>>>>>> step = 7 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>1</op><to>/ThingPlug/v1_0/remoteCSE-1.2.481.1.999.198.000101</to><ty>2</ty><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><dKey>T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==</dKey><pc><ae><api>1.2.481.1.0001.002.1234</api><apn>1.2.481.1.999.198.000101_AE_01</apn></ae></pc></m2m:req>
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>2001</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><pc><ae><ty>2</ty><ri>AE00000000000000000279</ri><rn>AE00000000000000000279</rn><pi>RC00000000000000003895</pi><ct>2016-10-25T19:25:26+09:00</ct><lt>2016-10-25T19:25:26+09:00</lt><apn>1.2.481.1.999.198.000101_AE_01</apn><api>1.2.481.1.0001.002.1234</api><aei>S00000000000000000279</aei></ae></pc></m2m:rsp>
name : rsc
value : 2001
name : rn
value : AE00000000000000000279
=============================================================================
>>>>>>>>>>>>>>>>> step = 8 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>1</op><to>/ThingPlug/v1_0/node-1.2.481.1.999.198.000101</to><ty>13</ty><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><nm>1.2.481.1.999.198.000101_areaNwkInfo_01</nm><dKey>T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==</dKey><pc><ani><mgd>1004</mgd><ant>type</ant><ldv></ldv></ani></pc></m2m:req>
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>2001</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><pc><ani><ty>13</ty><ri>AI00000000000000000231</ri><rn>1.2.481.1.999.198.000101_areaNwkInfo_01</rn><pi>ND00000000000000001397</pi><ct>2016-10-25T19:25:28+09:00</ct><lt>2016-10-25T19:25:28+09:00</lt><lbl></lbl><mgd>1004</mgd><ant>type</ant><ldv></ldv></ani></pc></m2m:rsp>
name : rsc
value : 2001
name : rn
value : 1.2.481.1.999.198.000101_areaNwkInfo_01
=============================================================================
>>>>>>>>>>>>>>>>> step = 9 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>3</op><to>/ThingPlug/v1_0/node-1.2.481.1.999.198.000101/areaNwkInfo-1.2.481.1.999.198.000101_areaNwkInfo_01</to><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><nm>1.2.481.1.999.198.000101_areaNwkInfo_01</nm><dKey>T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==</dKey><pc><ani><ant>type2</ant><ldv>1</ldv></ani></pc></m2m:req>
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>2004</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><pc><ani><ty>13</ty><ri>AI00000000000000000231</ri><rn>1.2.481.1.999.198.000101_areaNwkInfo_01</rn><pi>ND00000000000000001397</pi><ct>2016-10-25T19:25:28+09:00</ct><lt>2016-10-25T19:25:30+09:00</lt><lbl></lbl><mgd>1004</mgd><ant>type2</ant><ldv>1</ldv></ani></pc></m2m:rsp>
name : rsc
value : 2004
=============================================================================
>>>>>>>>>>>>>>>>> step = 10 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>3</op><to>/ThingPlug/v1_0/remoteCSE-1.2.481.1.999.198.000101/AE-AE00000000000000000279</to><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><dKey>T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==</dKey><pc><ae><apn>1.2.481.1.999.198.000101_AE_02</apn></ae></pc></m2m:req>
MessageArrivedCallback
MessageArrivedCallback
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>2004</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><pc><ae><ty>2</ty><ri>AE00000000000000000279</ri><rn>AE00000000000000000279</rn><pi>RC00000000000000003895</pi><ct>2016-10-25T19:25:26+09:00</ct><lt>2016-10-25T19:25:38+09:00</lt><apn>1.2.481.1.999.198.000101_AE_02</apn><api>1.2.481.1.0001.002.1234</api><aei>S00000000000000000279</aei></ae></pc></m2m:rsp>
name : rsc
value : 2004
=============================================================================
>>>>>>>>>>>>>>>>> step = 11 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>3</op><to>/ThingPlug/v1_0/locationPolicy-1.2.481.1.999.198.000101_locationPolicy_01</to><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><dKey>T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==</dKey><pc><lcp><los>3</los><lbl>1.2.481.1.999.198.000101</lbl></lcp></pc></m2m:req>
MessageArrivedCallback
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>2004</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><pc><lcp><ty>10</ty><ri>LP00000000000000000238</ri><rn>1.2.481.1.999.198.000101_locationPolicy_01</rn><pi>CB00000000000000000007</pi><ct>2016-10-25T19:25:25+09:00</ct><lt>2016-10-25T19:25:40+09:00</lt><lbl>1.2.481.1.999.198.000101</lbl><los>3</los></lcp></pc></m2m:rsp>
name : rsc
value : 2004
=============================================================================
>>>>>>>>>>>>>>>>> step = 12 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>3</op><to>/ThingPlug/v1_0/mgmtCmd-1.2.481.1.999.198.000101_mgmtCmd_01</to><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><pc><mgc><exe>true</exe><ext>ND00000000000000001397</ext></mgc></pc></m2m:req>
MessageArrivedCallback
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>4000</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><RSM>uKey Enter a field value.</RSM><pc></pc></m2m:rsp>
name : rsc
value : 4000
name : RSM
value : uKey Enter a field value.
=============================================================================
>>>>>>>>>>>>>>>>> step = 13 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>3</op><to>/ThingPlug/v1_0/remoteCSE-1.2.481.1.999.198.000101/container-1.2.481.1.999.198.000101_container_01</to><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><dKey>T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==</dKey><pc><cnt><lbl>event</lbl></cnt></pc></m2m:req>
MessageArrivedCallback
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>2004</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><pc><cnt><ty>3</ty><ri>CT00000000000000002399</ri><rn>1.2.481.1.999.198.000101_container_01</rn><pi>RC00000000000000003895</pi><ct>2016-10-25T17:10:27+09:00</ct><lt>2016-10-25T19:25:43+09:00</lt><lbl>event</lbl><st>12</st><cr>RC00000000000000003895</cr><cni>10</cni><cbs>342</cbs></cnt></pc></m2m:rsp>
name : rsc
value : 2004
=============================================================================
>>>>>>>>>>>>>>>>> step = 14 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>3</op><to>/ThingPlug/v1_0/remoteCSE-1.2.481.1.999.198.000101</to><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><dKey>T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==</dKey><pc><csr><csi>1.2.481.1.999.198.000101</csi></csr></pc></m2m:req>
MessageArrivedCallback
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>2004</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><pc><csr><ty>16</ty><ri>RC00000000000000003895</ri><rn>1.2.481.1.999.198.000101</rn><pi>CB00000000000000000007</pi><ct>2016-10-25T17:10:20+09:00</ct><lt>2016-10-25T19:25:44+09:00</lt><acpi>AP00000000000000001799</acpi><cst>3</cst><poa>MQTT|1.2.481.1.999.198.000101</poa><csi>1.2.481.1.999.198.000101</csi><rr>true</rr><nl>ND00000000000000001397</nl></csr></pc></m2m:rsp>
name : rsc
value : 2004
=============================================================================
>>>>>>>>>>>>>>>>> step = 15 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>3</op><to>/ThingPlug/v1_0/node-1.2.481.1.999.198.000101</to><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><nm>1.2.481.1.999.198.000101</nm><dKey>T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==</dKey><pc><nod><ni>1.2.481.1.999.198.000101</ni><hcl>RC00000000000000003895</hcl></nod></pc></m2m:req>
MessageArrivedCallback
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>4105</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><RSM>The nodeLink is already in use.</RSM><pc></pc></m2m:rsp>
name : rsc
value : 4105
name : RSM
value : The nodeLink is already in use.
=============================================================================
>>>>>>>>>>>>>>>>> step = 16 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>4</op><to>/ThingPlug/v1_0/node-1.2.481.1.999.198.000101/areaNwkInfo-1.2.481.1.999.198.000101_areaNwkInfo_01</to><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><dKey>T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==</dKey></m2m:req>
MessageArrivedCallback
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>2002</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><pc></pc></m2m:rsp>
name : rsc
value : 2002
=============================================================================
>>>>>>>>>>>>>>>>> step = 17 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>4</op><to>/ThingPlug/v1_0/remoteCSE-1.2.481.1.999.198.000101/AE-AE00000000000000000279</to><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><dKey>T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==</dKey></m2m:req>
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>2002</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><pc></pc></m2m:rsp>
name : rsc
value : 2002
=============================================================================
>>>>>>>>>>>>>>>>> step = 18 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>4</op><to>/ThingPlug/v1_0/locationPolicy-1.2.481.1.999.198.000101_locationPolicy_01</to><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><dKey>T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==</dKey></m2m:req>
MessageArrivedCallback
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>2002</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><pc></pc></m2m:rsp>
name : rsc
value : 2002
=============================================================================
>>>>>>>>>>>>>>>>> step = 19 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>4</op><to>/ThingPlug/v1_0/remoteCSE-1.2.481.1.999.198.000101/container-1.2.481.1.999.198.000101_container_01/contentInstance-CI00000000000013144405</to><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><dKey>T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==</dKey></m2m:req>
MessageArrivedCallback
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>2002</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><pc></pc></m2m:rsp>
name : rsc
value : 2002
=============================================================================
>>>>>>>>>>>>>>>>> step = 20 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>4</op><to>/ThingPlug/v1_0/mgmtCmd-1.2.481.1.999.198.000101_mgmtCmd_01</to><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><dKey>T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==</dKey></m2m:req>
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>2002</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><pc></pc></m2m:rsp>
name : rsc
value : 2002
=============================================================================
>>>>>>>>>>>>>>>>> step = 21 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>4</op><to>/ThingPlug/v1_0/remoteCSE-1.2.481.1.999.198.000101/container-1.2.481.1.999.198.000101_container_01</to><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><dKey>T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==</dKey></m2m:req>
MessageArrivedCallback
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>2002</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><pc></pc></m2m:rsp>
name : rsc
value : 2002
=============================================================================
>>>>>>>>>>>>>>>>> step = 22 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>4</op><to>/ThingPlug/v1_0/remoteCSE-1.2.481.1.999.198.000101</to><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><dKey>T3BHbitlODRpVXJEbVF5cHhLdVFZVElWRk1Sd2EwMEplWlJaaFVzV0hJZHVlb0x1dktnMnJEZDhjbE92Y0cxeA==</dKey></m2m:req>
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>2002</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><pc></pc></m2m:rsp>
name : rsc
value : 2002
=============================================================================
>>>>>>>>>>>>>>>>> step = 23 <<<<<<<<<<<<<<<<<<<<<<
MQTTPublishMessage
MQTT publish topic :/oneM2M/req/1.2.481.1.999.198.000101/ThingPlug
payload : <m2m:req xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><cty>application/vnd.onem2m-prsp+xml</cty><op>4</op><to>/ThingPlug/v1_0/node-1.2.481.1.999.198.000101</to><ri>1234</ri><fr>1.2.481.1.999.198.000101</fr><nm>1.2.481.1.999.198.000101</nm></m2m:req>
MessageArrivedCallback
MQTT publish Success
MQTTMessageDelivered : 0
mMessageCallback END
MessageArrivedCallback
mMessageCallback START
MQTTMessageArrived
payload : <m2m:rsp xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd"><ri>1234</ri><rsc>2002</rsc><cty>application/vnd.onem2m-res+xml;charset=UTF-8</cty><pc></pc></m2m:rsp>
name : rsc
value : 2002
=============================================================================
>>>>>>>>>>>>>>>>> step = 24 <<<<<<<<<<<<<<<<<<<<<<
mMessageCallback END
MessageArrivedCallback
MQTTDestory
```
SDK를 이용한 ArduinoMiddleware 실행하기
---
1. Arduino IDE를 실행한다.
2. 상단 메뉴에 파일->예제->oneM2M->ArduinoMiddleware 선택한다.
3. 상단 메뉴에 스케치->컴파일을 선택한다.
4. 상단 메뉴에 툴->보드, 포트를 Genuino 101/연결된 포트로 설정한다.
5. 상단 메뉴에 스케치-> 업로드를 선택한다.
6. 업로드가 완료되면 상단 메뉴에 툴->시리얼 모니터를 선택한다.
사진
7. oneM2M 동작 테스트가 시작된다.
8. 결과
