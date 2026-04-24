package com.sist.dao;

import java.util.*;

import com.sist.vo.MusicVO;

import java.sql.*;
public class MusicDAO {
	private Connection conn;
	private PreparedStatement ps;
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USERNAME = "hr";
	private static final String PASSWORD = "happy";
	private static final int ROWSIZE = 20;
	private static MusicDAO dao;
	
	public static MusicDAO newInstance() {
		if(dao==null) dao = new MusicDAO();
		return dao;
	}
	
	public MusicDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void disConnection() {
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// 1. 목록 출력
	public List<MusicVO> musicListData(int page) {
		List<MusicVO> list = new ArrayList<MusicVO>();
		try {
			getConnection();
			String sql = "SELECT no, cno, title, singer, album, state, idcrement "
					+ "FROM genie_music "
					+ "ORDER BY no "
					+ "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
			ps = conn.prepareStatement(sql);
			int start = (page*ROWSIZE)-ROWSIZE;
			ps.setInt(1, start);
			ps.setInt(2, ROWSIZE);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				MusicVO vo = new MusicVO();
				vo.setNo(rs.getInt(1));
				vo.setCno(rs.getInt(2));
				vo.setTitle(rs.getString(3));
				vo.setSinger(rs.getString(4));
				vo.setAlbum(rs.getString(5));
				vo.setState(rs.getString(6));
				vo.setIdcrement(rs.getInt(7));
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
	public int musicTotalPages() {
		int total = 0;
		try {
			getConnection();
			String sql = "SELECT CEIL(COUNT(*)/?) FROM genie_music";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, ROWSIZE);
			ResultSet rs = ps.executeQuery();
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
	public MusicVO musicDetailData(int no) {
		MusicVO vo = new MusicVO();
		try {
			getConnection();
			String sql = "SELECT no, cno, title, singer, album, state, idcrement "
					+ "FROM genie_music "
					+ "WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setCno(rs.getInt(2));
			vo.setTitle(rs.getString(3));
			vo.setSinger(rs.getString(4));
			vo.setAlbum(rs.getString(5));
			vo.setState(rs.getString(6));
			vo.setIdcrement(rs.getInt(7));
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return vo;
	}
	
	// 3. 검색(cno)
	public List<MusicVO> musicFindData(int cno) {
		List<MusicVO> list = new ArrayList<MusicVO>();
		try {
			getConnection();
			String sql = "SELECT no, title, singer, album, state, idcrement "
					+ "FROM genie_music "
					+ "WHERE cno=? "
					+ "ORDER BY no ASC";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, cno);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				MusicVO vo = new MusicVO();
				vo.setNo(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setSinger(rs.getString(3));
				vo.setAlbum(rs.getString(4));
				vo.setState(rs.getString(5));
				vo.setIdcrement(rs.getInt(6));
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
	
	// 4. 검색(제목, 가수)
	public List<MusicVO> musicFindData(String col, String fd) {
		List<MusicVO> list = new ArrayList<MusicVO>();
		try {
			getConnection();
			String sql = "SELECT no, cno, title, singer, album, state, idcrement "
					+ "FROM genie_music "
					+ "WHERE " + col + " LIKE '%'||?||'%' "
					+ "ORDER BY no ASC";
			ps = conn.prepareStatement(sql);
			ps.setString(1, fd);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				MusicVO vo = new MusicVO();
				vo.setNo(rs.getInt(1));
				vo.setCno(rs.getInt(2));
				vo.setTitle(rs.getString(3));
				vo.setSinger(rs.getString(4));
				vo.setAlbum(rs.getString(5));
				vo.setState(rs.getString(6));
				vo.setIdcrement(rs.getInt(7));
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
