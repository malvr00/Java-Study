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
	private JTextField tf_Code, tf_name, tf_Kor, tf_Eng, tf_Mat;	// ȭ��� Event Handler�� ���
	private JButton newBt, addBt, updateBt, deleteBt;				// BUtton ��밡��/ �Ұ��ɿ� ���
	
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
			
}
