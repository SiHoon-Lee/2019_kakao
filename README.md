#### http_test Directory
- bulk_test.http : 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API
- search_test.http : 검색 API
- update_test.http : 수정 API

### 문제해결 방법
- Insert : opencsv를 이용하여 csv기준으로 파싱 -> 저장하는 방식으로 해결하였으며 region을 PK로 잡아 수정에 용이하도록 잡았습니다.
- Update : region을 기준으로 업데이트되는 데이터만 업데이트하도록 하였습니다.
- Select : QueryDSL를 이용하여 Query를 명확하게 이해할 수 있도록 하였습니다.


### 실행 방법
 1. sql 디렉토리의 ddl.sql을 Mysql에 적용한다.
 2. resources/application.yml 파일에 datasource.url의 ip/host를 환경에 맡게 적용한다.
 3. Gradle Task 실행
    - compileQuerydsl을 실행하여 Q파일 생성
 4. SpringBoot를 실행한다. (ProblemApplication.java)

### 개발 프레임워크
- java 1.8
- opencsv 4.5
- queryDsl
- spring boot
- Mysql
- Junit5
- Lombok
- H2