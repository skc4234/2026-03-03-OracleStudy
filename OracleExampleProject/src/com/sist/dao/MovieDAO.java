package com.sist.dao;
/*
 *  - 자바
 *    - 변수 설정(VO), 필요시 매개변수(사용자 요청 값), 지역변수
 *    - 연산자 : 산술연산자, 대입연산자
 *    - 제어문 : if, for, while
 *    - 배열/List : VO 를 모아서 전송
 *    - 객체지향 프로그램
 *    	  - 캡슐화 => VO
 *    	  - 포함 => Connection / PreparedStatement
 *    	  - 오버라이딩
 *    	  - 클래스, 메소드 제작 => 1) 리턴형이 뭔지 2) 매개변수 잡기
 *    - 예외 처리 : Connection 사용 시 반드시 예외처리
 *    - 라이브러리 : String / Math(ceil) / StringTokenizer / Date / FileInputStream / FileOutputStream
 *    				Connection / PreparedStatement / ResultSet / ArrayList
 *    ----------------------------------------------------------------
 *    - 웹용 라이브러리 : 브라우저에서 값 받기 => Response
 *                     브라우저로 값 전송 => Request
 * 
 */
/*
 *  오라클(데이터베이스) JDBC 사용 방법
 *  1. 드라이버 등록(연결 준비) : Class.forName("oracle.jdbc.driver.OracleDriver");
 *  										"com.mysql.cj.driver.Driver" => mysql, mariadb;
 *  2. 오라클 연결 : Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","username","password");
 *  				=> conn username/password                    "jdbc:업체명:드라이버이름:@ip:port:db명"
 *  3. SQL 문장 제작 : String sql = "SELECT/INSERT/UPDATE/DELETE ... ?";
 *                   MyBatis : xml에 SQL 문장 저장
 *                   JPA : 메소드로 SQL 문장 대체
 *  4. SQL 문장을 오라클로 전송 : PreparedStatement ps = conn.preparedStatement(sql);
 *  5. 오라클 실행 => 결과값 받기 : ResultSet rs = ps.executeQuery();  // SELECT
 *  			                      int a = ps;executeUpdate(); // INSERT, UPDATE, DELETE
 *  6. ArrayList -> VO로 값 채우기 : list.add();
 *  7. 닫기 : rs.close(); ps.close(); conn.close();
 * 
 * 
 *  - 기능 설정
 *  1. 목록출력 : 사용자가 페이지 요청 => 매개변수
 *  	- page 당 20 rows => List로 모으기 => return형은 LIST<VO>, 매개변수는 int page
 *  2. 상세보기 : 사용자가 해당 목록의 고유번호(PRIMARY KEY) 요청 => 매개변수
 *  	- ROW 하나의 있는 컬럼 출력 => return형은 VO, 매개변수는 int mno
 *  3. 검색 : 사용자가 검색어 요청 => 매개변수
 *  	- ROW 여러개 출력 => return형은 List<VO>, 매개변수는 String 검색할 컬럼, String 검색어
 *  
 */

import com.sist.vo.*;
import java.util.*; // ArrayList
import java.sql.*; // 데이터베이스 연결 라이브러리
public class MovieDAO {
	// 1. DAO 전체에서 사용하는 객체
	// 연결 객체 : 하나만 사용
	private Connection conn;
	// 2. 송수신 객체
	private PreparedStatement ps;
	// ResultSet => SQL 문장에 따라서 저장되는 데이터가 다르다 => 지역변수
	// 3. URL
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	// MySQL / MariaDB => 3306:설정
	// MSSQL => 1433:PUB
	// 단점 : 오라클은 실패할때마다 포트번호+1 실행 => 포트가 달라진다
	private static final int ROWSIZE = 20;
	
