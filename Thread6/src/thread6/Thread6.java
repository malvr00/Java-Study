package thread6;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Thread6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread61 td1 = new Thread61();
		td1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		td1.setSize(640, 480);
		td1.setVisible(true);
		Thread trd1 = new Thread(td1, "tName1");
		Thread trd2 = new Thread(td1, "tName2");
		trd1.start();
		trd2.start();
	}

}


class Thread61 extends JFrame implements Runnable{
	private Random r;
	private int x, y, cnt;   // 둘이 차이점 확인.
	//static int x, y, cnt;
	public Thread61() {
		super("원 그리기");
		r = new Random();
		cnt = 0;
	}
	public void ppp(String tName) {
		Graphics g = getGraphics();
		if(tName.equals("tName1")) {
			g.setColor(Color.BLACK);
			g.fillOval(x,y,20,20);
		}else {
			g.setColor(new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)));
			g.fillRect(x,y,20,20);
		}
		g.drawString(String.valueOf(cnt), x+22, y+22);
		System.out.println(tName + "위치 : " + x + ", " + y);
	}
	//public synchronized void run() {			// 차이점 확인
		public void run() {
			Rectangle rec;
			for(int i = 0; i<30; i++) {
				rec = getBounds();		// 화면 크기 구하기
				x = r.nextInt(rec.width);
				y = r.nextInt(rec.height);
				cnt = i;
				ppp(Thread.currentThread().getName());
				try {
					Thread.sleep(100);
				}catch(Exception e) {}
			}
		}
	//}
}