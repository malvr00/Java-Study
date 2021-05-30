package circledrawing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Circledrawing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OvalFrame frame = new OvalFrame("Mouse Button Click 위치에 사각형, 원 그리기");
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
	  // 마우스 클릭 안했는데 그려지는 원 방지하기 위해
		if(sw != 0) {
			for(int i = 1; i<9; i++) {
				size += 15;
			// x, y 좌표값 계산은 원을 마우스클릭 중앙에 그리기 위해
			// size 15씩 8번 증가되는 원 그림
				gra.drawOval(x-size/2, y-size/2, size, size);			
			}
		}
	  // repaint 자동실행 안되는 문제 발생하여 사용
		else
			super.paint(gra);
		size = 5;
		sw = 0;
	}
	class MouseClick extends MouseAdapter{
		public void mousePressed (MouseEvent e) {
		  // Mouser 좌표 가져옴
			x = e.getX();
			y = e.getY();
			sw = 1;
			repaint();
		}
	}
}