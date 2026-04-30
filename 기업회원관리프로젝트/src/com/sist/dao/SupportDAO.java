package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sist.vo.MemberVO;
import com.sist.vo.SupportVO;

public class SupportDAO {
	// 공통 사용 부분
		private Connection conn; // Socket
		private PreparedStatement ps; // BufferedReader, OutputStream
		// 송신(SQL), 수신(오라클로부터 결과값 받기)
		private final static String URL = "jdbc:oracle:thin:@localhost:1521:XE";
		
		private static SupportDAO dao;
		public static SupportDAO newInstance() {
			if(dao==null) dao = new SupportDAO();
			return dao;
		}
		
		// 1. 드라이버 등록
			public SupportDAO() {
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
			// 2. 오라클 연결
			public void getConnection() {
				try {
					conn = DriverManager.getConnection(URL, "hr", "happy");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
			// 3. 연결 해제
			public void disConnection() {
				try {
					if(ps!=null) ps.close();
					if(conn!=null) conn.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
			// 기능
			
			// SUPPORT
			public int supportInsert(SupportVO vo) {
				int check = 0;
				try {
					getConnection();
					String sql = "INSERT INTO support VALUES(support_no_seq.nextval, ?, ?, ?, ?, ?, ?, SYSDATE, 'n', NULL)";
					ps = conn.prepareStatement(sql);
					ps.setString(1, vo.getId());
					ps.setString(2, vo.getPwd());
					ps.setString(3, vo.getName());
					ps.setString(4, vo.getTitle());
					ps.setString(5, vo.getPhone());
					ps.setString(6, vo.getContent());
					check = ps.executeUpdate();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					disConnection();
				}
				return check;
			}
			
			// 비밀번호가 맞는지 체크
			public boolean supportPwdCheck(String id, String pwd) {
				boolean bCheck = false;
				try {
					getConnection();
					String sql = "SELECT pwd FROM member WHERE id=?";
					ps = conn.prepareStatement(sql);
					ps.setString(1, id);
					ResultSet rs = ps.executeQuery();
					rs.next();
					if(pwd.equals(rs.getString(1))) bCheck=true;
					rs.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					disConnection();
				}
				return bCheck;
			}
			
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
			
			
			public List<SupportVO> supportAllData() {
				List<SupportVO> list = new ArrayList<SupportVO>();
				try {
					getConnection();
					String sql = "SELECT no, id, name, phone, content, TO_CHAR(regdate, 'YYYY-MM-DD HH24:MI:SS'), isAnswer, title "
							+ "FROM support "
							+ "ORDER BY no DESC";
					ps = conn.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						SupportVO vo = new SupportVO();
						vo.setNo(rs.getInt(1));
						vo.setId(rs.getString(2));
						vo.setName(rs.getString(3));
						vo.setPhone(rs.getString(4));
						vo.setContent(rs.getString(5));
						vo.setDbday(rs.getString(6));
						vo.setIsAnswer(rs.getString(7));
						vo.setTitle(rs.getString(8));
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
			
			public SupportVO supportOneData(int no) {
				SupportVO vo = new SupportVO();
				try {
					getConnection();
					String sql = "SELECT no, id, name, phone, content, TO_CHAR(regdate, 'YYYY-MM-DD HH24:MI:SS'), isAnswer, title, answer "
							+ "FROM support "
							+ "WHERE no = ?";
					ps = conn.prepareStatement(sql);
					ps.setInt(1, no);
					ResultSet rs = ps.executeQuery();
					rs.next();
					vo.setNo(rs.getInt(1));
					vo.setId(rs.getString(2));
					vo.setName(rs.getString(3));
					vo.setPhone(rs.getString(4));
					vo.setContent(rs.getString(5));
					vo.setDbday(rs.getString(6));
					vo.setIsAnswer(rs.getString(7));
					vo.setTitle(rs.getString(8));
					vo.setAnswer(rs.getString(9));
					rs.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					disConnection();
				}
				return vo;
			}
			
			public void supportUpdate(int no, String answer) {
				try {
					getConnection();
					String sql = "UPDATE support SET answer = ?, isAnswer = 'y' WHERE no = "+no;
					ps = conn.prepareStatement(sql);
					ps.setString(1, answer);
					ps.executeUpdate();
					System.out.println("성공");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					disConnection();
				}
			}
			
			  public MemberVO memberOneData(String id) {
				  MemberVO vo = new MemberVO();
				  try {
					getConnection();
					String sql = "SELECT name, phone FROM member WHERE id=?";
					ps = conn.prepareStatement(sql);
					ps.setString(1, id);
					ResultSet rs = ps.executeQuery();
					rs.next();
					vo.setName(rs.getString(1));
					vo.setPhone(rs.getString(2));
					rs.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					disConnection();
				}
				  return vo;
			  }
}
