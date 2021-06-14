package accessDBConnect3;

import java.sql.*;

public class AccessDBConnect3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con = null;		// �������� �ʱ�ȭ
		Statement smt = null;		// �������� �ʱ�ȭ
		AccessDBConnect3 db = new AccessDBConnect3();
		try {
			con = db.dbConnect();
			smt = con.createStatement();
			System.out.println(con);		//Connection ���� ���
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			smt.close();		// statement ����
			con.close();		// DateBase���� ���� ����
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public Connection dbConnect() throws Exception{
		Connection con = DriverManager.getConnection("jdbc:ucanaccess://SjTestDB.accdb;memory=false");
		System.out.println("Access DB�� ���� ����");
		return con;
	}

}
