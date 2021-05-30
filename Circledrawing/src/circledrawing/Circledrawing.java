package circledrawing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Circledrawing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OvalFrame frame = new OvalFrame("Mouse Button Click ��ġ�� �簢��, �� �׸���");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,600);
		frame.setVisible(true);
	}

}

class OvalFrame extends JFrame{
	private int x;
	private int y;
	private int  size = 5;
	private int sw = 0;
	public OvalFrame() {}
	public OvalFrame(String str) {
		super(str);
		addMouseListener(new MouseClick());
	}
	
	public void paint(Graphics gra) {
	  // ���콺 Ŭ�� ���ߴµ� �׷����� �� �����ϱ� ����
		if(sw != 0) {
			for(int i = 1; i<9; i++) {
				size += 15;
			// x, y ��ǥ�� ����� ���� ���콺Ŭ�� �߾ӿ� �׸��� ����
			// size 15�� 8�� �����Ǵ� �� �׸�
				gra.drawOval(x-size/2, y-size/2, size, size);			
			}
		}
	  // repaint �ڵ����� �ȵǴ� ���� �߻��Ͽ� ���
		else
			super.paint(gra);
		size = 5;
		sw = 0;
	}
	class MouseClick extends MouseAdapter{
		public void mousePressed (MouseEvent e) {
		  // Mouser ��ǥ ������
			x = e.getX();
			y = e.getY();
			sw = 1;
			repaint();
		}
	}
}