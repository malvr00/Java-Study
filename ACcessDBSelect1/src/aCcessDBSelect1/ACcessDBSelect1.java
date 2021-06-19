package aCcessDBSelect1;

import java.sql.*;

public class ACcessDBSelect1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con = null;
		Statement smt = null;
		ACcessDBSelect1 db = new ACcessDBSelect1();
		try {
			con = db.dbConnect();
			smt = con.createStatement();
			db.selectData(smt);
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
  // DB ���� �Լ�
	public Connection dbConnect() throws Exception{
		Connection con = DriverManager.getConnection("jdbc:ucanaccess://SjTestDB.accdb;memory=false");
		return con;
	}
  // DB Select
	public void selectData(Statement smt) { 
		ResultSet rs;
		String sql;
		String hakBun;
		String hakName;
		int kor, eng, mat;
		
		try {
			//sql = "Select * FROM Score";									// ��� ���ڴ� �ҷ���
			// sql = "Select FROM Score where strName = '�ż���'";			// '�ż���' ã��
			sql = "Select * FROM Score where strName like '*��*'";			// '��' ������ ��� �ܾ�
			// sql = "Select * FROM Score where nkor >= 80";				// nkor 80���̻��� ���ڴ���
			// sql = "Select * FROM Score where nkor >= 80 and nMat = 100";	// nkor 80�̻��̸鼭 nMat�� 100���� ���ڴ���
			// sql = "Select * FROM Score order by strName asc";			// ��������
			// sql = "Select * FROM Score order by strName desc;			// ��������
			rs = smt.executeQuery(sql);
			
			while(rs.next()) {
				hakBun = rs.getString("strCode");
				hakName = rs.getString("strName");
				kor = rs.getInt("nkor");
				mat = rs.getInt("nMat");
				eng = rs.getInt("nEng");
				System.out.println(hakBun + " " + hakName + " " + kor +" " + mat + " " + eng + " ");
			}
		}catch(SQLException e) {
			System.out.println("SQLException " + e.getMessage());
		}
	}

}
