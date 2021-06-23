package dB_main;

import java.sql.*;

public class DB_DAO {		// DAO( Data Access Object ) : Daga 접근을 위한 객체
	private Connection con;
	private Statement smt;
	public DB_DAO() {
		try{				// 생성자에서 DB 연결
			con = DriverManager.getConnection("jdbc:ucanaccess://SjTestDB.accdb;memory=false");
			smt = con.createStatement();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
  // DB Meta Data 출력 함수
	public void printMetaData(String tableName) {
		ResultSet rs = getResultSet("select * FROM " + tableName);
		try {
			ResultSetMetaData meta = rs.getMetaData();
			int colCnt = meta.getColumnCount();
			System.out.println(meta.getTableName(1) + " Table의 field 수 : " + colCnt);
			System.out.println("Field명 \t Data형");
			for(int i= 1; i<=colCnt; i++) {
				System.out.printf("%-10s %s\n",meta.getColumnName(i), meta.getColumnTypeName(i));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}	// printMetaData end
	
  // DB executeQuery 함수
	public ResultSet getResultSet(String sql) {
		try {
			return smt.executeQuery(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}	//getResultSet end
	
  // DB executeUpdate 함수
	public void Excute(String sql) {
		try {
			smt.executeUpdate(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}	// Excute end
  // DB 종료 함수
	public void Close() {
		try {
			smt.close();
			con.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}	// Close end
}  // DB_DAO end
