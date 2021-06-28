package dB_main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;

public class DB_Frame extends JFrame{
	private DB_DAO stuDB;					// sql ó�� class
	private DB_TableMode tablemodel;		// table class
	private ResultSet result;
	private JTable table;
	private int dataCount, selectedCol, sw = 1;
	private JTextField tf_Code, tf_Name, tf_Kor, tf_Eng, tf_Mat;	// ȭ��� Event Handler�� ���
	private JButton newBt, addBt, updateBt, deleteBt;				// Button ��� ����/ �Ұ��ɿ� ���
	
	public DB_Frame(){}
	public DB_Frame(DB_DAO db) {
		super("���� ����");
		stuDB = db;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		result = stuDB.getResultSet("Select * FROM Score ORDER BY strCode");
		
		initForm();
	}
	
  // ȭ�� ������ �Լ�
	public void initForm() {
		Container cpane = getContentPane();
		JPanel leftPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JLabel label;
		JButton bt;
		selectedCol = -1;
		GridBagLayout gbl = new GridBagLayout();		// GridBagLayout ��ü����
		leftPanel.setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();	// GridBagLayout�� �����ϱ� ���� ��ü
		gbc.fill = GridBagConstraints.BOTH;					// Ư�� �������� �÷��� ��Ÿ����
		gbc.weightx = 1;									// 0 : ȭ���� �߾ӿ�, 1 : ȭ�鿡 �� �� ���
		gbc.weighty = 1;
		
		setGrid(gbc, 0, 1, 1, 1);
		label = new JLabel("		��   ȣ	");
		gbl.setConstraints(label, gbc);
		leftPanel.add(label);
		
		setGrid(gbc,1,1,1,1);
		tf_Code = new JTextField(5);
		gbl.setConstraints(tf_Code, gbc);
		leftPanel.add(tf_Code);
		
		setGrid(gbc, 0,2,1,1);
		label = new JLabel("		��   ��	");
		gbl.setConstraints(label, gbc);
		leftPanel.add(label);
		
		setGrid(gbc,1,2,1,1);
		tf_Name = new JTextField(10);
		gbl.setConstraints(tf_Name, gbc);
		leftPanel.add(tf_Name);
		
		setGrid(gbc,0,3,1,1);
		label = new JLabel("		��   ��	");
		gbl.setConstraints(label, gbc);
		leftPanel.add(label);
		
		setGrid(gbc,1,3,1,1);
		tf_Kor = new JTextField(5);
		gbl.setConstraints(tf_Kor, gbc);
		leftPanel.add(tf_Kor);
		
		setGrid(gbc,0,4,1,1);
		label = new JLabel("		��  ��	");
		gbl.setConstraints(label, gbc);
		leftPanel.add(label);
		
		setGrid(gbc,1,4,1,1);
		tf_Mat = new JTextField(5);
		gbl.setConstraints(tf_Mat, gbc);
		leftPanel.add(tf_Mat);
		
		setGrid(gbc,0,5,1,1);
		label = new JLabel("		��   ��	");
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
		
	// Button �߰� - ó��, ����, ����, ������
		setGrid(gbc,0,9,1,1);
		bt = new JButton("ó��");
		bt.addActionListener(new FirstButtonListener());
		gbl.setConstraints(bt, gbc);
		leftPanel.add(bt);
		
		setGrid(gbc,1,9,1,1);
		bt = new JButton("����");
		bt.addActionListener(new PreviousButtonListener());
		gbl.setConstraints(bt, gbc);
		leftPanel.add(bt);
		
		setGrid(gbc,2,9,1,1);
		bt = new JButton("����");
		bt.addActionListener(new NextButtonListener());
		gbl.setConstraints(bt, gbc);
		leftPanel.add(bt);
		
		setGrid(gbc,3,9,1,1);
		bt = new JButton("������");
		bt.addActionListener(new EndButtonListener());
		gbl.setConstraints(bt, gbc);
		leftPanel.add(bt);
		
		setGrid(gbc,3,0,1,1);
		newBt = new JButton("���ڷ�");
		newBt.addActionListener(new newButtonListener());
		gbl.setConstraints(newBt, gbc);
		leftPanel.add(newBt);
		
		setGrid(gbc,3,1,1,1);
		addBt = new JButton("���");
		addBt.addActionListener(new addButtonListener());
		gbl.setConstraints(addBt, gbc);
		leftPanel.add(addBt);
		
		setGrid(gbc,3,2,1,1);
		updateBt = new JButton("����");
		updateBt.addActionListener(new updateButtonListener());
		gbl.setConstraints(updateBt, gbc);
		leftPanel.add(updateBt);
		
		setGrid(gbc,3,3,1,1);
		deleteBt = new JButton("����");
		deleteBt.addActionListener(new deleteButtonListener());
		gbl.setConstraints(deleteBt, gbc);
		leftPanel.add(deleteBt);
		
		setGrid(gbc,3,5,1,1);
		bt = new JButton("����");
		bt.addActionListener(new sortButtonListener());
		gbl.setConstraints(bt, gbc);
		leftPanel.add(bt);
		
		setGrid(gbc,3,7,1,1);
		bt = new JButton("����");
		bt.addActionListener(new exitButtonListener());
		gbl.setConstraints(bt, gbc);
		leftPanel.add(bt);
		
		setEnabledButton(true);
		
	// Table ����
		String columnName[] = {"�й�","�̸�","����","����","����","����","���","����"};
		tablemodel = new DB_TableMode(columnName.length, columnName);
		table = new JTable(tablemodel);			// Table ����
		
		table.setPreferredScrollableViewportSize(new Dimension(470, 14*16));		// Table ũ�� ����
		table.getSelectionModel().addListSelectionListener(new tableListener());
		JScrollPane scrollPane = new JScrollPane(table);
		centerPanel.add(scrollPane);
		
		cpane.add("West", leftPanel);
		cpane.add("Center",centerPanel);
		pack();
		
		LoadList();		// Table�� DB��ǥ �߰�
		
		try {
			result.first();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		MoveData();		// TextField�� ù��° �ڷ� ��Ÿ��
	} // initForm end
	
  // Button ��� ���� / �Ұ��� �Լ�
	private void setEnabledButton(Boolean bool) {
		newBt.setEnabled(bool);
		addBt.setEnabled(!bool);
		updateBt.setEnabled(bool);
		deleteBt.setEnabled(bool);
	} // setEnabledButton end
	
  // GridBagConstraints �Լ�
	private void setGrid(GridBagConstraints gbc, int dx, int dy, int width, int height) {
	  // ��� ��ġ
		gbc.gridx = dx;
		gbc.gridy = dy;
	  // ��� ũ��
		gbc.gridwidth = width;
		gbc.gridheight = height;
	} // setGrid end
	
  // Table�� �ڷ� ����
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
	
  // Table�� �ڷ� ����� �Լ�
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
	
  // DdataBase ���� Table�� ���
	private void LoadList() {
	  // �ڵ��, ������ ������ ��Ÿ���� ���Ͽ�
		if(sw == 1)
			result = stuDB.getResultSet("SELECT * FROM Score ORDER BY strCode");
		else
			result = stuDB.getResultSet("SELECT * FROM Score ORDER BY nTotal desc");
	  // Table�� ���系�� �����
		for(int i=0; i<dataCount; i++) {
			removeTableRow(i);
		}
		
		try {
			for(dataCount=0; result.next(); dataCount++) {
				inputTable(dataCount, result.getString("strCode"), result.getString("strName"),result.getInt("nKor"),result.getInt("nMat"),
						result.getInt("nEng"), result.getInt("nTotal"),result.getDouble("dAverage"),result.getInt("nRank"));
			}
			repaint();			// Table �ٽ� �׸���
		}catch(SQLException e) {
			e.printStackTrace();
		}
	} // LoadList end

  // DB �������
	private void rank() {
		int tot=0, rank1 = 0, rank2 = 0;
		String sql;
		ResultSet rs = stuDB.getResultSet("SELECT * FROM Score ORDER BY nTotal desc");
		try {
			while(rs.next()) {
				rank1++;
				if(tot != rs.getInt("nTotal")) {		// ���� ������ ���� ������ ���Ͽ� �ٸ���
					tot = rs.getInt("nTotal");			// ���������� tot������ �Űܼ� ���� ���� �� ���
					rank2 = rank1;						// ����� ��� ������ ������
				}
				sql = "UPDATE Score Set nRank="+ rank2 + " WHERE strCode ='"+ rs.getString("strCode") + "'";
				stuDB.Excute(sql);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	} // rank end
	
  // ���õ� JTable ������ JTextField�� ������ �Լ�
	public void MoveData() {
		try {
			String hakBun = result.getString("strCode");
			String name = result.getString("strName");
			String kor = String.valueOf(result.getInt("nKor"));
			String eng = String.valueOf(result.getInt("nEng"));
			String mat = String.valueOf(result.getInt("nMat"));
			tf_Code.setText(hakBun);
			tf_Name.setText(name);
			tf_Kor.setText(kor);
			tf_Mat.setText(eng);
			tf_Eng.setText(mat);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	} // MoveData end
	
  // Table Event
	class tableListener implements ListSelectionListener{		// Event 2�� �߻�
		public void valueChanged(ListSelectionEvent e) {
			if(e.getValueIsAdjusting())							// Event 2�� �߻�, �߰� �޼��� ����
				return;
			ListSelectionModel lsm = (ListSelectionModel)e.getSource();
			if(lsm.isSelectionEmpty())
				System.out.println("No columns are selected");
			else {
				selectedCol = lsm.getMinSelectionIndex();		// ������ �ε����� ��ȯ or ��������� -1 ��ȯ
				if(selectedCol >= dataCount)
					System.out.println("data is Empty");
				else {
					tf_Code.setText(table.getValueAt(selectedCol,  0).toString());
					tf_Name.setText(table.getValueAt(selectedCol, 1).toString());
					tf_Kor.setText(table.getValueAt(selectedCol,  2).toString());
					tf_Mat.setText(table.getValueAt(selectedCol,  3).toString());
					tf_Eng.setText(table.getValueAt(selectedCol,  4).toString());
					try {
						result.absolute(selectedCol+1);		// ������ ��ġ�� cursor �̵�
						MoveData();
					}catch(Exception e1) {
						e1.printStackTrace();
					}
					repaint();
				}
			}
		}
	} // tableListener end
	
  // newButton Event
	class newButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			tf_Code.setText(null);
			tf_Name.setText(null);
			tf_Kor.setText(null);
			tf_Mat.setText(null);
			tf_Eng.setText(null);
			
			setEnabledButton(false);
		}
	} // newButtonListener end
	
  // addButton Event
	class addButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String sql;
			if(tf_Code.getText().isEmpty()) {
				System.out.println("��ȣ�� �Էµ��� �ʾҽ��ϴ�.");
				return;
			}
			int total;
			total = Integer.parseInt(tf_Kor.getText()) + Integer.parseInt(tf_Mat.getText()) + Integer.parseInt(tf_Eng.getText());
			sql = "INSERT INTO Score(strCode, strName, nKor, nMat, nEng, nTotal, dAverage) VALUES('" + tf_Code.getText() + "','" + 
					tf_Name.getText() + "'," + tf_Kor.getText() + "," + tf_Eng.getText() + "," + tf_Mat.getText() + ","
					+ Integer.toString(total) + "," + Double.toString(total/3.0) + ")";
			System.out.println(sql);			// sqlȮ��, ȭ�鿡 ���
			
			stuDB.Excute(sql);
			rank();
			LoadList();
			setEnabledButton(true);
		}
	} // addButtonListener end
	
