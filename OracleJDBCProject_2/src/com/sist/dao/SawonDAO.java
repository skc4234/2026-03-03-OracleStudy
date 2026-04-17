package com.sist.dao;
// 오라클 연결 => 요청 데이터 검색 / 수정 / 삭제 / 추가
import java.sql.*;
public class SawonDAO {
	// 연결하는 객체
	private Connection conn;
	// 송수신하는 객체
	private PreparedStatement ps;
	
	// 싱글턴 => 단 하나만 생성
	public static SawonDAO dao;

	// 오라클 주소
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	
	// 1. 드라이버 등록 => 단 한번만 수행
	public SawonDAO() {
		try {
			// 리플렉션 - 클래스 이름으로 제어(메모리 저장, 변수값 제어, 메소드 호출)
			// ojdbc8.jar
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// 2. 싱글턴 생성 => 사용자 한명이 Connection 한개만 사용
	public static SawonDAO newInstance() {
		if(dao==null) dao=new SawonDAO();
		return dao;
	}
	
	// 3. 오라클 연결
	public void getConnection() {
		try {
			// conn hr/happy => SQL Plus 생성
			conn = DriverManager.getConnection(URL,"hr","happy");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	// 4. 오라클 연결 해제
	public void disConnetion() {
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
			// exit
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	// ---------------JDBC 공통 부분(MyBatis, JPA는 자동화)------------------------
	
	// 1. 로그인 => COUNT(*)
	
	
	// 2. 사원 목록 출력 => 페이징 => ROWNUM
	// 3. 상세보기 => 사번
	// 4. 통계 => GROUP BY
	
}
