package threadCircleDrawing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ThreadCircleDrawing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OvalFrame frame = new OvalFrame("Mouse Button Click ��ġ�� Thread Ȱ���Ͽ� ������ �� �׸���");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,600);
		frame.setVisible(true);
	}

}

class OvalFrame extends JFrame{
  // ��ǥ
	private int x;
	private int y;
	
  // �� Size
	private int  size = 5;
	
  // Thread 	
	private ThreadOval t;

	public OvalFrame() {}
	public OvalFrame(String str) {
		super(str);
		addMouseListener(new MouseClick());
	}

	public void ThreadStart() {
	  // OvalFrame ��ü�� ������ ����Ϸ� ������, ��ü�� ������ ����ϸ�
	  // X, Y, SIZE�� �����ϴ� �����ڿ� ������ ��ǥ�� ����� ������ ���������� �ڿ�Ȱ��
	  // Thread�� getGraphics�� ����� �� ���� ���� ������.
		t = new ThreadOval(x,y,size,getGraphics());
		t.start();
	}
	class MouseClick extends MouseAdapter{
		public void mousePressed (MouseEvent e) {
		  // Mouser ��ǥ ������
			x = e.getX();
			y = e.getY();
			ThreadStart();
		}
	}
}
class ThreadOval extends Thread{
  // Thread ���� Counting
	static int ThreadCount;
	private int x, y, size;
	private Graphics g;
	ThreadOval(){}
	ThreadOval(int x, int y, int size, Graphics gra){
		this.x = x;
		this.y = y;
		this.size = size;
		this.g = gra;
	}
	public void run() {
		ThreadCount++;
		DrawOval();
	}
  // ���׸��� �κ� �Լ��и�
	public void DrawOval() {
		try {
			System.out.println("x = " + x +" "+"y = " + y + " " + "Thread = " + ThreadCount);
			for(int i = 1; i<9; i++) {
				size += 15;
				// x, y ��ǥ�� ����� ���� ���콺Ŭ�� �߾ӿ� �׸��� ����
				// size 15�� 8�� �����Ǵ� �� �׸�
				g.drawOval(x-size/2, y-size/2, size, size);
				Thread.sleep(100);
			}
		}catch(Exception e) {}
	}
}