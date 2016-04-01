
#### 2016년 3월 10일 - Version: 0.9.0

## ThingPlug를 위한 Device 미들웨어 설치 및 실행가이드
본 챕터는 SKT ThingPlug Device 미들웨어 설치 및 실행 방법을 서술한다.

#### 1. ThingPlug Device 미들웨어 란?
개방형 사물인터넷 서버 플랫폼 ThingPlug 를 위한 Device 미들웨어는 ThingPlug 와의 연동을 쉽게 해주고 다양한 Device 에 적용 가능한, Device 개발자 및 서비스 개발자를 위한 소프트웨어이다.
![](images/mw_architect.png)
* 미들웨어는 총 4개의 모듈과 5개의 에이전트로 구성되어 있으며, 각각의 모듈간 통신을 통해 사물과 ThingPlug 간 연동을 수행한다.
* **Management Agent** 는 미들웨어 내부의 모든 명령 및 처리의 중심에서 실제적인 역할을 수행한다. User 와의 외부 Interface 는 Gateway Portal, ThingPlug 와는 Connection Ready Agent 와 연동하며, 내부 Device/Sensor 와는 Service Ready Agent 를 통해 연동한다.
* **Service Ready Agent** 는 Sensor Management Agent 로 부터 센서 관련 정보들을 전달받아, 센서별 정책에 따라 데이터를 가공하는 역할을 한다. 가공된 센서 정보는 Management Agent 로 전달한다.
* **Sensor Management Agent** 는 센서 데이터를 수집하고, 직접 제어를 담당하며, Service Ready Agent 와 데이터를 주고 받는다.
* **Gateway Portal** 은 관리자/개발자가 미들웨어 시스템을 제어하고, 각종 정보를 조회할 수 있는 사용자 인터페이스이며, Node.js 기반의 어플리케이션으로 구현되어 있다.

#### 2. 환경 설정

0. 윈도우 사용자의 경우 아래의 URL 에서 putty 를 다운받아 설치한다.
	* http://www.chiark.greenend.org.uk/~sgtatham/putty/download.html
1. 인터넷 연결을 위하여 Ethernet(LAN 케이블)이나 Wi-Fi USB 동글을 장치에 연결한다.
2. 터미널(윈도우 PC에서는 putty)을 열고 각 장치 환경에 따라 네트워크 환경을 설정한다.
3. 처음 실행하는 장치를 업데이트 및 업그레이드 한다.

	```
	# apt-get update
	# apt-get upgrade
	```

#### 3. 미들웨어에서 사용하는 Library 안내
미들웨어에서 사용하는 Library 들은 다음과 같다.
<table>
<thead><tr><th>Part</th><th>Library</th><th>Type</th><th>용도</th></tr></thead>
<tbody>
<tr><td rowspan="7">Gateway Portal</td><td>express</td><td>패키지 포함</td><td>프레임워크</td></tr>
<tr><td>express-session</td><td>패키지 포함</td><td>Express 에 Session 추가</td></tr>
<tr><td>body-parser</td><td>패키지 포함</td><td>Express 에 BodyParser 추가</td></tr>
<tr><td>request</td><td>패키지 포함</td><td>http request 전송</td></tr>
<tr><td>xml2js</td><td>패키지 포함</td><td>XML 파싱</td></tr>
<tr><td>ping</td><td>패키지 포함</td><td>Ping 체크</td></tr>
<tr><td>i18n</td><td>패키지 포함</td><td>다국어 지원</td></tr>
<tr><td rowspan="4">Management Agent</td><td>libcurl</td><td>패키지 포함</td><td>HTTP 통신</td></tr>
<tr><td>libmosquitto</td><td>패키지 포함</td><td>MQTT 통신</td></tr>
<tr><td>libxml2</td><td>shared</td><td>XML 데이터 처리</td></tr>
<tr><td>libsqlite3</td><td>shared</td><td>데이터 저장</td></tr>
<tr><td rowspan="2">공용</td><td>libsodium</td><td>shared</td><td>IPC 통신</td></tr>
<tr><td>lizeromq</td><td>shared</td><td>IPC 통신</td></tr>
</tbody>
</table>

#### 4. 패키지 설치
0. 데비안 패키지 파일을 다운로드 한다.

	```
	# wget https://[TBD]
	```

1. 데비안 패키지를 설치한다.(반드시 root 계정을 이용해야 한다.)	

	* 일반적으로 dpkg 명령을 통하여 패키지를 설치한다.
	```
	# dpkg -i devicemiddleware_arm_1.0.0_20160301.deb
	```
	* Library dependencies 등의 문제가 발생할 경우 gdebi 를 이용하여 패키지를 설치한다.
	```
	# apt-get install gdebi
	# gdebi devicemiddleware_arm_1.0.0_20160301.deb
	```

#### 5. 패키지 설치 확인
브라우저에서 http://IP-address:8000 번으로 접속하여 다음과 같은 화면(Gateway Portal)이 나오면 모든 설치가 완료된 것이다.  
![](images/gpIntro.png)
(로그인 화면에서 아이디 / 비밀번호 : thingplugadmin / adminthingplug)

#### 6. 사용 방법
0. 정지

	```
	# service middleware stop
	```

1. 시작

	```
	# service middleware start
	```

2. 재시작

	```
	# service middleware restart
	```

3. 구동 확인

	```
	# ps xl | grep middleware
	```

4. 제거
	* usr/local/middleware 내부의 모든 파일이 삭제되니 필요한 경우 백업이 필요함

	```
	# dpkg -r devicemiddleware
	```
	
5. UART5/HDMI 형식의 센서 사용법
	* BeagleboneBlack에서 HDMI 포트와 UART포트가 겹치는 현상이 있어 둘중에 하나만 사용이 가능하다.
	* 미들웨어 설치시 HDMI포트 자동으로 Disable하고 UART를 Enable 하기 때문에, HDMI를 다시 사용 할 경우 다음과 같은 절차를 따른다.
	```
	# dpkg -r devicemiddleware
	# vi /boot/uEnv.txt

	##Disable HDMI (v3.8.x)
	#cape_disable=capemgr.disable_partno=BB-BONELT-HDMI,BB-BONELT-HDMIN
	```
	(cape_disable 앞에 # 을 추가한 후 reboot 명령어로 재시작하면 HDMI 포트 사용이 가능해짐과 동시에 UART 사용 불가)


#### 7. 장치 등록
* HTTP 연동 규격 가입자
  * [ThingPlug Device 미들웨어 oneM2M 등록 가이드](oneM2M_Guide.md)
* TCP 연동 규격 가입자
  * [ThingPlug Device 미들웨어 GMMP 등록 가이드](GMMP_Guide.md)

#### 8. 센서
* 센서 가이드
  * [Sensor 연동 가이드](Sensor_Guide.md)
* 장치별 가이드
  * [BeagleBone Black 장치의 센서 드라이버 설치 가이드](BBB_Sensor_Installation.md)
