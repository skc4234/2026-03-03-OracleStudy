package com.sist.dao;
// 사용자 요청 시에 오라클 연결 처리
// Data Access Object
/*
 *  데이터베이스는 읽어오는 단위가 ROW
 *  
 * 
 */
import java.sql.*;
import java.util.*;

import com.sist.vo.FoodVO;
public class FoodDAO {
	// 오라클 연결을 위한 객체 선언
	// 연결 객체
	private Connection conn;
	// 송수신 하는 객체 - SQL 보내고 데이터 받기
	private PreparedStatement ps;
	// 한 개의 커넥션만 사용
	// 드라이버는 한번만 설정
	private static FoodDAO dao; // 싱글턴 패턴 - 메모리 하나만 사용 => static
	// 오라클 주소
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	// thin = 드라이버 이름 => 연결만 해주는 드라이버
	public FoodDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	// 싱글턴 => 반드시 사용 / 팩토리 패턴
	public static FoodDAO newInstance() {
		if(dao==null) dao=new FoodDAO();
		return dao;
	}
	
	// 오라클 연결
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
			// conn hr/happy => 오라클로 명령 전송
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	// 오라클 닫기
	public void disConnection() {
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
			// 오라클 => exit
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	// --------------------------------------------------------
	public void foodInsert(FoodVO vo) {
		try {
			// 1. 연결
			getConnection();
			// 2. SQL 문장
			String sql = "INSERT INTO food VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql); // SQL 문장을 오라클에 전송
			// 실행 전에 ?에 값을 채운다
			ps.setInt(1, vo.getNo());
			ps.setString(2, vo.getName());
			ps.setString(3, vo.getType());
			ps.setString(4, vo.getPhone());
			ps.setString(5, vo.getAddress());
			ps.setDouble(6, vo.getScore());
			ps.setString(7, vo.getParking());
			ps.setString(8, vo.getPoster());
			ps.setString(9, vo.getTime());
			ps.setString(10, vo.getContent());
			ps.setString(11, vo.getTheme());
			ps.setString(12, vo.getPrice());
			// 실행
			ps.executeUpdate(); // commit 포함
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
		
	}

	// 1. 검색 => 한식 / 중식 / 분식 / 양식 / 일식
	public List<FoodVO> foodFindData(String type) {
		List<FoodVO> list = new ArrayList<FoodVO>();
		try {
			getConnection();
			String sql = "SELECT no, name, type, address, phone "
					+ "FROM food "
					//+ "WHERE type LIKE '%'||?||'%' "
					+ "WHERE type LIKE '%"+type+"%' "
					+ "ORDER BY no ASC";
			// 전송
			ps = conn.prepareStatement(sql);
			// 값 채우기
			//ps.setString(1, type);
			// 실행 후에 결과값 리턴
			ResultSet rs = ps.executeQuery();
			while(rs.next()) { // 커서를 맨 윗줄에 설정
				FoodVO vo = new FoodVO();
				vo.setNo(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setType(rs.getString(3));
				vo.setAddress(rs.getString(4));
				vo.setPhone(rs.getString(5));
				list.add(vo);
//				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " "
//						+ rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5));
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			disConnection();
		}
		return list;
	}

}
