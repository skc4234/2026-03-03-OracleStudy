package com.sist.vo;

import java.util.Date;

import lombok.Data;

/*
NO      NOT NULL NUMBER       
ID               VARCHAR2(20) 
TYPE    NOT NULL NUMBER       
GNO     NOT NULL NUMBER       
ACCOUNT          NUMBER       
PRICE            NUMBER       
REGDATE          DATE
 */
@Data
public class BuyVO {
	private int no, type, gno, account, price;
	private String id, dbday;
	private Date regdate;
	private GoodsVO bvo = new GoodsVO(); // JOIN 처리 => VO에 클래스를 포함한다
}
