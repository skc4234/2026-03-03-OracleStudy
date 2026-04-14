package com.sist.vo;

import lombok.Data;

// 컬럼 - 변수 매칭
/*
 
NO      NOT NULL NUMBER         
NAME             VARCHAR2(100)  
TYPE             VARCHAR2(100)  
PHONE            VARCHAR2(30)   
ADDRESS          VARCHAR2(260)  
SCORE            NUMBER(2,1)    
PARKING          VARCHAR2(200)  
POSTER           VARCHAR2(260)  
TIME             VARCHAR2(50)   
CONTENT          CLOB           
THEME            VARCHAR2(4000) 
PRICE            VARCHAR2(100)
 */

@Data
public class FoodVO {
	private int no;
	private String name;
	private String type;
	private String phone;
	private String address;
	private double score;
	private String parking;
	private String poster;
	private String time;
	private String content;
	private String theme;
	private String price;
}
