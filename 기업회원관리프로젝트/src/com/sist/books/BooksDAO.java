package com.sist.books;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class BooksDAO {
	// 공통 사용 부분
		private Connection conn; // Socket
		private PreparedStatement ps; // BufferedReader, OutputStream
		// 송신(SQL), 수신(오라클로부터 결과값 받기)
		private final static String URL = "jdbc:oracle:thin:@localhost:1521:XE";
		
		private static BooksDAO dao;
		public static BooksDAO newInstance() {
			if(dao==null) dao = new BooksDAO();
			return dao;
		}
		
		// 1. 드라이버 등록
			public BooksDAO() {
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
			
			/*
			NO       NOT NULL NUMBER         
			BOOKNAME NOT NULL VARCHAR2(2000) 
			POSTER   NOT NULL VARCHAR2(260)  
			AUTHOR   NOT NULL VARCHAR2(1000) 
			PRICE    NOT NULL VARCHAR2(100)  
			PUBDATE  NOT NULL VARCHAR2(100)  
			ISBN     NOT NULL VARCHAR2(100)  
			CONTENT           CLOB           
			TAG               CLOB
			 */
			
			// 기능
			// 데이터 추가
			public void insertBooks(BooksVO vo) {
				try {
					getConnection();
					String sql = "INSERT INTO books VALUES(books_no_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";
					// 1. ; => 자동 추가
					// 2. Auto Commit
					// 3. ? 개수만큼 값을 ps.setXxx로 채워야함 => SQL Injection 때문에 ? 사용
					ps = conn.prepareStatement(sql);
					ps.setString(1, vo.getBookname());
					ps.setString(2, vo.getPoster());
					ps.setString(3, vo.getAuthor());
					ps.setString(4, vo.getPrice());
					ps.setString(5, vo.getPubdate());
					ps.setString(6, vo.getIsbn());
					ps.setString(7, vo.getContent());
					ps.setString(8, vo.getTag());
					ps.executeUpdate(); // int => INSERT / UPDATE / DELETE / CREATE / DROP / ALTER / TRUNCATE
										// CREATE => ID로 개인 사이트 생성(테이블)
										// WEB 3.0 => 개인 사이트(콘텐츠 관리)
										// 블록체인
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					disConnection();
				}
			}
			
			public List<BooksVO> booksAllData() {
				List<BooksVO> list = new ArrayList<BooksVO>();
				try {
					getConnection();
					String sql = "SELECT no, bookname, price, pubdate, isbn, content FROM books ORDER BY no DESC";
					ps = conn.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						BooksVO vo = new BooksVO();
						vo.setNo(rs.getInt(1));
						vo.setBookname(rs.getString(2));
						vo.setPrice(rs.getString(3));
						vo.setPubdate(rs.getString(4));
						vo.setIsbn(rs.getString(5));
						vo.setContent(rs.getString(6));
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
			
			public BooksVO booksOneData(int no) {
				BooksVO vo = new BooksVO();
				try {
					getConnection();
					String sql = "SELECT no, bookname, price, pubdate, isbn, content FROM books WHERE no="+no;
					ps = conn.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();
					rs.next();
					vo.setNo(rs.getInt(1));
					vo.setBookname(rs.getString(2));
					vo.setPrice(rs.getString(3));
					vo.setPubdate(rs.getString(4));
					vo.setIsbn(rs.getString(5));
					vo.setContent(rs.getString(6));
					rs.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					disConnection();
				}
				return vo;
			}
			
			// 도서 삭제
			public void booksDeleteData(int no) {
				try {
					getConnection();
					String sql = "DELETE FROM books WHERE no="+no;
					ps = conn.prepareStatement(sql);
					ps.executeUpdate();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					disConnection();
				}
			}
			
			
}
