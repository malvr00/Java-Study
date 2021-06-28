package dB_main;

public class DB_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DB_Frame frame;
		DB_DAO db = new DB_DAO();
		db.printMetaData("Score");
		db.printMetaData("Student");
		frame = new DB_Frame(db);
		frame.setVisible(true);
	}

}
