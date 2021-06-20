package accessDBMetaData;

import java.sql.*;

public class AccessDBMetaData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con = null;
		Statement smt = null;
		AccessDBMetaData db = new AccessDBMetaData();
		try {
			con = db.dbConnect();
			smt = con.createStatement();
			db.metaData(smt);
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			smt.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
  // DB ����
	public Connection dbConnect() throws Exception{
		Connection con = DriverManager.getConnection("jdbc:ucanaccess://SjTestDB.accdb;memory:false");
		return con;
	}

	public void metaData(Statement smt) {
		String sql;
		ResultSet rs;
		ResultSetMetaData meta;
		int colCnt;
		try {
			sql = "Select * FROM Score";
			
			rs  =smt.executeQuery(sql);
			meta = rs.getMetaData();
			colCnt = meta.getColumnCount();
			System.out.println("�ʵ�� : " + colCnt);
			System.out.println("�ʵ��\t DATA��");
			for(int i = 1; i<=colCnt; i++) {
				System.out.println(meta.getColumnName(i) + "\t" + meta.getColumnTypeName(i));
			}
		}catch(SQLException e) {
			System.out.println("SQLException " + e.getMessage());
		}
	}
}
