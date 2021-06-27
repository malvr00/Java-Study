package dB_main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;

public class DB_Frame extends JFrame{
	private DB_DAO stuDB;
	private DB_TableMode tablemodel;
	private ResultSet result;
	private JTable table;
	private int dataCount, selectedCol, sw = 1;
	private JTextField tf_Code, tf_name, tf_Kor, tf_Eng, tf_Mat;	// 화면과 Event Handler에 사용
	private JButton newBt, addBt, updateBt, deleteBt;				// BUtton 사용가능/ 불가능에 사용
	
	public DB_Frame(){}
	public DB_Frame(DB_DAO db) {
		super("성적 관리");
		stuDB = db;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		result = stuDB.getResultSet("Select * FROM Score ORDER BY strCode");
		
		initForm();
	}
	
  // 화면 디자인 함수
	public void initForm() {
		Container cpane = getContentPane();
		JPanel leftPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JLabel label;
		JButton bt;
		selectedCol = -1;
		GridBagLayout gbl = new GridBagLayout();		// GridBagLayout 객체생성
		leftPanel.setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();	// GridBagLayout을 제어하기 위한 객체
		gbc.fill = GridBagConstraints.BOTH;					// 특정 방향으로 늘려서 나타내기
		gbc.weightx = 1;									// 0 : 화면의 중앙에, 1 : 화면에 꽉 찬 모양
		gbc.weighty = 1;
		
		setGrid(gbc, 0, 1, 1, 1);
		label = new JLabel("		번   호	");
		gbl.setConstraints(label, gbc);
		leftPanel.add(label);
		
		setGrid(gbc,1,1,1,1);
		tf_Code = new JTextField(5);
		gbl.setConstraints(tf_Code, gbc);
		leftPanel.add(tf_Code);
		
		setGrid(gbc, 0,2,1,1);
		label = new JLabel("		이   름	");
		gbl.setConstraints(label, gbc);
		leftPanel.add(label);
		
		setGrid(gbc,1,2,1,1);
		tf_Name = new JTextField(10);
		gbl.setConstraints(tf_Name, gbc);
		leftPanel.add(tf_Name);
		
		setGrid(gbc,0,3,1,1);
		label = new JLabel("		국   어	");
		gbl.setConstraints(label, gbc);
		leftPanel.add(label);
		
		setGrid(gbc,1,3,1,1);
		tf_Kor = new JTextField(5);
		gbl.setConstraints(tf_Kor, gbc);
		leftPanel.add(tf_Kor);
		
		setGrid(gbc,0,4,1,1);
		label = new JLabel("		수  학	");
		gbl.setConstraints(label, gbc);
		leftPanel.add(label);
		
		setGrid(gbc,1,4,1,1);
		tf_Mat = new JTextField(5);
		gbl.setConstraints(tf_Mat, gbc);
		leftPanel.add(tf_Mat);
		
		setGrid(gbc,0,5,1,1);
		label = new JLabel("		영   어	");
		gbl.setConstraints(label, gbc);
		leftPanel.add(label);
		
		setGrid(gbc,1,5,1,1);
		tf_Eng = new JTextField(5);
		gbl.setConstraints(tf_Eng, gbc);
		leftPanel.add(tf_Eng);
		
		setGrid(gbc,0,6,1,1);
		label = new JLabel("  ");
		gbl.setConstraints(label, gbc);
		leftPanel.add(label);
		
		setGrid(gbc,0,8,1,1);
		label = new JLabel("  ");
		gbl.setConstraints(label, gbc);
		leftPanel.add(label);
		
	// Button 추가 - 처음, 이전, 다음, 마지막
		setGrid(gbc,0,9,1,1);
		bt = new JButton("처음");
		bt.addActionListener(new FirstButtonListener());
		gbl.setConstraints(bt, gbc);
		leftPanel.add(bt);
		
		setGrid(gbc,1,9,1,1);
		bt = new JButton("이전");
		bt.addActionListener(new PreviousButtonListener());
		gbl.setConstraints(bt, gbc);
		leftPanel.add(bt);
		
		setGrid(gbc,2,9,1,1);
		bt = new JButton("다음");
		bt.addActionListener(new NextButtonListener());
		gbl.setConstraints(bt, gbc);
		leftPanel.add(bt);
		
		setGrid(gbc,3,9,1,1);
		bt = new JButton("마지막");
		bt.addActionListener(new EndButtonListener());
		gbl.setConstraints(bt, gbc);
		leftPanel.add(bt);
		
		setGrid(gbc,3,0,1,1);
		newBt = new JButton("새자료");
		newBt.addActionListener(new newButtonListener());
		gbl.setConstraints(newBt, gbc);
		leftPanel.add(newBt);
		
		setGrid(gbc,3,1,1,1);
		addBt = new JButton("등록");
		addBt.addActionListener(new addButtonListener());
		gbl.setConstraints(addBt, gbc);
		leftPanel.add(addBt);
		
		setGrid(gbc,3,2,1,1);
		updateBt = new JButton("수정");
		updateBt.addActionListener(new updateButtonListener());
		gbl.setConstraints(updateBt, gbc);
		leftPanel.add(updateBt);
		
		setGrid(gbc,3,3,1,1);
		deleteBt = new JButton("삭제");
		deleteBt.addActionListener(new deleteButtonListener());
		gbl.setConstraints(deleteBt, gbc);
		leftPanel.add(deleteBt);
		
		setGrid(gbc,3,5,1,1);
		bt = new JButton("정렬");
		bt.addActionListener(new sortButtonListener());
		gbl.setConstraints(bt, gbc);
		leftPanel.add(bt);
		
		setGrid(gbc,3,7,1,1);
		bt = new JButton("종료");
		bt.addActionListener(new exitButtonListener());
		gbl.setConstraints(bt, gbc);
		leftPanel.add(bt);
		
		setEnabledButton(true);
		
	// Table 설정
		String columnName[] = {"학번","이름","국어","수학","영어","총점","평균","석차"};
		tablemodel = new DB_TableMode(columnName.length, columnName);
		table = new JTable(tablemodel);			// Table 설정
		
		table.setPreferredScrollableViewportSize(new Dimension(470, 14*16));		// Table 크지 지정
		table.getSelectionModel().addListSelectionListener(new tableListener());
		JScrollPane scrollPane = new JScrollPane(table);
		centerPanel.add(scrollPane);
		
		cpane.add("West", leftPanel);
		cpane.add("Center",centerPanel);
		pack();
		
		LoadList();		// Table에 DB자표 추가
		
		try {
			result.first();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		MoveData();		// TextField에 첫번째 자료 나타냄
	} // initForm end
	
  // Button 사용 가능 / 불가능 함수
	private void setEnabledButton(Boolean bool) {
		newBt.setEnabled(bool);
		addBt.setEnabled(!bool);
		updateBt.setEnabled(bool);
		deleteBt.setEnabled(bool);
	} // setEnabledButton end
	
  // GridBagConstraints 함수
	private void setGrid(GridBagConstraints gbc, int dx, int dy, int width, int height) {
	  // 출력 위치
		gbc.gridx = dx;
		gbc.gridy = dy;
	  // 출력 크기
		gbc.gridwidth = width;
		gbc.gridheight = height;
	} // setGrid end
	
  // Table에 자료 대입
	private void inputTable(int row, String num, String name, int kor, int mat, int eng, int total, double average, int rank) {
		table.setValueAt(num, row, 0);
		table.setValueAt(name, row, 1);
		table.setValueAt(kor, row, 2);
		table.setValueAt(mat, row, 3);
		table.setValueAt(eng, row, 4);
		table.setValueAt(total, row, 5);
		table.setValueAt(average, row, 6);
		table.setValueAt(rank, row, 7);
	} // inputTable end
	
  // Table에 자료 지우는 함수
	private void removeTableRow(int row) {
		table.setValueAt(null, row, 0);
		table.setValueAt(null, row, 1);
		table.setValueAt(null, row, 2);
		table.setValueAt(null, row, 3);
		table.setValueAt(null, row, 4);
		table.setValueAt(null, row, 5);
		table.setValueAt(null, row, 6);
		table.setValueAt(null, row, 7);
	}  // removeTable end
			
}
