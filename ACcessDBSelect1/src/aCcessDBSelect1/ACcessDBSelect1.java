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
  // DB 연결 함수
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
			//sql = "Select * FROM Score";									// 모든 레코더 불러옴
			// sql = "Select FROM Score where strName = '신세종'";			// '신세종' 찾기
			sql = "Select * FROM Score where strName like '*신*'";			// '신' 포함한 모든 단어
			// sql = "Select * FROM Score where nkor >= 80";				// nkor 80점이상인 레코더만
			// sql = "Select * FROM Score where nkor >= 80 and nMat = 100";	// nkor 80이상이면서 nMat는 100점인 레코더만
			// sql = "Select * FROM Score order by strName asc";			// 오름차순
			// sql = "Select * FROM Score order by strName desc;			// 내림차순
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
