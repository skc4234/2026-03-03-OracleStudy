# Oracle Study (04/10~)



***
# 목차
1. [JDBC 오라클 연결](#0410---jdbc-오라클-연결)
2. [데이터베이스 개념, 발전 과정 / 릴레이션과 무결성 / DQL-SELECT 기초 정리](#0413---데이터베이스-개념-발전-과정--릴레이션과-무결성--dql-select-기초-정리)
3. [SELECT(형식, 내장 함수-집계 함수, 단일행 함수, WHERE, ORDER BY, GROUP BY, HAVING)](#0414-0415---select형식-내장-함수-집계-함수-단일행-함수-where-order-by-group-by-having)
4. [JOIN, SubQuery](#0416---join-subquery)
5. [DDL, DML](#0417---ddl-dml)
6. [ROWNUM, SubQuery, VIEW, SEQUENCE](#0420---rownum-subquery-view-sequence)
***


## 04/10 - JDBC 오라클 연결
OracleFirstProject 참조
***


## 04/13 - 데이터베이스 개념, 발전 과정 / 릴레이션과 무결성 / SQL 기초 정리
<details><summary>숨기기/펼치기</summary>


1. 데이터베이스
   => 조직에 필요한 정보를 얻기 위해 논리적으로 연관된 데이터를 모아놓은 것
2. 데이터베이스의 개념
   - 통합된 데이터 : 데이터를 한 곳으로 통합해서 중복제거, 일관성 유지, 무결성의 원칙을 지킨다
   - 저장된 데이터 : 직접 지우기 전까진 저장장치에 계속 저장되어 있다
   - 운영 데이터 : 업무를 위한 검색을 할 목적으로 저장된 데이터
   - 공용 데이터 : 한 사람이 아니라 여러명이 접근할 수 있다
3. 데이터베이스의 특징
   - 실시간 접근성 : 종료되지 않고 실시간으로 사용자에게 서비스를 제공한다
   - 계속적인 변화 : 데이터는 시간에 따라 삽입, 삭제, 추가 등 계속 변화한다
   - 동시 공유 : 데이터는 여러 사람에게 공유된다
   - 내용에 따른 참조 : 메모리 주소가 아닌 실제 데이터값을 참조한다
4. 데이터베이스 시스템 구성
   - DBMS : 데이터베이스를 사용자가 사용할 수 있게 연결시켜주는 소프트웨어
   - 데이터베이스 : 데이터를 모아 둔 토대, 물리적으로는 데이터들이 실제 저장된 곳(하드디스크 등)
   - 데이터 모델 : 데이터가 저장되는 기법(스타일)
5. 정보 시스템의 발전
   - 파일 시스템 - 각 프로그램이 독립적으로 처리 -> 중복, 일관성 훼손
   - 계층형 시스템 - 트리구조, 한방향으로만 처리
   - 네트워크 시스템 - 클라이언트/서버
   - **관계형 데이터베이스 시스템** - 데이터를 종류별로 나눠서 저장하고 체계적으로 관리한다
   - 분산 데이터베이스 시스템 - 각각 데이터베이스를 이용하여 필요시 상호연동
   - 최근에는 비정형화된 데이터를 정형화해서 예측 가능한 모델로 처리한다 => AI
6. 파일 시스템 VS DBMS
   - 데이터 정의 : 응용프로그램 / DBMS(오라클 사용)
   - 데이터 저장 : 파일            / 오라클
   - 데이터 접근 : 응용프로그램 / SQL 언어
   - 사용 언어 : 자바,C,C++...    / 자바, SQL
   - CPU/주기억장치 : 적음      / 많음
7. 데이터베이스 사용자
     - 일반 사용자 : SQL을 모르면서 일반적인 검색,삭제,수정 등의 작업만 하는 사용자
      	   => 검색어를 입력하면 프로그래머가 SQL 문장을 제작 => LIKE '%검색어%'
	 - **응용 프로그래머** : 데이터 처리 로직을 만든다(자바, SQL 등 사용)
	 - SQL 사용자 : 업무 담당자 => SQL문 사용, 전산실
	 - 데이터베이스 관리자(DBA) : 보안 관리, 설계, 유지보수 등
8. 관계 데이터 모델
   - 데이터베이스 모델
	 - 계층 데이터 모델
	 - 네트워크 데이터 모델
	 - **관계 데이터 모델**
	 - 객체 데이터 모델
	 - 객체-관계 데이터베이스
9. 릴레이션, 튜플, 애트리뷰트
    - 릴레이션(=TABLE) : 행과 열로 구성된 데이터 테이블
    - 튜플(=ROW) : 행, 클래스의 객체 인스턴스
    - 애트리뷰트(=COLUMN) : 열, 클래스의 멤버변수
    - 도메인 : 속성(=COLUMN)의 집합
10. 릴레이션의 특징
	   - 속성(컬럼)은 단일 값이다
	   - 속성은 중복이 없다
	   - 속성은 순서가 없다
	   - 튜플도 순서가 없다
11. 데이터 무결성 / 참조 무결성
       - 이상현상 반드시 방지(수정, 추가, 삭제) => 원하지 않는 데이터 변경 위험
	   - 반드시 중복없는 데이터 사용 => PRIMARY KEY
	   - 이미 사용중인 데이터를 참조할 수 있게 만든다 => FOREIGN KEY
12. KEY의 종류
	   - 각 데이터를 구별하기 위한 값(구분자)  ex) 배열/List의 index
		  => 기본키(PRIMARY KEY) => 유일값 => 번호
		  => 영화번호, 도서번호, 게시물 번호 등
		  => CREATE SEQUENCE : 자동 번호 생성
		  => 데이터 무결성 목적으로 반드시 한 개 이상이 존재(NOT NULL)
	   - 후보키
		  => 기본키가 될 수 있는 키
		  => ID(PK)를 대신하는 후보키 : 주민번호(보안 위험) / 전화번호 / 이메일 주소
							=> NOT UNIQUE : 중복 불가
	   - 대체 키
		  => 후보키 중에 기본키로 선택 안된 키
		  => 이메일 주소, 주민번호 등
	   - 외래키(FOREIGN KEY) : 참조 무결성
		  => 다른 테이블의 기본키를 이용해서 연결

		      ```
      		  게시판(게시판 번호(PK)) - 댓글(게시판 번호 포함)                       
              회원(ID(PK)) - 예약(회원 ID)
      		  ```                  
	   - 슈퍼키 / 대리키 등

13. 관계대수
	   => 데이터를 찾는 방법
	   - 셀렉션
		  => 조건에 맞는 데이터를 찾는 경우
		  => WHERE절 사용
	   - 프로젝션
		  => 출력에 필요한 컬럼만 선택
	   - 조인
		  => 서로 다른 테이블을 연결해서 사용할 때
	   - 집합연산자
		  => 합집합 / 차집합 / 교집합 / 카디션 프로덕트
14. DQL - SELECT 문장
     - 형식/순서
		  ```
          SELECT *(ALL) / 원하는 컬럼지정(column list)
		  FROM 테이블명;
		  => 필수 조건
		  [
		  WHERE 조건(연산자)
		  GROUP BY(그룹 컬럼)
		  HAVING(그룹 조건, GROUP BY가 있을때만 사용)
		  ORDER BY(정렬 컬럼) DESC(내림차순)/ASC(오름차순)
		  ]
		  => 옵션, 순서 지켜야함
          ```
15. SQL의 종류
	 - DML(데이터 조작어) :  **SELECT => 데이터 검색(형식 많음), INSERT => 데이터 추가, UPDATE => 데이터 수정, DELETE => 데이터 삭제
		- SELECT는 검색
		- INSERT, UPDATE, DELETE는 데이터가 변하기 때문에 반드시 COMMIT; 필요
	 - DDL(데이터 정의어) : 테이블 / 뷰 / 시퀀스 / 인덱스 / 시노님 / 함수 / 프로시저 / 트리거 등을 만들 때 사용
	    - CREATE : 생성, DROP : 삭제, ALTER : 변경, TRUNCATE : 데이터 잘라내기, RENAME : 이름 변경
	 - DCL(데이터 제어어) : 데이터 권한 관리
	    - GRANT : 권한 부여, REVOKE : 권한 해제
     - TCL(트랜잭션 제어어)
		- COMMIT : 정상적으로 저장, ROLLBACK : 명령문 전체 취소, SAVEPOINT : 원하는 부분만 취소
</details>

***


## 04/14, 04/15 - SELECT(형식, 내장 함수-집계 함수, 단일행 함수, WHERE, ORDER BY, GROUP BY, HAVING)
<details><summary>숨기기/펼치기</summary>

	
- SQL
	- DQL(데이터 검색)
		```
  		SELECT * / column_list
		FROM table_name
		[
		WHERE ------- 연산자(true/false)
		GROUP BY --- 함수
		HAVING ------ 집계함수
		ORDER BY ---- 컬럼 순서
		]
   		```
		- 동작 순서 : FROM - WHERE - GROUP BY - HAVING - SELECT - ORDER  BY
		- 연산자
			- 산술 연산자 => ROW 단위 통계 => +, -, *, /
				- + : 덧셈, / : 정수/정수=실수, 0으로 나눌 수 없다
			- 비교 연산자 => =, (!=, <>, ^=), <, >, <=, >=
			- 논리 연산자 => AND, OR, NOT => !(X)
			- BETWEEN A AND B : 기간, 범위
			- IN(OR 대체 연산자)
			- IS NULL / IS NOT NULL => NULL 처리
			- LIKE : _, % 사용 => 문자열 포함 검색 가능
				- contains : %?%, startsWith : ?%, endsWith : %?
		- 내장 함수(단일행 함수, 집계 함수)
			- 집계 함수 : ROW
				- COUNT : ROW의 개수
				- MAX / MIN : COLUMN의 최대값/최소값
				- SUM : COLUMN 전체의 합
				- AVG : COLUMN 전체 평균
				- RANK / DENSE_RANK : COLUMN 순위 => 1 2 2 4 / 1 2 2 3
			- 단일행 함수
				- 문자 함수
					- LENGTH
					- SUBSTR
					- INSTR
					- RPAD
				- 숫자 함수
					- CEIL
					- ROUND
					- TRUNC
					- MOD
				- 날짜 함수
					- SYSDATE
					- MONTS_BETWEEN
				- 변환 함수
					- TO_CHAR
					- TO_DATE
				- 기타 함수
					- NVL
					- CASE

	- DML(데이터 조작)
	- DDL(데이터 정의)
	- DCL(데이터 제어)
	- TCL(트랜젝션 제어)
</details>

***

## 04/16 - JOIN, SubQuery
<details><summary>숨기기/펼치기</summary>

	
- JOIN : 두 개 이상의 테이블을 연결해서 출력에 필요한 데이터를 추출하는 과정
	- 오라클 조인 : 오라클에서만 사용
	- ANSI 조인 : 데이터베이스 표준
	- **INNER JOIN** : 교집합
			- **EQUI_JOIN
			- NON_EQUI_JOIN : 범위 지정 => BETWEEN~AND
			- NULL 값은 처리 불가
	- OUTER JOIN : INNER JOIN을 보안 => NULL값 처리 가능
		- LEFT OUTER JOIN => 왼쪽에 처리 안된 데이터 읽기
		- RIGHT OUTER JOIN => 오른쪽에 처리 안된 데이터 읽기
		- FULL OUTER JOIN => 양쪽에 있는 모든 데이터 읽기
		- Admin에서 주로 사용
		- **INNER JOIN과 OUTER JOIN은 컬럼명이 다를 수 있다**
	- 자연 조인(NATURAL JOIN)
	- USING : JOIN~USING
   		- **컬럼명이 같아야 한다**

	- 형식
  		- INNER JOIN(오라클 조인)
      		```
			SELECT A.col, B.col
			FROM A,B
			WHERE A.col = B.col => 테이블 별칭 사용 가능

			SELECT a.col, b.col
			FROM A a, B b
			WHERE a.col=b.col
			=> 컬럼이 같으면 반드시 구분
			```
		- **ANSI JOIN**
    		```
			SELECT A.col, B.col
			FROM A JOIN B
      		ON A.col = B.col
			```
- SubQuery : 쿼리 안에 쿼리문장
	- DML 전체에서 사용 가능(INSERT, UPDATE, DELETE)
	- 종류
		- WHERE 뒤에 일반 서브쿼리 => 조건문
		- SELECT 뒤에 **스칼라 서브쿼리**(JOIN 대체) => 컬럼
		- FROM 뒤에 **인라인뷰** => 테이블 => 페이징 기법
	- 사용법
   		- WHERE 뒤에 일반 서브쿼리
       		```
			MainQuery
			WHERE (SUBQUERY)
         	```
	  	- SELECT 뒤에 스칼라 서브쿼리
   	  		```
			SELECT (SUBQUERY)
			FROM ...
			WHERE ...
   	    	```
		- FROM 뒤에 인라인뷰
    		```
			SELECT *
			FROM (SUBQUERY)
			WHERE 조건문
			```
</details>

***


## 04/17 - DDL, DML
<details><summary>숨기기/펼치기</summary>

	
- DDL(데이터 정의어) => 단위가 COLUMN
	- CREATE : 생성
	- TALBE : 데이터를 저장하는 공간
	- SEQUENCE : 자동 증가 번호 => 게시판 No.
	- VIEW : 가상 테이블 => SELECT로 저장
	- INDEX : 검색 최적화 / 정렬
	- FUNCTION / PROCEDURE / TRIGGER : 함수, 기능 추가 => 5장
		- Auto Commit => 자동 저장(Commit 명령어 사용 안해도됨)
	- ALTER : 추가 / 수정 / 삭제
		- ADD / MODIFY / DROP
	- DROP : 전체 삭제
	- RENAME : 테이블 이름 변경 => 리팩토링
	- TRUNCATE : 테이블 유지, 데이터만 삭제
- CREATE
	```
	CREATE TABLE table_name (
		[컬럼] [데이터형] [제약조건][제약조건], ... => NOT NULL / DEFAULT
		[컬럼] [데이터형] [제약조건][제약조건], ...
		[컬럼] [데이터형] [제약조건][제약조건], ...
		...
		[제약조건], => PK, FK, UK, CK
		[제약조건]
	);
	```
	- 제약조건
   		- NOT NULL
			- 반드시 입력을 필요로 하는 경우 => NOT NULL
		- UNIQUE : 중복 금지
			=> email / phone =>  후보키
		- PRIMARY KEY : NOT NULL + UNIQUE
			-  데이터 무결성 => 테이블 제작 시 반드시 한개 이상 추가
		- FOREIGN KEY : 다른 테이블과 연결 => 외래키/참조키 
		- CHECK : 지정된 데이터만 출력
			-  radio / select(콤보박스) => 부서명 / 근무지 / 장르
		- DEFAULT : 미리 데이터 설정
			- regdate DATE DEFAULT SYSDATE
   - 사용법
  	```
   CREATE TABLE board (
    	no NUMBER,
    	name VARCHAR2(51) CONSTRAINT board_name_nn NOT NULL,
    	subject VARCHAR2(4000) CONSTRAINT board_subject_nn NOT NULL,
    	content CLOB CONSTRAINT board_content_nn NOT NULL,
    	pwd VARCHAR2(20) CONSTRAINT board_pwd_nn NOT NULL,
    	regdate DATE DEFAULT SYSDATE,
   		hit NUMBER DEFAULT 0,
	    CONSTRAINT board_no_pk PRIMARY KEY(no)
	);
	
	CREATE TABLE reply (
    	no NUMBER,
    	bno NUMBER,
    	id VARCHAR2(20) CONSTRAINT reply_id_nn NOT NULL,
    	name VARCHAR2(51) CONSTRAINT reply_name_nn NOT NULL,
    	sex CHAR(6),
    	msg CLOB CONSTRAINT reply_msg_nn NOT NULL,
    	regdate DATE DEFAULT SYSDATE,
    	CONSTRAINT reply_no_pk PRIMARY KEY(no),
    	CONSTRAINT reply_bno_fk FOREIGN KEY(bno) REFERENCES board(no) ON DELETE CASCADE,
    	CONSTRAINT reply_sex_ck CHECK(sex IN('남자','여자'))
	);
- ALTER : 삽입(ADD), 수정(MODIFY), 삭제(DROP)
  	- 사용법
  	 	```
  	  	-- 제약조건 제거
		ALTER TABLE board DROP CONSTRAINT board_name_nn;

		-- 제약조건 변경
		ALTER TABLE board MODIFY name VARCHAR(51) CONSTRAINT board_name_nn NOT NULL;

  	  	-- 컬럼, 제약조건 추가
		ALTER TABLE board ADD email VARCHAR2(20) CONSTRAINT board_email_UK UNIQUE;

		-- 컬럼 변경
		ALTER TABLE board MODIFY email VARCHAR2(200);

		-- 컬럼 삭제
		ALTER TABLE board DROP COLUMN email;
		```  
- DROP : 테이블 + ROW 전체 삭제
  	```
  	DROP TABLE [테이블명];
	```
   
- INSERT
	- 형식
		- 전체 값 추가
		```
		// board에 있는 모든 값 추가
		// DEFAULT가 있어도 반드시 값을 넣어야 한다
		INSERT INTO board VALUES(값, 값, ...); // 문자열이나 날짜가 들어갈때는 작은따옴표 사용
		```
		- 지정된 값만 추가
		```
		// 지정하지 않은 값은 NULL 또는 DEFAULT 값
		INSERT INTO board(no, name, subject, content, pwd) VALUES(지정된 값만 추가)
		```
- UPDATE
	- 형식
		- 전체 수정
		```
		// 모든 값이 한번에 수정
		UPDATE table_name SET
		컬럼=값, 컬럼=값...
		```
  		- 조건에 맞는 데이터만 수정
		```
		// WHERE 뒤에 조건 추가
		UPDATE table_name SET
		컬럼=값, 컬럼=값
		WHERE 조건;
		```
-  DELETE
	- 형식
		- 전체 삭제
		```
		DELETE FROM table_name;
		```
		- 조건에 맞는 데이터 삭제
		```
		DELETE FROM table_name
		WHERE 조건;
		```
- INSERT / UPDATE 는 제약조건을 반드시 확인
</details>

***



## 04/20 - ROWNUM, SubQuery, VIEW, SEQUENCE

<details><summary>ROWNUM</summary>

- 오라클에서 지원하는 가상 컬럼 => ROWNUM / ROWID(INDEX에서 사용)
- 가상 컬럼은 모든 테이블이 갖고 있다
- 오라클에서 순차적으로 ROW에 번호를 부여
- 사용처
	- 인기 순위, 페이징, 게시판 상세보기(이전/다음)
- 가상 컬럼의 단점 : TOP-N => 처음부터 N까지 => 중간부터 불가
</details>

<details><summary>SubQuery</summary>
	
- 일반 서브쿼리 => 조건값 => WHERE 뒤에
	- 단일행 서브쿼리 => 결과값 1개
	- 다중행 서브쿼리 => 결과값 여러개
	- 다중컬럼 서브쿼리 => 컬럼 여러개
	```
	SELECT 
	FROM table_name
	WHERE 컬럼 연산자 (SELECT ~)
			  --------
	// 비교 연산자 : =, >, <, >=, <=, <>  ===> 서브쿼리의 결과값이 1개 일때
	// 집합 연산자 : IN, NOT IN
	// 한정 연산자 : ANY, SOME, ALL => 결과값 중에 1개 결정 => MAX, MIN
	// 존재 연산자 : EXISTS, NOT EXISTS
	```
- 인라인뷰 : 테이블 대신 사용 => 한번만 사용(보안/페이징 기법) => FROM 뒤에
	```
	// 테이블 역할
	SELECT ~
	FROM (SELECT ~)
	// SELECT가 안보이게 만드는 과정 : VIEW
	```
	- 없는 데이터가 있는 경우에 오류
- 스칼라 서브쿼리 : 컬럼 대신 사용 : JOIN 대체 => SELECT 뒤에
	- SELECT 안에 들어가는 서브쿼리
	- 반드시 결과값이 1개
	- 주로 집계함수와 사용
	- CASE 문장과 같이 쓰임
- 스칼라 서브쿼리 / 인라인뷰 => 저장이 안됨(보안)
- 컬럼 1개            임시 테이블
</details>

<details><summary>VIEW</summary>
	
- 하나 이상의 테이블을 연결해서 만든 가상 테이블
- 보여만 주는 역할(SELECT) => 단순 뷰에서는 DML 가능(INSERT, DELETE)
- 장점
	- 편리성 : SQL 문장을 간결하게 만들 수 있다
	- 재사용성 : 자주 사용하는 SQL문을 뷰로 정의해두면 재사용이 가능
	- 보안성 : 데이터가 저장이 안된 상태 => 단순한 SELECT 문장으로만 DB에 저장
	- 응용프로그램(웹, 애플리케이션)에서 사용하기 편리하다
	- SQL 문장이 간결하기 때문에 오류가 거의 없다
	- JOIN / SUBQUERY 등 SQL 문장이 복잡할 때 VIEW 사용
- 단점
	- VIEW에서 DML이 되는 것이 아니라 실제 참조하는 테이블에 영향을 미침
   	- 데이터가 많으면 속도 저하가 크다 => JOIN 사용
- 뷰의 종류
	- 단순 뷰 : 한 개의 테이블 참조(거의 사용 x)
	- 복합 뷰 : 두 개 이상의 테이블을 참조(조인, 서브쿼리 등)
	- 인라인뷰 : 임시 테이블을 만들어서 처리(한번만 사용)
- 생성
	```
	CREATE VIEW view_name // VIEW는 SELECT 문장을 저장하고 있음
	AS
	SELECT ~
 	[옵션]
 	- WITH CHECK OPTION : DML이 가능
	- WITH READ ONLY : 읽기 전용
  	// 저장된 내용 확인
	SELECT text FROM user_views
	WHERE view_name='대문자';
	```
- 수정
	```
	// 이미 있으면 REPLACE, 없으면 CREATE
	CREATE OR REPLACE VIEW view_name
	AS
	SELECT~
	```
- 삭제
	```
	DROP VIEW view_name;
	```
</details>

<details><summary>SEQUENCE</summary>

- 자동 증가 번호
- 테이블 + 시퀀스 => PRIMARY KEY 값을 만들어 줌
- 테이블당 시퀀스는 한개만 사용한다
- 사용처 : 게시물 번호, 맛집 번호, 영화 번호, 예약 번호
- 시퀀스 생성
	```
	CREATE SEQUENCE seq_name
	옵션 설정  // 시작번호, 증가, MAX값 설정
	```
- 시퀀스 옵션
	- START WITH 1 : 1부터 시작
	- INCREMENT BY 1 : 1씩 증가
	- NOCYCLE : 무한한 값
	- NOCACHE : 미리 번호 생성 없이 처리
- 현재 값 / 다음 값
	- seq_name.currval / seq_name.nextval
- 시퀀스 삭제
	```
	DROP SEQUENCE seq_name;
	```
- 시퀀스는 독립적이기 때문에 테이블을 삭제해도 시퀀스는 남아있다
- 복구하려면 삭제 후에 다시 생성
</details>

***
