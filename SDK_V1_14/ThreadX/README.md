# ThreadX
## PC 시스템의 최소 요건
  * 운영 시스템 : Windows® 7 (32비트, 64비트)
  * 프로세서 : Intel® Core™ 패밀리 프로세서, 2.0 GHz 이상 (또는 동등 프로세서)
  * 메모리 용량 : 최소 8 GB
  * 하드디스크 용량 : 최소 250 GB
  * 디스플레이 : 해상도 1024 x 768 이상, 65,536 컬러 이상
  * 인터페이스	: USB 2.0
## 지원사양
  1. Footprint
    + CPU : 50MHz 이상
    + RAM : 64KB 이상
    + Flash memory : 256KB 이상
  2. Main Board
    * S7G2 Renesas Synergy™ S7 MCU 32-Bit ARM® Cortex®-M4 Embedded Evaluation Board
    ![image.png](images/renesas_s7g2.png)
## Source Tree

+ ThreadX (project root)
	+ Debug ( elf 파일 및 컴파일 결과물 )
		+ script ( ld 파일 )
		+ src (FreeRTOS 및 WIZnet driver configuration headers)
			+ mbedtls ( tls library )
			+ MQTT ( mqtt library )
			+ oneM2M ( oneM2M library )
				+ net (MQTT handler)
				+ Versions (ThingPlug oneM2M v1.14 API)
			+ sample
				+ MA (ManagementAgent)
				+ SMA (SensorManagementAgent)
				+ SRA (ServiceReadyAgent)
			+ synergy_gen ( auto generated source code )
		+ synergy ( board & driver source )
		+ synergy_cfg ( board & driver configuration source )
# Project build
## IDE Setting & Build & Run
1. https://synergygallery.renesas.com -> 개발툴 -> e2studio ISDE -> 다운로드
2. 압축 해제 후 설치
3. e2studio 실행
4. File->Import->General->Existing Projects into Workspace 선택하고 Select root directory 에서 소스 추가
5. e2studio의 추가된 프로젝트에서 configuration.xml을 클릭한다.
6. Generate Project Content를 클릭한다.
7. Run->Debug Configurations 클릭하고 새창에서 Renesas GDB Hardware Debugging 클릭한다.
8. Run->Resume 선택하여, 프로그램을 시작한다.
9. 프로그램이 시작된다.
