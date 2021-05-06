package chattingFrame;

import javax.swing.*;
import java.awt.*;

public class ChattingFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChatFrame chatForm = new ChatFrame("Java Chatting");
		chatForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chatForm.initForm();
		chatForm.setVisible(true);
	}

}

class ChatFrame extends JFrame{
	public ChatFrame() {}
	public ChatFrame(String str) {
		super(str);
	}
}