package com.sist.db;

import java.sql.*;
import java.util.*;
public class EmpMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		System.out.print("직위를 입력하시오 : ");
		String name = scan.next();
		
		// 1. 드라이버
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		
		// 2. 오라클 연결 
		// Socket
		Connection conn = DriverManager.getConnection(url,"hr","happy");
		
		// 3. 명령문 전송
		String sql = "SELECT empno, ename, job FROM emp WHERE job LIKE '%" + name +"%'";
		PreparedStatement ps = conn.prepareStatement(sql);
		// OutputStream
		
		// 4. 결과값 받기
		ResultSet rs = ps.executeQuery();
		
		// 5. 출력
		while(rs.next()) {
			System.out.println(rs.getInt(1) +" " + rs.getString(2) + " " + rs.getString(3));
			
		}
		rs.close();
		ps.close();
		conn.close();
	}

}
