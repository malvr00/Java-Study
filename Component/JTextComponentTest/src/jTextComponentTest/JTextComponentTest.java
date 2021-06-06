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
		
		// 이벤트 처리
		txtField1.addActionListener(new JTextActionHandler());
		txtField1.addKeyListener(new JTextKeyHandler());
		txtField1.addFocusListener(new JTextFocusHandler());
		txtField2.addFocusListener(new JTextFocusHandler());
		
		txtArea = new JTextArea(10, 20);
		
		// TextArea 스크롤 기능 추가
		JScrollPane scrollPane1 = new JScrollPane(txtArea);
		// 수직 Scroll 기능 사용, 수평 Scroll 기능 사용안함.
		scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		button1 = new JButton("눌러봐");
		button1.addActionListener(new JTextActionHandler2());
		cpane.add(button1,"Center");
		cpane.add(pan1,"North");
		cpane.add(scrollPane1,"South");
	}
	
	// txtField1 Event
	public class JTextActionHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
		// 내용 입력 후 Enter 입력하면 출력 
			System.out.println("Enter Key 입력된 내용은" + e.getActionCommand());
			txtArea.append("Enter Key 입력된 내용은 " + e.getActionCommand() + "\n");
			txtField3.setText(txtField1.getText());
		}
	}
	
	// Button Event 
	public class JTextActionHandler2 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			txtField2.requestFocus(true);		// txtField2 Focus 엏음
			txtField2.selectAll();				// txtField2 전체 선택
		}
	}
	
	// TextFocus Event
	public class JTextFocusHandler implements FocusListener{
		JTextField text;
		String str;
	// Focus 엏음
		public void focusGained(FocusEvent e) {
		// 발생한 이벤트 text에 대입
			text = (JTextField)e.getSource();
		// 어디에서 발생했는지 발생한 이벤트와 비교
			if(text.equals(txtField1))
				str = "textField1";
			else if(text.equals(txtField2))
				str = "txtField2";
			else if(text.equals(txtField3))
				str = "txtField3";
			
			System.out.println(str + " Focus 얻음 ");
			txtArea.append(str + " Focus 얻음\n");
		}
	// Focus 잃음
		public void focusLost(FocusEvent e) {
			text = (JTextField)e.getSource();
			if(text.equals(txtField1))
				str = "textField1";
			else if(text.equals(txtField2))
				str = "txtField2";
			else if(text.equals(txtField3))
				str = "txtField3";
			System.out.println(str+ "Focus 잃음 \n");;
			txtArea.append(str + "Focus 잃음");
		}
	}
	// txtField1 KeyEvent
	public class JTextKeyHandler extends KeyAdapter{
		public void keyTyped(KeyEvent e) {
		// BACK_SPACE를 제외한 Key 입력시
			if(e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
				System.out.println("Key 눌림 " + e.getKeyChar());
				txtArea.append("Key 눌림  "+ e.getKeyChar() + "\n" );
			}
		}
	}
}