  // updateButton Event
	class updateButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String sql, code;
			if(selectedCol == -1) {
				System.out.println("������ ���� ���õ��� �ʾҽ��ϴ�.");
				return;
			}
			code = table.getValueAt(selectedCol, 0).toString();
			int total = Integer.parseInt(tf_Kor.getText()) + Integer.parseInt(tf_Mat.getText()) + Integer.parseInt(tf_Eng.getText());
			sql = "UPDATE Score SET strName ='" + tf_Name.getText() + " ', nKor=" + tf_Kor.getText() + ", nMat=" + tf_Mat.getText() + ", nEng=" + 
			tf_Eng.getText() + ", nTotal=" + Integer.toString(total) + ", dAverage=" + Double.toString(total/3.0) + " WHERE strCode = '" + code + "'";
			
			System.out.println(sql);
			stuDB.Excute(sql);
			rank();
			LoadList();
		}
	} // updateButtonListener end
	
  // Delete Event 
	class deleteButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String sql, code;
			if(selectedCol == -1) {
				System.out.println("������ ���� ���õ��� �ʾҽ��ϴ�.");
				return;
			}
			code = table.getValueAt(selectedCol, 0).toString();
			sql = "DELETE FROM Score WHERE strCode ='" + code + "'";
			stuDB.Excute(sql);
			rank();
			LoadList();
		}
	} // deleteButtonListener end
	
  // FirstButtonListener Event
	class FirstButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				result.first();
			}catch(SQLException e1) {
				System.err.println("SQLEXception " + e1.getMessage());
			}
		}
	} // FirstButtonListener end
	
  // Previous Event
	class PreviousButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				result.previous();
				if(result.isBeforeFirst())
					result.first();
			}catch(SQLException e1) {
				System.err.println("SQLException" + e1.getMessage());
			}
			MoveData();
		}
	} // PreviousButtonListener end
	
  // Next Event
	class NextButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				result.next();
				if(result.isAfterLast()) {
					result.last();
				}
			}catch(SQLException e1) {
				System.err.println("SQLException" + e1.getMessage());
			}
			MoveData();
		}
	} // NextButtonListenet end
	
  // End Event
	class EndButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				result.last();
			}catch(SQLException e1) {
				System.err.println("SQLException" + e1.getMessage());
			}
			MoveData();
		}
	} // EndButtonListener end
	
  // Sort Evnet
	class sortButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			sw *= -1;
			LoadList();
		}
	} // sortButtonListener end
	
  // end Event
	class exitButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			stuDB.Close();
			System.exit(0);
		}
	} // exitButtonListener end
} // DB_FrameEnd
