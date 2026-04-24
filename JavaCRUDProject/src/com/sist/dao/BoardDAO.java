package com.sist.dao;
import java.util.*;

import com.sist.commons.DBUtil;
import com.sist.vo.BoardVO;

import java.sql.*;
public class BoardDAO {
	private DBUtil db = new DBUtil();
	private Connection conn;
	private PreparedStatement ps;
	private static BoardDAO dao; // 싱글턴
	
	public static BoardDAO newInstance() {
		if(dao==null) dao = new BoardDAO();
		return dao;
	}
	
	// 1. 목록 출력
	// 정렬 => **ORDER BY, INDEX_DESC
	// 페이징 => OFFSET (start ROW index) ROWS FETCH NEXT (ROW_SIZE) ROWS ONLY
	public List<BoardVO> board_list(int page) {
		List<BoardVO> list = new ArrayList<BoardVO>();
		try {
			conn = db.getConnection();
			// SQL 문장 오라클로 전송
			String sql = "SELECT /*+ INDEX_DESC(board board_no_pk) */ no, subject, name, TO_CHAR(regdate, 'YYYY-MM-DD'), hit"
					+ " FROM board"
					+ " OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY";
			int start = (page*10)-10;
			ps=conn.prepareStatement(sql);
			// ?에 값 삽입
			ps.setInt(1, start);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				BoardVO vo = new BoardVO();
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
			System.out.println(e.getMessage());
		} finally {
			db.disConnection(conn, ps);
		}

		return list;
	}
	
	// 1.1 총 페이지 수 구하기 => CEIL
	public int boardTotalPage() {
		int total=0;
		try {
			// 1. 오라클 연결
			conn = db.getConnection();
			// 2. SQL 문장 제작
			String sql = "SELECT CEIL(COUNT(*)/10.0) as total FROM board";
			ps = conn.prepareStatement(sql);
			// 3. SQL 문장에 ? 이 있으면 ps.set
			ResultSet rs = ps.executeQuery();
			// 4. 출력된 메모리 위치로 커서 이동 => rs.next()
			rs.next();
			// 5. 해당 데이터형을 이용해서 데이터를 가지고 온다 => 1부터 첫번째값
			// 별칭(컬럼명)으로도 읽을 수 있다 => MyBatis는 별칭명으로 읽는다
			total = rs.getInt("total");
			//total = rs.getInt(1);
			rs.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		} finally {
			db.disConnection(conn, ps);
		}
		
		return total;
	}
	
	// 2. INSERT
	public void board_insert(BoardVO vo) {
		try {
			conn = db.getConnection();
			String sql = "INSERT INTO board VALUES("
					+ "board_seq.nextval, ?, ?, ?, ?, SYSDATE, 0)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			// 검색 : excuteQuery, 삽입/삭제/변경 : executeUpdate
			ps.executeUpdate(); // commit 포함 => 자바는 Auto Commit
			/*
			 * INSERT
			 * INSERT => ERROR 발생시 밑에절 COMMIT 실패 => 트랜잭션(동시 성공/실패)
			 * INSERT
			 */
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
	}
	
	
	// 3. 상세보기 => SELECT
	public BoardVO board_detail(int no) {
		BoardVO vo = new BoardVO();
		try {
			conn = db.getConnection();
			// 조회수 증가
			String sql = "UPDATE board SET "
					+ "hit=hit+1 "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
			
			// 실제 데이터 읽기
			sql = "SELECT no, name, subject, content, TO_CHAR(regdate, 'YYYY-MM-DD HH24:MI:SS'), hit "
					+ "FROM board "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setDbday(rs.getString(5));
			vo.setHit(rs.getInt(6));
			rs.close();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
	
	// 4. 삭제
	public boolean board_delete(int no, String pwd) {
		boolean bCheck = false; // 비밀번호 일치 여부
		try {
			conn = db.getConnection();
			String sql = "SELECT pwd FROM board WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs =  ps.executeQuery();
			rs.next();
			String db_pwd = rs.getString(1);
			rs.close();
			if(db_pwd.equals(pwd)) {
				bCheck = true;
				sql = "DELETE FROM board WHERE no=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return bCheck;
	}
	
	// 5. 수정
	public boolean board_update(int no, String pwd) {
		boolean bCheck = false;
		try {
			conn = db.getConnection();
			String sql = "SELECT pwd FROM board WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd = rs.getString(1);
			if(db_pwd.equals(pwd)) {
				bCheck = true;
				//sql = "UPDATE board SET "
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return bCheck;
	}
}
