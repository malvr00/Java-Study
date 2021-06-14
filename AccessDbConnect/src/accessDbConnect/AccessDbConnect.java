package accessDbConnect;

import java.sql.*;

public class AccessDbConnect {
	static Connection con;		// 객체 생성하지 않고 사용하므로 static
	static Statement smt;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
		  // 특정위치 지정하여 Connection 획득 (DB 연결) ↓
			// con = DriverManager.getConnection("jdbc:ucanaccess://c:\\DB\\SjTestDB.accdb;memory=false");
		  // Project Directory에 있는 DB연결 ↓
			con = DriverManager.getConnection("jdbc:ucanaccess://SjTestDB.accdb;memory=false");
			System.out.println("Access DB에 연결 성공");
		  // DB에 접극하긴위한 Statement 객체 생성
			smt = con.createStatement();
			System.out.println("Statement 객체 생성 성공!!");
			
			smt.close();
			con.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
