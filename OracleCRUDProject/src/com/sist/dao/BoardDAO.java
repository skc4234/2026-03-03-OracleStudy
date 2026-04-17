package com.sist.dao;
// 오라클 연결 => 사용자 요청
import java.util.*; // List, 데이터를 묶어서 윈도우, 브라우저

import com.sist.vo.BoardVO;

// => List<BoardVO>
// BoardVO => 게시물 한개에 대한 정보
import java.sql.*; // 오라클 연결 - Connection / PreparedStatement / ResultSet
public class BoardDAO {
	private Connection conn; // 오라클 전송 부분
	private PreparedStatement ps; // SQL 받아서 결과값 처리
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static BoardDAO dao; // 단 하나만 생성 => 싱글턴 패턴
	
	// 드라이버 등록 => 단 한번만 호출
	public BoardDAO() {
		try {
			// 리플렉션 => 클래스 이름으로 제어(메모리 할당, 메소드 호출, 변수 값 추가)
			// 스프링 => invoke()
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// 싱글턴 => Spring의 기본
	public static BoardDAO newInstance() {
		if(dao==null) {
			dao = new BoardDAO();
		}
		return dao;
	}
	
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void disConnection() {
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	//----------------------------------------- 공통사항
	// 기능
	// 1. 목록 => SELECT / 페이징
	public List<BoardVO> boardListData(int page) {
		List<BoardVO> list = new ArrayList<BoardVO>();
		try {
			// 연결
			getConnection();
			// SQL 문장 제작
			String sql = "SELECT no, subject, name, TO_CHAR(regdate, 'YYYY-MM-DD') as dbday, hit "
					+ "FROM board "
					+ "ORDER BY no DESC " // 최신 글을 앞으로
					+ "OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY"; // 한 페이지 당 10개의 게시글
			ps = conn.prepareStatement(sql);
			int rowSize = 10;
			int start = (page*rowSize)-rowSize;
			//int end = page*rowSize;
			ps.setInt(1, start);
			// 결과값 받기
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				BoardVO vo = new BoardVO();
				// getInt / getString / getDouble / getDate
				// TO_CHAR => getString
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			disConnection();
		}
		return list;
	}
	
	// 1-1 총 페이지 개수
	public int boardTotalPages() {
		int total = 0;
		try {
			getConnection();
			String sql = "SELECT CEIL(COUNT(*)/10.0) FROM board";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next(); // 결과값으로 커서 이동
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
	
	// 2. 상세보기 => WHERE / 조회수 증가
	public BoardVO boardDetail(int no) {
		BoardVO vo = new BoardVO();
		try {
			getConnection();
			String sql = "SELECT no, name, subject, content, TO_CHAR(regdate, 'YYYY-MM-DD'), hit "
					+ "FROM board "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				vo.setNo(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setSubject(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setDbday(rs.getString(5));
				vo.setHit(rs.getInt(6));
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			disConnection();
		}
		return vo;
	}
	
	
	// 3. 글쓰기 => INSERT
	public void boardInsert(String name, String subject, String content, String pwd) {
		try {
			getConnection();
			String sql = "INSERT INTO board(no, name, subject, content, pwd) "
					+ "VALUES((SELECT NVL(MAX(no)+1,1) FROM board), ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, subject);
			ps.setString(3, content);
			ps.setString(4, pwd);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			disConnection();
		}
	}
	
	// 4. 수정 => UPDATE
	// 5. 삭제 => DELETE
	// 6. 찾기 => SELECT LIKE
}
