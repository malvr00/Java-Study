package chattingFrame;

import javax.swing.*;
import java.awt.*;

public class ChattingFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChatFrame chatForm = new ChatFrame("Java Chatting");
		chatForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chatForm.initForm();
		chatForm.pack();
		chatForm.setVisible(true);
	}

}

class ChatFrame extends JFrame{
	JTextArea showText, showUser;
	JTextField ServerIp, PortNo, UserName, Users, MessageBox;
	JButton StartBt, StopBt, SendBt, ExpulsionBt;
	JPanel pan1, pan2, pan21,pan22,pan23;
	public ChatFrame() {}
	public ChatFrame(final String str) {
		super(str);
	}
	public void  initForm() {
		pan1 = new JPanel();
		pan2 = new JPanel();
		pan21 = new JPanel();
		pan22 = new JPanel();
		pan23 = new JPanel();
		
	// Panel1 메세지 입력 및 출력
		showText = new JTextArea(20, 40);
		MessageBox = new JTextField(40);
		
	// Panel21 Server 접속창
		ServerIp = new JTextField("localhost",10);
		PortNo = new JTextField("1234",10);
		UserName = new JTextField("Server", 10);
		StartBt = new JButton("Server Start");
		StopBt = new JButton("Server Stop");
		
	// Panel22 User 접속현황 창
		showUser = new JTextArea(10, 20);
		
	// Panel23 강퇴 및 메세지 보내기
		SendBt = new JButton("Send");
		ExpulsionBt = new JButton("강퇴");
		
	// Panel1 제작
		pan1.setLayout(new BorderLayout());
		JScrollPane scrollPane1 = new JScrollPane(showText);
		scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		pan1.add("North",scrollPane1);
		pan1.add("Center", MessageBox);
		
	// Panel2 제작	
		pan2.setLayout(new BorderLayout());
		pan21.setLayout(new GridLayout(4,2));
		pan22.setLayout(new BorderLayout());
		pan23.setLayout(new BorderLayout());
		
		pan21.add(new Label(" Server Ip"));
		pan21.add(ServerIp);
		pan21.add(new Label(" Port No"));
		pan21.add(PortNo);
		pan21.add(new Label(" Name"));
		pan21.add(UserName);
		pan21.add(StartBt);
		pan21.add(StopBt);
		
		pan22.add("North",new Label("접속자"));
		pan22.add("Center", showUser);
		
		pan23.add("North", ExpulsionBt);
		pan23.add("Center",SendBt);
		
		pan2.add("North", pan21);
		pan2.add("Center", pan22);
		pan2.add("South", pan23);
		
		add("East",pan2);
		add("Center",pan1);
	}
}