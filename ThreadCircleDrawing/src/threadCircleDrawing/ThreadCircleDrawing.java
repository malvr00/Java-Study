package threadCircleDrawing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ThreadCircleDrawing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OvalFrame frame = new OvalFrame("Mouse Button Click 위치에 Thread 활용하여 반응형 원 그리기");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,600);
		frame.setVisible(true);
	}

}

class OvalFrame extends JFrame{
  // 좌표
	private int x;
	private int y;
	
  // 원 Size
	private int  size = 5;
	
  // Thread 	
	private ThreadOval t;

	public OvalFrame() {}
	public OvalFrame(String str) {
		super(str);
		addMouseListener(new MouseClick());
	}

	public void ThreadStart() {
	  // OvalFrame 객체를 보내서 사용하려 했으나, 객체를 보내서 사용하면
	  // X, Y, SIZE를 공유하는 공유자원 문제로 좌표와 사이즈를 보내서 개별적으로 자원활용
	  // Thread에 getGraphics를 사용할 수 없어 같이 보내줌.
		t = new ThreadOval(x,y,size,getGraphics());
		t.start();
	}
	class MouseClick extends MouseAdapter{
		public void mousePressed (MouseEvent e) {
		  // Mouser 좌표 가져옴
			x = e.getX();
			y = e.getY();
			ThreadStart();
		}
	}
}
class ThreadOval extends Thread{
  // Thread 생성 Counting
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
  // 원그리는 부분 함수분리
	public void DrawOval() {
		try {
			System.out.println("x = " + x +" "+"y = " + y + " " + "Thread = " + ThreadCount);
			for(int i = 1; i<9; i++) {
				size += 15;
				// x, y 좌표값 계산은 원을 마우스클릭 중앙에 그리기 위해
				// size 15씩 8번 증가되는 원 그림
				g.drawOval(x-size/2, y-size/2, size, size);
				Thread.sleep(100);
			}
		}catch(Exception e) {}
	}
}