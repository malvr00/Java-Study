package paint1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Paint1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SPaint1 frame = new SPaint1("Paint ����");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,300);
		frame.setVisible(true);
	}

}


class SPaint1 extends JFrame{
	private int cnt = 0;
	public SPaint1() {}
	public SPaint1(String str) {
		super(str);
		addMouseListener(new MouseClick());
	}
	
	public void paint(Graphics g) {
		super.paint(g);				// ���� paint ȣ��(ȭ�� ����)
		int x,y;
		cnt++;
		x = (int)(Math.random()*300);
		y = (int)(Math.random()*300);
		g.drawLine(0, 0, x, y);
		g.drawString("�ȳ��ϼ���" + x + " " + y, 100, 50);
		System.out.println("paint�����" + cnt);		// ����� test
	}
	
	class MouseClick extends MouseAdapter {
		public void mousePressed (MouseEvent arg0) {
			if(arg0.getButton()==MouseEvent.BUTTON1) {
				repaint();
			}
			else if(arg0.getButton()==MouseEvent.BUTTON3) {
				Graphics gra = getGraphics();
				gra.drawString("������ ��ư ����", 100, 80);
			}
		}
	}
}