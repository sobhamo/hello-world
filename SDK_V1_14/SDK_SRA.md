## 1. SRA 의 역활
    * IoT 기기들의 다양한 데이터를 원하는 형태로 변환하는 것에 있다. 
    * 데이터 변환에 대한 정책을 관리한다. 

## 2. SRA 폴더 구조
   * SRA ( root )
      * SRA.c.h ( 입력 파라메타 기반으로 ttv byte를 생성한다 )
      * List.c.h ( 리스트 자료구조 )
      * TTVManager.c.h ( TTV Byte 변환 모듈 )
      * TTVBuilder.c.h ( TTV Data 변환 모듈 )
      * TTVDatatype.h ( Datatype 정의 )
      * TTVDefinitions.h ( TTV 규격 정의 )

## 3. 주요 함수
    * void SRAGetTTV(char **out_buf, char type, char datatype, char *value )
    * TTV 데이터 변환을 할 때 사용하는 함수이다.

## 4. 사용 방법

* Raw 데이터를 TTV로 변환하는 방법 :
```
char *ttv;

char type = 0x01; 
char datatype = 0x0a;
char* value = "23.534";
SRAGetTTV( &ttv, type, datatype, value );

printf("%s\n" ttv);
```
