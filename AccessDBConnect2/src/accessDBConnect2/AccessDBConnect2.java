package accessDBConnect2;

import java.sql.*;

public class AccessDBConnect2 {
	Connection con;
	Statement smt;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AccessDBConnect2 db = new AccessDBConnect2();
		db.dbConnect();
	}
	public void dbConnect() {
		try {
			con = DriverManager.getConnection("jdbc:ucanaccess://SjTestDB.accdb;memory=false");
			System.out.println("Access DB�� ���� ����");
			
			smt = con.createStatement();
			System.out.println("Statement ��ü ���� ����");
			smt.close();
			con.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
