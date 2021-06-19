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
  // DB 연겷
	public Connection dbConnect() throws Exception{
		Connection con = DriverManager.getConnection("jdbc:ucanaccess://SjTestDB.accdb;memory=false");
		return con;
	}
	
	Statement smt;
	ResultSet rs;
	
  // DB 출력
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
	
  // DB 자료 위치이동
	public void cursorTest(Connection con) {
		try {
		  // ResultSet 옵션 사용 : 바뀐값 인식X, 읽기전용(기본값)
			smt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String sql = "Select * FROM Score order by strCode";
			rs = smt.executeQuery(sql);
		
		  // 처음부터 끝까지 차례대로 출력
			while(rs.next()) {
				printData();
			}
			System.out.println("whlie(rs.next()) End\n");
			
			while(rs.previous()) {
				printData();
			}
			System.out.println("whlie(rs.previous()) End\n");
			
			//rs.last();		// 마지막 행으로 이동
			//rs.first();			// 첫 번째 행으로 이동
			//rs.relative(2);		// 현 위치에서 2 행 뒤로 이동
			//rs.relative(-1);	// 현 위치에서 2 행 앞으로 이동
			rs.absolute(2);	// 앞에서 2번째 위치로 이동
			//rs.absolute(-2); 	// 뒤에서 2번째 위치로 이동
			printData();
			
			smt.close();
		}catch(SQLException e) {
			System.out.println("SQLException " + e.getMessage());
		}
	}
}
