package com.sist.vo;

import java.util.Date;

import lombok.Data;

/*
NO       NOT NULL NUMBER       
ID                VARCHAR2(20) 
PWD      NOT NULL VARCHAR2(10) 
NAME     NOT NULL VARCHAR2(51) 
PHONE    NOT NULL VARCHAR2(14) 
CONTENT  NOT NULL CLOB         
REGDATE           DATE         
ISANSWER          CHAR(1)      
ANSWER            CLOB
 */
@Data
public class SupportVO {
	private int no;
	private String id, pwd, name, phone, content, dbday, isAnswer, answer, title;
	private Date date;
}
