## oneM2M 등록과 주기보고 및 제어하기
본 챕터는 SKT ThingPlug Device 미들웨어를 통한 oneM2M 등록과정과 주기보고 및 제어하기에 대하여 서술한다.

#### 1. 등록과 주기보고
등록과 주기보고는 아래 그림에서 좌측부터 우측으로 진행된다.
![](images/Regi&ReportFlow.png)


* **등록**
  * nodeCreate
  Device로부터 node 등록을 요청받는 인터페이스.
	```xml
*   Trying 61.250.21.212...
* Connected to onem2m.sktiot.com (61.250.21.212) port 9000 (#0)
> POST /ThingPlug HTTP/1.1
Host: onem2m.sktiot.com:9000
Accept: application/xml
Content-Type: application/vnd.onem2m-res+xml;ty=14
X-M2M-RI: 744653
X-M2M-Origin: 1.2.481.1.999.130.3000005
X-M2M-NM: 1.2.481.1.999.130.3000005
Content-Length: 193
<?xml version="1.0" encoding="UTF-8"?>
<m2m:nod xmlns:m2m="http://www.onem2m.org/xml/protocols"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<ni>1.2.481.1.999.130.3000005</ni>
</m2m:nod>

HTTP/1.1 201 Created
Server: Apache-Coyote/1.1
Set-Cookie: JSESSIONID=0939F0F442FD552733BAE20494CBA2E8; Path=/; HttpOnly
X-M2M-RI: 74465340a4f732-8d02-407a-923d-39e83a57bfe7
X-M2M-RSC: 2001
Content-Location: /ThingPlug/node-1.2.481.1.999.130.3000005
Content-Type: application/vnd.onem2m-res+xml;charset=UTF-8
Content-Length: 414
Date: Mon, 09 May 2016 07:16:56 GMT
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<m2m:nod xmlns:m2m="http://www.onem2m.org/xml/protocols"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<ty>14</ty>
	<ri>ND00000000000000001021</ri>
	<rn>1.2.481.1.999.130.3000005</rn>
	<pi>ThingPlug</pi>
	<ct>2016-05-09T16:16:56+09:00</ct>
	<lt>2016-05-09T16:16:56+09:00</lt>
	<lbl></lbl>
	<acpi></acpi>
	<at></at>
	<aa></aa>
	<ni>1.2.481.1.999.130.3000005</ni>
</m2m:nod>
	```

  * remoteCSECreate 
  Device로부터 remoteCSE 등록을 요청받는 인터페이스.
	```xml
*   Trying 61.250.21.212...
* Connected to onem2m.sktiot.com (61.250.21.212) port 9000 (#0)
> POST /ThingPlug HTTP/1.1
Host: onem2m.sktiot.com:9000
Accept: application/xml
Content-Type: application/vnd.onem2m-res+xml;ty=16
X-M2M-RI: 783715
X-M2M-Origin: 1.2.481.1.999.130.3000005
passCode: ******
X-M2M-NM: 1.2.481.1.999.130.3000005
Content-Length: 292
<?xml version="1.0" encoding="UTF-8"?>
<m2m:csr xmlns:m2m="http://www.onem2m.org/xml/protocols"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<cst>1</cst>
	<poa>MQTT|1.2.481.1.999.130.3000005</poa>
	<csi>1.2.481.1.999.130.3000005</csi>
	<rr>true</rr>
	<nl>ND00000000000000001021</nl>
</m2m:csr>

HTTP/1.1 201 Created
Server: Apache-Coyote/1.1
Set-Cookie: JSESSIONID=143E3C68954CD258653E58689949FCEA; Path=/; HttpOnly
X-M2M-RI: 78371548f298f8-a800-4d5b-bef0-fdcc732b2fd1
X-M2M-RSC: 2001
Content-Location: /ThingPlug/remoteCSE-1.2.481.1.999.130.3000005
dKey: OGs2RjJYZitiS1hqcmJjSHFNTG5hR1pQRXNkK2FCNStHcUxlL21Rd3FackI1S0tBNW1pNVFrckVSY0QxaVMrcw==
Content-Type: application/vnd.onem2m-res+xml;charset=UTF-8
Content-Length: 535
Date: Mon, 09 May 2016 07:17:15 GMT
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<m2m:csr xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
<ty>16</ty>
<ri>RC00000000000000001050</ri>
<rn>1.2.481.1.999.130.3000005</rn>
<pi>ThingPlug</pi>
<ct>2016-05-09T16:17:14+09:00</ct>
<lt>2016-05-09T16:17:14+09:00</lt>
<lbl></lbl>
<acpi>AP00000000000000002130</acpi>
<at></at>
<aa></aa>
<cst>1</cst>
<poa>MQTT|1.2.481.1.999.130.3000005</poa>
<csi>1.2.481.1.999.130.3000005</csi>
<rr>true</rr>
<nl>ND00000000000000001021</nl>
</m2m:csr>
	```


  * containerCreate-rc
  remoteCSE 등록 완료 후 Device로부터 remoteCSE의 container 생성을 요청받는 인터페이스
	```xml
*   Trying 61.250.21.212...
* Connected to onem2m.sktiot.com (61.250.21.212) port 9000 (#0)
> POST /ThingPlug/remoteCSE-1.2.481.1.999.130.3000005 HTTP/1.1
Host: onem2m.sktiot.com:9000
Accept: application/xml
Content-Type: application/vnd.onem2m-res+xml;ty=3
X-M2M-RI: 480863
X-M2M-Origin: 1.2.481.1.999.130.3000005
dKey: OGs2RjJYZitiS1hqcmJjSHFNTG5hR1pQRX****
X-M2M-NM: beagleboneblack
locale: en
Content-Length: 159
<?xml version="1.0" encoding="UTF-8"?>
<m2m:cnt xmlns:m2m="http://www.onem2m.org/xml/protocols"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
</m2m:cnt>

HTTP/1.1 201 Created
Server: Apache-Coyote/1.1
Set-Cookie: JSESSIONID=9039790E2F31CD73693CEC1CFACAE978; Path=/; HttpOnly
X-M2M-RI: 4808635e3edbc9-1354-4346-b334-38a6af169e8c
X-M2M-RSC: 2001
Content-Location: /ThingPlug/remoteCSE-1.2.481.1.999.130.3000005/container-beagleboneblack
Content-Type: application/vnd.onem2m-res+xml;charset=UTF-8
Content-Length: 434
Date: Mon, 09 May 2016 07:17:31 GMT
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<m2m:cnt xmlns:m2m="http://www.onem2m.org/xml/protocols"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
<ty>3</ty>
<ri>CT00000000000000002297</ri>
<rn>beagleboneblack</rn>
<pi>RC00000000000000001050</pi>
<ct>2016-05-09T16:17:32+09:00</ct>
<lt>2016-05-09T16:17:32+09:00</lt>
<lbl></lbl>
<at></at>
<aa></aa>
<st>0</st>
<cr>RC00000000000000001050</cr>
<cni>0</cni>
<cbs>0</cbs>
</m2m:cnt>
	```


* 주기보고
* 

