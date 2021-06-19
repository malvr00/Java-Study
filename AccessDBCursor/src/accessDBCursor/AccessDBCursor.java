package accessDBCursor;

import java.sql.*;

public class AccessDBCursor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con = null;
		AccessDBCursor db = new AccessDBCursor();
		
		try {
			con = db.dbConnect();
			db.cursorTest(con);
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
  // DB ����
	public Connection dbConnect() throws Exception{
		Connection con = DriverManager.getConnection("jdbc:ucanaccess://SjTestDB.accdb;memory=false");
		return con;
	}
	
	Statement smt;
	ResultSet rs;
	
  // DB ���
	public void printData() throws SQLException{
		String hakBun, hakName;
		int kor, eng, mat;
		hakBun = rs.getString("strCode");
		hakName = rs.getString("strName");
		kor = rs.getInt("nKor");
		eng = rs.getInt("nEng");
		mat = rs.getInt("nMat");
		
		System.out.println(hakBun + " " + hakName + " " + kor +" " + mat + " " + eng);
	}
	
  // DB �ڷ� ��ġ�̵�
	public void cursorTest(Connection con) {
		try {
		  // ResultSet �ɼ� ��� : �ٲﰪ �ν�X, �б�����(�⺻��)
			smt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String sql = "Select * FROM Score order by strCode";
			rs = smt.executeQuery(sql);
		
		  // ó������ ������ ���ʴ�� ���
			while(rs.next()) {
				printData();
			}
			System.out.println("whlie(rs.next()) End\n");
			
			while(rs.previous()) {
				printData();
			}
			System.out.println("whlie(rs.previous()) End\n");
			
			//rs.last();		// ������ ������ �̵�
			//rs.first();			// ù ��° ������ �̵�
			//rs.relative(2);		// �� ��ġ���� 2 �� �ڷ� �̵�
			//rs.relative(-1);	// �� ��ġ���� 2 �� ������ �̵�
			rs.absolute(2);	// �տ��� 2��° ��ġ�� �̵�
			//rs.absolute(-2); 	// �ڿ��� 2��° ��ġ�� �̵�
			printData();
			
			smt.close();
		}catch(SQLException e) {
			System.out.println("SQLException " + e.getMessage());
		}
	}
}
