package com.sist.vo;
/*
EMPNO     NOT NULL NUMBER(5)     
ENAME     NOT NULL VARCHAR2(30)  
GENDER             CHAR(1)       
ADDRESS            VARCHAR2(50)  
AGE                NUMBER(3)     
POSITION           VARCHAR2(20)  
LOCATION           VARCHAR2(30)  
SALARY             NUMBER(10,2)  
PHONE              VARCHAR2(20)  
INTRO              VARCHAR2(100) 
DEPTNO    NOT NULL NUMBER(2)     
HIRE_DATE          DATE

NUMBER   => int
DATE     => java.util.Date
VARCHAR2 => String
CHAR     => String

TO_CHAR  => String
TO_DATE  => Date

TO_CHAR(salary, 'L999,999')

 */

import java.util.*;
import lombok.Data;

// 데이터베이스의 ROW와 매칭 => DTO(MyBatis) / ENTITY(INSERT 자동화, JPA) / BEAN(JSP)
@Data
public class SawonVO {
	private int empno, age, deptno, salary;
	private String ename, gender, address, position, location, phone, intro, dbday, pay;
	private Date hire_date;
	
}