	// 1. 드라이버 등록 => 한번만 작업 => 생성자
	private static MovieDAO dao;
	public MovieDAO() {
		try {
			// 오라클에 있는 데이터를 드라이버에 설정
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// 2. 오라클 연동 => SQL PLUS 열기
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// 3. 닫기
	public void disConnection() {
		try {
			// 연 순서의 역순으로 닫음
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// 싱글턴
	public static MovieDAO newInstance() {
		if(dao==null) dao=new MovieDAO();
		return dao;
	}
	
	// 공통사항 => 오라클은 반드시 열고 닫기 반복
	
	// 기능 수행
	// 1. 목록출력
	public List<MovieVO> movieListData(int page) {
		List<MovieVO> list = new ArrayList<MovieVO>();
		try {
			// 1. 연결
			getConnection();
			// 2. SQL 문장 제작
			String sql = "SELECT mno, title, actor, regdate, genre "
					+ "FROM movie "
					+ "ORDER BY mno "
					+ "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
			// 3. 오라클로 전송
			ps = conn.prepareStatement(sql);
			// 4. ?에 값을 채운다
			int start = (page*ROWSIZE)-ROWSIZE;
			ps.setInt(1, start);
			ps.setInt(2, ROWSIZE);
			// 5. 실행 후에 결과값 받기
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				MovieVO vo = new MovieVO();
				// ROW 단위로 저장
				vo.setMno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setActor(rs.getString(3));
				vo.setRegdate(rs.getString(4));
				vo.setGenre(rs.getString(5));
				// 전체 저장
				list.add(vo);
			}
			rs.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return list;
	}
	
	// 1-1 총 페이지 수
	public int movieTotalPage() {
		int total = 0;
		try {
			// 1. 연결
			getConnection();
			// 2. SQL 문장 작성
			String sql = "SELECT CEIL(COUNT(*)/?) FROM movie";
			// 3. SQL 오라클로 전송
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, (double)ROWSIZE);
			//ps.setInt(1, ROWSIZE);
			// 4. 결과값 받기
			ResultSet rs = ps.executeQuery();
			// 5. 데이터 출력 위치로 커서 이동
			rs.next();
			total = rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return total;
	}
	
	
	// 2. 상세보기
	public MovieVO movieDetailData(int mno) {
		MovieVO vo = new MovieVO();
		try {
			// 1. 연결
			getConnection();
			// 2. SQL 문장 제작
			String sql = "SELECT mno, title, genre, actor, regdate, grade, director "
					+ "FROM movie "
					+ "WHERE mno=?";
			// 3. 오라클로 SQL 전송
			ps=conn.prepareStatement(sql);
			// 4. 값 채우기
			ps.setInt(1, mno);
			// 5. 결과값 받기
			ResultSet rs = ps.executeQuery();
			// 6. 출력된 데이터 앞으로 커서 이동
			rs.next();
			// 7. 데이터 저장
			vo.setMno(rs.getInt(1));
			vo.setTitle(rs.getString(2));
			vo.setGenre(rs.getString(3));
			vo.setActor(rs.getString(4));
			vo.setRegdate(rs.getString(5));
			vo.setGrade(rs.getString(6));
			vo.setDirector(rs.getString(7));
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return vo;
	}
	
	// 3. 검색
	/*
	 *  SELECT * FROM movie
	 *  WHERE ? LIKE '%'||?||'%' => 안됨
	 *     "+col+"
	 *  ps.setString(1, col) => 자동 ''
	 *  ps.setString(2, fd)
	 * 
	 *  LIKE는 문자열 결합을 사용 
	 */
	public List<MovieVO> movieFindData(String col, String fd) {
		List<MovieVO> list = new ArrayList<MovieVO>();
		try {
			// 1. 연결
			getConnection();
			// 2. SQL 문장 제작
			String sql = "SELECT mno, title, actor, regdate, genre "
					+ "FROM movie "
					+ "WHERE " + col + " LIKE '%'||?||'%'";
			//		  "WHERE " + col + " LIKE '%" + fd + "%'"; => SQL 인젝션 때문에 ? 사용 권장
			// LIKE 문장 사용시 문자열 결합 사용 : ||
			// 3. SQL 오라클로 전송
			ps = conn.prepareStatement(sql);
			// 4. 값 채우기
			ps.setString(1, fd);
			// 5. 결과값 받기
			ResultSet rs = ps.executeQuery();
			// 6. 결과값 저장
			while(rs.next()) {
				MovieVO vo = new MovieVO();
				// ROW 단위로 저장
				vo.setMno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setActor(rs.getString(3));
				vo.setRegdate(rs.getString(4));
				vo.setGenre(rs.getString(5));
				// 전체 저장
				list.add(vo);
			}
			rs.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return list;
	}
}
