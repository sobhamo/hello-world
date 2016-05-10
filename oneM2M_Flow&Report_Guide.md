## oneM2M 등록과 주기보고 및 제어하기
본 챕터는 SKT ThingPlug Device 미들웨어를 통한 oneM2M 등록과정과 주기보고 및 제어하기에 대하여 서술한다.

#### 1. 등록과 주기보고
등록과 주기보고는 아래 그림에서 좌측부터 우측으로 진행된다.
![](images/Regi&ReportFlow.png)


* **등록**
  * node CREATE

	```c
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


* 주기보고
* 

