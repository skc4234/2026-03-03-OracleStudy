package com.sist.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// ***1. 아이디 중복 체크
// ***2. 우편번호 검색
// ***3. 회원가입
// ***4. 회원정보
// 5. 회원수정
// ***6. 회원탈퇴
import com.sist.vo.*;
public class MemberDAO {
	// 공통 사용 부분
	private Connection conn; // Socket
	private PreparedStatement ps; // BufferedReader, OutputStream
	// 송신(SQL), 수신(오라클로부터 결과값 받기)
	private final static String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	
	private static MemberDAO dao;
	public static MemberDAO newInstance() {
		if(dao==null) dao = new MemberDAO();
		return dao;
	}
	
	// 1. 드라이버 등록
		public MemberDAO() {
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
		// 우편번호 검색
		public List<ZipcodeVO> postFind(String dong) {
			List<ZipcodeVO> list = new ArrayList<ZipcodeVO>();
			try {
				getConnection();
				String sql = "SELECT zipcode, sido, gugun, dong, NVL(bunji, ' ') "
						+ "FROM zipcode "
						+ "WHERE dong LIKE '%'||?||'%'";
				//                         '%"+dong+"%'
				//                         => SQL 인젝션
				//                MySQL, MariaDB => CONCAT('%',?,'%')
				ps = conn.prepareStatement(sql);
				ps.setString(1, dong);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					ZipcodeVO vo = new ZipcodeVO();
					vo.setZipcode(rs.getString(1));
					vo.setSido(rs.getString(2));
					vo.setGugun(rs.getString(3));
					vo.setDong(rs.getString(4));
					vo.setBunji(rs.getString(5));
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
		
		// 검색 결과 count
		public int postFindCount(String dong) {
			int count = 0;
			try {
				getConnection();
				String sql = "SELECT COUNT(*) "
						+ "FROM zipcode "
						+ "WHERE dong LIKE '%'||?||'%'";
				//                         '%"+dong+"%'
				//                         => SQL 인젝션
				//                MySQL, MariaDB => CONCAT('%',?,'%')
				ps = conn.prepareStatement(sql);
				ps.setString(1, dong);
				ResultSet rs = ps.executeQuery();
				rs.next();
				count = rs.getInt(1);
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				disConnection();
			}
			return count;
		}
		
		// id 중복 체크
		public int memberIdCheck(String id) {
			// count가 1이면 중복, 0이면 사용가능
			int count = 0;
			try {
				getConnection();
				String sql = "SELECT COUNT(*) FROM member "
						+ "WHERE id=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, id);
				ResultSet rs = ps.executeQuery();
				rs.next();
				count = rs.getInt(1);
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				disConnection();
			}
			return count;
		}
		
		// 회원가입
		public int memberJoin(MemberVO vo) {
			int check = 0;
			try {
				getConnection();
				String sql = "INSERT INTO member VALUES(?,?,?,?,?,?,?,?,?,'n',SYSDATE)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, vo.getId());
				ps.setString(2, vo.getPwd());
				ps.setString(3, vo.getName());
				ps.setString(4, vo.getSex());
				ps.setString(5, vo.getPost());
				ps.setString(6, vo.getAddr1());
				ps.setString(7, vo.getAddr2());
				ps.setString(8, vo.getPhone());
				ps.setString(9, vo.getContent());
				check = ps.executeUpdate(); // 정상처리되면 check = 1
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				disConnection();
			}
			return check;
		}
		
		// 로그인 확인
		public MemberVO isLogin(String id, String pwd) {
			// 경우의 수
			// 2개면 boolean, 3개 이상이면 int / String
			//String result="";
			MemberVO vo = new MemberVO();
			try {
				getConnection();
				String sql="SELECT COUNT(*) FROM member WHERE id=?"; // id 존재여부
				ps = conn.prepareStatement(sql);
				ps.setString(1, id);
				ResultSet rs = ps.executeQuery();
				rs.next();
				int count = rs.getInt(1);
				rs.close();
				if(count==0) { // id가 없는 상태
					vo.setMsg("NOID");
				}
				else { // id 존재
					sql="SELECT pwd,id,isAdmin FROM member WHERE id=?";
					ps = conn.prepareStatement(sql);
					ps.setString(1, id);
					rs = ps.executeQuery();
					rs.next();
					String db_pwd = rs.getString(1);
					String db_id = rs.getString(2);
					String isAdmin = rs.getString(3);
					rs.close();
					if(db_pwd.equals(pwd)) { // 로그인 성공
						vo.setMsg("OK");
						vo.setIsAdmin(isAdmin);
						vo.setId(db_id);
					}
					else { // 비밀번호 틀림
						vo.setMsg("NOPWD");
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				disConnection();
			}
			return vo;
		}
		
		// 회원 목록
		public List<MemberVO> memberAllData() {
			List<MemberVO> list = new ArrayList<MemberVO>();
			try {
				getConnection();
				String sql = "SELECT id, name, sex, addr1, phone, TO_CHAR(regdate, 'YYYY-MM-DD HH24:MI:SS') "
						+ "FROM member "
					    + "WHERE isAdmin<>'y' "
						+ "ORDER BY regdate DESC";
				ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					MemberVO vo = new MemberVO();
					vo.setId(rs.getString(1));
					vo.setName(rs.getString(2));
					vo.setSex(rs.getString(3));
					vo.setAddr1(rs.getString(4));
					vo.setPhone(rs.getString(5));
					vo.setDbday(rs.getString(6));
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
		
		 public List<String> memberGetId()
		  {
			  List<String> list=
					  new ArrayList<String>();
			  try
			  {
				  getConnection();
				  String sql="SELECT id "
						    +"FROM member";
				  // 0 , 1 
				  ps=conn.prepareStatement(sql);
				  
				  ResultSet rs=ps.executeQuery();
				  while(rs.next())
				  {
					  list.add(rs.getString(1));
				  }
				  rs.close();
				  
			  }catch(Exception ex)
			  {
				  ex.printStackTrace();
			  }
			  finally
			  {
				  disConnection();
			  }
			  return list;
		  }
		  public void gradeInsert(String id,String grade)
		  {
			  try
			  {
				  getConnection();
				  String sql="INSERT INTO grades "
						    +"VALUES(?,?)";
				  ps=conn.prepareStatement(sql);
				  ps.setString(1, id);
				  ps.setString(2, grade);
				  ps.executeUpdate();
				  
			  }catch(Exception ex)
			  {
				  ex.printStackTrace();
			  }
			  finally
			  {
				  disConnection();
			  }
		  }
		  public List<MemberVO> memberListData()
		  {
			  List<MemberVO> list=
					  new ArrayList<MemberVO>();
			  try
			  {
				  getConnection();
				  String sql="SELECT m.id,name,sex,addr1,phone,grade "
						    +"FROM member m JOIN grades g "
						    +"ON m.id=g.id "
						    + "AND m.isAdmin<>'y'";
				  ps=conn.prepareStatement(sql);
				  ResultSet rs=ps.executeQuery();
				  while(rs.next())
				  {
					  MemberVO vo=
							  new MemberVO();
					  vo.setId(rs.getString(1));
					  vo.setName(rs.getString(2));
					  vo.setSex(rs.getString(3));
					  vo.setAddr1(rs.getString(4));
					  vo.setPhone(rs.getString(5));
					  vo.setGrade(rs.getString(6));
					  list.add(vo);
				  }
				  rs.close();
			  }catch(Exception ex)
			  {
				  ex.printStackTrace();
			  }
			  finally
			  {
				  disConnection();
			  }
			  return list;
		  }
		  
		  public void gradeUpdate(String id, String grade) {
			  try {
				  getConnection();
				  String sql = "UPDATE grades SET grade=? WHERE id=?";
				  ps = conn.prepareStatement(sql);
				  ps.setString(1, grade);
				  ps.setString(2, id);
				  ps.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				disConnection();
			}
		  }
		  
		  public void memberDelete(String id) {
			  try {
				getConnection();
				String sql = "DELETE FROM member WHERE id = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, id);
				ps.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				disConnection();
			}
		  }
		  

}
