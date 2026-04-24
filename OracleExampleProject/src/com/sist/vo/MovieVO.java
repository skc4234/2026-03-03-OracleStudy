package com.sist.vo;

import lombok.Data;

/*
 * - 변수 매칭
 *  오라클  =>  자바
 *  CHAR
 *  VARCHAR2  String
 *  CLOB
 *  ----------------------
 *  NUMBER    int, double
 *  ----------------------
 *  DATE      Date
 *  
 *  오라클 ====> 윈도우
 * 		 =====> 브라우저(웹)
 *        자바
 *        
 *        
MNO      NOT NULL NUMBER(4)     
TITLE             VARCHAR2(100) 
GENRE             VARCHAR2(100) 
POSTER            VARCHAR2(200) 
ACTOR             VARCHAR2(300) 
REGDATE           VARCHAR2(100) 
GRADE             VARCHAR2(50)  
DIRECTOR          VARCHAR2(100)
 */
@Data
public class MovieVO {
	private int mno;
	private String title, genre, poster, actor, regdate, grade, director;
}
