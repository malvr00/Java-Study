package accessDbConnect;

import java.sql.*;

public class AccessDbConnect {
	static Connection con;		// ��ü �������� �ʰ� ����ϹǷ� static
	static Statement smt;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
		  // Ư����ġ �����Ͽ� Connection ȹ�� (DB ����) ��
			// con = DriverManager.getConnection("jdbc:ucanaccess://c:\\DB\\SjTestDB.accdb;memory=false");
		  // Project Directory�� �ִ� DB���� ��
			con = DriverManager.getConnection("jdbc:ucanaccess://SjTestDB.accdb;memory=false");
			System.out.println("Access DB�� ���� ����");
		  // DB�� �����ϱ����� Statement ��ü ����
			smt = con.createStatement();
			System.out.println("Statement ��ü ���� ����!!");
			
			smt.close();
			con.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
