package accessDBConnect3;

import java.sql.*;

public class AccessDBConnect3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con = null;		// 지역변수 초기화
		Statement smt = null;		// 지역변수 초기화
		AccessDBConnect3 db = new AccessDBConnect3();
		try {
			con = db.dbConnect();
			smt = con.createStatement();
			System.out.println(con);		//Connection 정보 출력
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			smt.close();		// statement 종료
			con.close();		// DateBase와의 연결 종료
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public Connection dbConnect() throws Exception{
		Connection con = DriverManager.getConnection("jdbc:ucanaccess://SjTestDB.accdb;memory=false");
		System.out.println("Access DB에 연결 성공");
		return con;
	}

}
