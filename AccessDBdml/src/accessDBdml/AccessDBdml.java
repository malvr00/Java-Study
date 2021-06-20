package accessDBdml;

import java.sql.*;

public class AccessDBdml {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DmlTest db = new DmlTest();
		try {
			//db.printAllData();
			
			//db.insertData();		// �ڷ��߰�
			//db.updateData();		// �ڷ� ����
			//db.deleteData();		// �ڷ� ����
			//db.selectData();		// �ڷ� �Լ�, �ʵ� ����
			//db.totAvg();			// �ڷ� ����, ���
			db.rank();			// �ڷ� ���� �� ���� ���ϱ�
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		db.Close();
	}

}

class DmlTest{
	Connection con;
	Statement smt;
	String sql;
	
  // �����ڿ��� DB ����
	public DmlTest() {
		try {
			con = DriverManager.getConnection("jdbc:ucanaccess://SjTestDB.accdb;memory=false");
			smt = con.createStatement();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
  // DB Insert
	public void insertData() throws SQLException{
		 // INSERT INTO Table�� ( (Field��, ...) ) VALUES ( ������ �ڷ�, ...)
		
		// sql = "INSERT INTO Score (strCode, strName) VALUES ('1006', '����')";
													// �ʵ�� �����ϸ� ��ü �Է�
		sql = "INSERT INTO Score VALUES ('1008','����',99,88,77,0,0,0)";
		
		smt.executeUpdate(sql); 	// Select ������ ��� DML��ɾ�� executeUpdate
		printAllData();
	}  // Insert end
	
  // DB Update
	public void updateData() throws SQLException{
		// UPDATE Table�� SET Field�� = ������ ���� (WHERE ����)
		// sql = "UPDATE Score Set nKor=100";						// ���������� ���� 100���� ����
		sql = "UPDATE Score SET nKor=100 WHERE strName='����'";		// ������ ���� ������ 100���� ����
		
		smt.executeUpdate(sql);
		printAllData();
	}  // Update end
	
  // DB Delete
	public void deleteData() throws SQLException{
		// DELETE FROM Table�� ( WHERE ���� )
		// sql = "DELETE FROM Score WHERE strName='����'";
		// sql = "DELETE FROM Score WHERE strName LIKE '��%'";	// Access�� %�� ��� ����
		sql = "DELETE FROM Score WHERE strName LIKE '��*'";
		
		smt.executeUpdate(sql);
		printAllData();
	}  // Delete end
	
  // DB Select
	public void selectData() throws SQLException{
		ResultSet rs;
		
		sql = "Select COUNT(*) as count, sum(nKor) as total FROM Score";	// Record �� �� �հ�, Field  ����
		
		rs = smt.executeQuery(sql);
		rs.next();
		ResultSetMetaData meta;
		meta = rs.getMetaData();
		System.out.println(meta.getColumnName(1) + "\t" + meta.getColumnName(2));
		int cnt = rs.getInt(meta.getColumnName(1));
		System.out.println("Record �� :" + cnt);
		System.out.println("���� ���� �հ� : " + rs.getDouble(meta.getColumnName(2)));
	}  // Select end
	
 // DB ���� �� ��� Update
	public void totAvg() throws SQLException {
		ResultSet rs;
		int tot;
		double avg1, avg2;
		sql = "Select * FROM Score";
		rs = smt.executeQuery(sql);
		while(rs.next()) {
			tot = rs.getInt("nKor") + rs.getInt("nMat") + rs.getInt("nEng");
			avg2 = (double)tot / 3.0;
			avg1 = Math.round(avg2*10)/10.0;		// �ڸ� �ø�
			sql = "UPDATE Score Set nTotal = " + tot + ", dAverage =" + avg1 + " where strCode = '"+rs.getString("strCode")+" ' ";
			
			smt.executeUpdate(sql);
		}
		printAllData();
	} // totAvg end
	
  // DB Rank Update
	public void rank() throws SQLException{
		ResultSet rs;
		int tot=0, rank1 = 0, rank2 = 0;
		sql = "Select * FROM Score order by nTotal desc";
		rs = smt.executeQuery(sql);
		
		while(rs.next()) {
			rank1++;
			if(tot !=rs.getInt("nTotal")) {		// ���� ������ ���� ������ ���Ͽ� �ٸ���
				tot =rs.getInt("nTotal");		// ���������� tot ������ �Űܼ� ���� ���� �� ���
				rank2 = rank1;					// ����� ��� ������ ������
			}
			sql = "UPDATE Score Set nRank=" + rank2 + " where strCode = '" + rs.getString("strCode") + "'";
			smt.executeUpdate(sql);
		}
		printAllData();
	} // rank end
	
  // DB ���
	public void printAllData() throws SQLException{
		ResultSet rs;
		String hakBun, hakName;
		int kor, eng, mat;
		sql = "Select * FROM Score ";
		rs = smt.executeQuery(sql);
		
		while(rs.next()) {
			hakBun = rs.getString("strCode").trim();
			hakName = rs.getString("strName").trim();
			kor = rs.getInt("nKor");
			mat = rs.getInt("nMat");
			eng = rs.getInt("nEng");
			System.out.println(hakBun + "\t" + hakName + "\t" + kor + "\t" + mat + "\t" + eng + "\t" + 
			rs.getInt("nTotal") + "\t" + rs.getDouble("dAverage") + "\t" + rs.getInt("nRank"));
			System.out.println();
		}
	} // printAllData end
	
  // DB ����
	public void Close() {
		try {
			smt.close();
			con.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}  // close end
} // DB clasee end