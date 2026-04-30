package com.sist.dao;

import com.sist.vo.*;
import java.util.*;
import java.sql.*;
public class GoodsDAO {
	// 공통 사용 부분
	private Connection conn; // Socket
	private PreparedStatement ps; // BufferedReader, OutputStream
	// 송신(SQL), 수신(오라클로부터 결과값 받기)
	private final static String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private String[] tables = {
		"",
		"goods_all",
		"goods_best",
		"goods_new",
		"goods_special"
	};
	// Singletone
	private static GoodsDAO dao;
	public static GoodsDAO newInstance() {
		if(dao==null) dao = new GoodsDAO();
		return dao;
	}
	
	// 1. 드라이버 등록
	public GoodsDAO() {
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
	
	// 기능 설정 => 회원 가입 / 회원 탈퇴 / 로그인
	// 회원은 상품 구매를 할 수 있다 => 구매 테이블
	// 관리 => 상품 구매가 많은 사람 => 등급 지정 => 관리자
	// 구매 현황, 회원 관리
	
	// 1. 상품 목록 출력
	public List<GoodsVO> goodsListData(int type, int page) {
		List<GoodsVO> list = new ArrayList<GoodsVO>();
		try {
			getConnection();
			String sql = "SELECT no, goods_name, goods_sub, goods_price, goods_discount, goods_first_price, goods_delivery, "
					+ "goods_poster, hit "
					+ "FROM " + tables[type] + " "
					+ "ORDER BY no ASC "
					+ "OFFSET ? ROWS FETCH NEXT 12 ROWS ONLY";
					// 페이지 나누기 : 인라인뷰 => offset
			ps = conn.prepareStatement(sql);
			int start = (page*12)-12;
			ps.setInt(1, start);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				GoodsVO vo = new GoodsVO();
				vo.setNo(rs.getInt(1));
				vo.setGoods_name(rs.getString(2));
				vo.setGoods_sub(rs.getString(3));
				vo.setGoods_price(rs.getString(4));
				vo.setGoods_discount(rs.getInt(5));
				vo.setGoods_first_price(rs.getString(6));
				vo.setGoods_delivery(rs.getString(7));
				vo.setGoods_poster(rs.getString(8));
				vo.setHit(rs.getInt(9));
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
	
	// 1-1 총 페이지
	public int totalPages(int type) {
		int total = 0;
		try {
			getConnection();
			String sql = "SELECT CEIL(COUNT(*)/12.0) FROM " + tables[type];
			ps = conn.prepareStatement(sql);
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
	
	// 1. List => 조건 없는 경우 / 페이지 나누기
	// 2. VO => SELECT 뒤에 컬럼 두개 이상
	// 3. 컬럼 1개 => 해당 데이터형
	
	// 2. 상세페이지
	public GoodsVO goodsDetailData(int type, int gno) {
		GoodsVO vo = new GoodsVO();
		try {
			getConnection();
			String sql = "SELECT no, goods_name, goods_sub, goods_price, goods_discount, goods_first_price, goods_delivery, "
					+ "goods_poster, hit "
					+ "FROM " + tables[type] + " "
					+ "WHERE no = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, gno);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setGoods_name(rs.getString(2));
			vo.setGoods_sub(rs.getString(3));
			vo.setGoods_price(rs.getString(4));
			vo.setGoods_discount(rs.getInt(5));
			vo.setGoods_first_price(rs.getString(6));
			vo.setGoods_delivery(rs.getString(7));
			vo.setGoods_poster(rs.getString(8));
			vo.setHit(rs.getInt(9));
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return vo;
	}
	
	
	// Goods_all 전체 데이터 출력
	public List<GoodsVO> goodsListDataAll(int type) {
		List<GoodsVO> list = new ArrayList<GoodsVO>();
		try {
			getConnection();
			String sql = "SELECT no, goods_name, goods_sub, goods_price, goods_discount, goods_first_price, goods_delivery, "
					+ "goods_poster, hit "
					+ "FROM " + tables[type] + " "
					+ "ORDER BY no ASC";
					// 페이지 나누기 : 인라인뷰 => offset
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				GoodsVO vo = new GoodsVO();
				vo.setNo(rs.getInt(1));
				vo.setGoods_name(rs.getString(2));
				vo.setGoods_sub(rs.getString(3));
				vo.setGoods_price(rs.getString(4));
				vo.setGoods_discount(rs.getInt(5));
				vo.setGoods_first_price(rs.getString(6));
				vo.setGoods_delivery(rs.getString(7));
				vo.setGoods_poster(rs.getString(8));
				vo.setHit(rs.getInt(9));
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
	
	/*
	NO      NOT NULL NUMBER       
	ID               VARCHAR2(20) 
	TYPE    NOT NULL NUMBER       
	GNO     NOT NULL NUMBER      
	ACCOUNT          NUMBER       
	PRICE            NUMBER       
	REGDATE          DATE
	 */
	
	// 구매
	public void goodsBuyData(BuyVO vo) {
		try {
			getConnection();
			String sql = "INSERT INTO buy VALUES(buy_no_seq.nextval, ?, ?, ?, ?, ?, SYSDATE)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getId());
			ps.setInt(2, vo.getType());
			ps.setInt(3, vo.getGno());
			ps.setInt(4, vo.getAccount());
			ps.setInt(5, vo.getPrice());
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}
	
	// **조인
	/*
	 *    고객     <구매>     도서
	 *            고객id
	 *            도서번호
	 *            ...
	 *            
	 *    INNER JOIN(EQUI JOIN)
	 *    OUTER JOIN(LEFT/RIGT JOIN)
	 */
	public List<BuyVO> buyListData(String id) {
		List<BuyVO> list = new ArrayList<BuyVO>();
		try {
			getConnection();
			String sql = "SELECT b.no, goods_poster, goods_name, account, TO_CHAR(regdate, 'YYYY-MM-DD HH24:MI:SS'), price "
					+ "FROM goods_all g JOIN buy b "
					+ "ON g.no = b.gno "
					+ "AND b.id = ? "
					+ "ORDER BY regdate DESC";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				BuyVO vo = new BuyVO();
				vo.setNo(rs.getInt(1));
				vo.getBvo().setGoods_poster(rs.getString(2));
				vo.getBvo().setGoods_name(rs.getString(3));
				vo.setAccount(rs.getInt(4));
				vo.setDbday(rs.getString(5));
				vo.setPrice(rs.getInt(6));
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
	
	// 목록 삭제
	public void buyDelete(int no) {
		try {
			getConnection();
			String sql = "DELETE FROM buy "
					+ "WHERE no="+no;
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
