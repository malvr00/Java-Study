package jTextComponentTest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JTextComponentTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		swText frame = new swText("TextField Test");
		frame.initForm();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

}

class swText extends JFrame{
	JPanel pan1;
	JButton button1;
	JButton button2;
	Container cpane;
	JTextField txtField1;
	JTextField txtField2;
	JTextField txtField3;
	JTextArea txtArea;
	
	public swText() {}
	public swText(String str) {
		super(str);
	}
	public void initForm() {
		cpane = getContentPane();
		pan1 = new JPanel();
		pan1.setLayout(new BoxLayout(pan1,BoxLayout.Y_AXIS));
		
		txtField1 = new JTextField(30);
		txtField2 = new JTextField("Text Field Test", 20);
		txtField2.setEditable(true);
		txtField3 = new JTextField(40);
		txtField3.setEditable(false);;
		txtField3.setText(txtField2.getText());
		
		pan1.add(txtField1);
		pan1.add(txtField2);
		pan1.add(txtField3);
		
		// �̺�Ʈ ó��
		txtField1.addActionListener(new JTextActionHandler());
		txtField1.addKeyListener(new JTextKeyHandler());
		txtField1.addFocusListener(new JTextFocusHandler());
		txtField2.addFocusListener(new JTextFocusHandler());
		
		txtArea = new JTextArea(10, 20);
		
		// TextArea ��ũ�� ��� �߰�
		JScrollPane scrollPane1 = new JScrollPane(txtArea);
		// ���� Scroll ��� ���, ���� Scroll ��� ������.
		scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		button1 = new JButton("������");
		button1.addActionListener(new JTextActionHandler2());
		cpane.add(button1,"Center");
		cpane.add(pan1,"North");
		cpane.add(scrollPane1,"South");
	}
	
	// txtField1 Event
	public class JTextActionHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
		// ���� �Է� �� Enter �Է��ϸ� ��� 
			System.out.println("Enter Key �Էµ� ������" + e.getActionCommand());
			txtArea.append("Enter Key �Էµ� ������ " + e.getActionCommand() + "\n");
			txtField3.setText(txtField1.getText());
		}
	}
	
	// Button Event 
	public class JTextActionHandler2 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			txtField2.requestFocus(true);		// txtField2 Focus �j��
			txtField2.selectAll();				// txtField2 ��ü ����
		}
	}
	
	// TextFocus Event
	public class JTextFocusHandler implements FocusListener{
		JTextField text;
		String str;
	// Focus �j��
		public void focusGained(FocusEvent e) {
		// �߻��� �̺�Ʈ text�� ����
			text = (JTextField)e.getSource();
		// ��𿡼� �߻��ߴ��� �߻��� �̺�Ʈ�� ��
			if(text.equals(txtField1))
				str = "textField1";
			else if(text.equals(txtField2))
				str = "txtField2";
			else if(text.equals(txtField3))
				str = "txtField3";
			
			System.out.println(str + " Focus ���� ");
			txtArea.append(str + " Focus ����\n");
		}
	// Focus ����
		public void focusLost(FocusEvent e) {
			text = (JTextField)e.getSource();
			if(text.equals(txtField1))
				str = "textField1";
			else if(text.equals(txtField2))
				str = "txtField2";
			else if(text.equals(txtField3))
				str = "txtField3";
			System.out.println(str+ "Focus ���� \n");;
			txtArea.append(str + "Focus ����");
		}
	}
	// txtField1 KeyEvent
	public class JTextKeyHandler extends KeyAdapter{
		public void keyTyped(KeyEvent e) {
		// BACK_SPACE�� ������ Key �Է½�
			if(e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
				System.out.println("Key ���� " + e.getKeyChar());
				txtArea.append("Key ����  "+ e.getKeyChar() + "\n" );
			}
		}
	}
}