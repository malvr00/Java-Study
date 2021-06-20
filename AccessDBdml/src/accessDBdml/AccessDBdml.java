package accessDBdml;

import java.sql.*;

public class AccessDBdml {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DmlTest db = new DmlTest();
		try {
			//db.printAllData();
			
			//db.insertData();		// 자료추가
			//db.updateData();		// 자료 수정
			//db.deleteData();		// 자료 삭제
			//db.selectData();		// 자료 함수, 필드 별명
			//db.totAvg();			// 자료 총점, 평균
			db.rank();			// 자료 정렬 후 석차 구하기
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
	
  // 생성자에서 DB 연결
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
		 // INSERT INTO Table명 ( (Field명, ...) ) VALUES ( 대입할 자료, ...)
		
		// sql = "INSERT INTO Score (strCode, strName) VALUES ('1006', '세종')";
													// 필드명 생략하면 전체 입력
		sql = "INSERT INTO Score VALUES ('1008','세종',99,88,77,0,0,0)";
		
		smt.executeUpdate(sql); 	// Select 제외한 모든 DML명령어는 executeUpdate
		printAllData();
	}  // Insert end
	
  // DB Update
	public void updateData() throws SQLException{
		// UPDATE Table명 SET Field명 = 변경할 내용 (WHERE 조건)
		// sql = "UPDATE Score Set nKor=100";						// 국어점수를 전부 100으로 변경
		sql = "UPDATE Score SET nKor=100 WHERE strName='세종'";		// 세종의 국어 점수를 100으로 변경
		
		smt.executeUpdate(sql);
		printAllData();
	}  // Update end
	
  // DB Delete
	public void deleteData() throws SQLException{
		// DELETE FROM Table명 ( WHERE 조건 )
		// sql = "DELETE FROM Score WHERE strName='세종'";
		// sql = "DELETE FROM Score WHERE strName LIKE '세%'";	// Access는 %도 사용 가능
		sql = "DELETE FROM Score WHERE strName LIKE '세*'";
		
		smt.executeUpdate(sql);
		printAllData();
	}  // Delete end
	
  // DB Select
	public void selectData() throws SQLException{
		ResultSet rs;
		
		sql = "Select COUNT(*) as count, sum(nKor) as total FROM Score";	// Record 수 와 합계, Field  별명
		
		rs = smt.executeQuery(sql);
		rs.next();
		ResultSetMetaData meta;
		meta = rs.getMetaData();
		System.out.println(meta.getColumnName(1) + "\t" + meta.getColumnName(2));
		int cnt = rs.getInt(meta.getColumnName(1));
		System.out.println("Record 수 :" + cnt);
		System.out.println("국어 점수 합계 : " + rs.getDouble(meta.getColumnName(2)));
	}  // Select end
	
 // DB 총점 및 평균 Update
	public void totAvg() throws SQLException {
		ResultSet rs;
		int tot;
		double avg1, avg2;
		sql = "Select * FROM Score";
		rs = smt.executeQuery(sql);
		while(rs.next()) {
			tot = rs.getInt("nKor") + rs.getInt("nMat") + rs.getInt("nEng");
			avg2 = (double)tot / 3.0;
			avg1 = Math.round(avg2*10)/10.0;		// 자리 올림
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
			if(tot !=rs.getInt("nTotal")) {		// 이전 점수와 현재 점수를 비교하여 다르면
				tot =rs.getInt("nTotal");		// 현재점수를 tot 변수에 옮겨서 다음 비교할 때 사용
				rank2 = rank1;					// 등수도 계속 누적한 등수사용
			}
			sql = "UPDATE Score Set nRank=" + rank2 + " where strCode = '" + rs.getString("strCode") + "'";
			smt.executeUpdate(sql);
		}
		printAllData();
	} // rank end
	
  // DB 출력
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
	
  // DB 종료
	public void Close() {
		try {
			smt.close();
			con.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}  // close end
} // DB clasee end