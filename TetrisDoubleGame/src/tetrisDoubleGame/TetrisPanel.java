package tetrisDoubleGame;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class TetrisPanel extends JPanel{
	Thread gameThread;
	BufferedImage off;		// ����ȭ��
	Graphics offg;			// ����ȭ�� Graphics Context
	
	Random r;				// Block ��� Random ���� �����ο�
	int [][] map;			// Tetris game map
	Color[] colorType;		// 7 ���� Block
	
	int blockType;			// Block ��� ����
	int [] blockX;			// Block ���� 'X'��ġ ����
	int [] blockY;			// Block ���� 'Y'��ġ ����	
	int blockpos;			// Block ȸ�� ��� ����
	int score;				// Tetris ����
	int delayTime;			// Block �������� �ӵ� ����
	boolean bGame;			// Game ���࿩��
	JLabel strScore;		// ����
	
	public TetrisPanel() {}
	public TetrisPanel(Color [] c) {
		this.colorType = c;
		setLayout(null);
		strScore = new JLabel("			0");
		strScore.setBounds(10,320,100,56);
		
		strScore.setHorizontalAlignment(JLabel.RIGHT);		// �۾� ���������� ����
		add(strScore);
	}	// ������ end
	
	public void initForm() {
		off = new BufferedImage(15*12+1, 15*21+1, BufferedImage.TYPE_INT_RGB);		// ����ȭ�� ���� �׸�ũ��(15*15)���� (12*21)����
		offg=off.getGraphics();		// ����ȭ�� Graphics Context ���
		
		map = new int[12][21];		// 12*21 map����, Block�� ������ Block(1-7), ������ 0
		blockX = new int[4];		// Block ������ġ ����
		blockY = new int[4];
		r = new Random();
		
		drawMap();
		drawGrid();
		drawTitle();
	}
	
	public void setBlockXY(int type) {
	  //  Block ��� �ʱ� ��ǥ	int[8][2][4]
		int initData[][][] = {{{0,0,0,0},{0,0,0,0}}, {{6,5,6,7},{0,1,1,1}},
							  {{7,5,6,7},{0,1,1,1}}, {{5,5,6,7},{0,1,1,1}},
							  {{5,5,6,6},{0,1,1,2}}, {{6,5,6,5},{0,1,1,2}},
							  {{4,5,6,7},{0,0,0,0}}, {{5,6,5,6},{0,0,1,1}} };
	  // Block ��� ����
		for(int i=0; i<4; i++) {
			blockX[i] = initData[type][0][i];
			blockY[i] = initData[type][1][i];
		}
	} // setBlockXY end
	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(off, 0, 0, this);		// ����ȭ���� ���� ȭ������
	} // paint end
	
  // Game Over(����)�� ȭ�鿡 ��Ÿ���� ����
	public void gameOver() {
		offg.setColor(Color.white);
		offg.fillRect(35,120,110,70);
		offg.setColor(Color.black);
		offg.drawRect(40, 125, 100, 60);
		offg.setColor(Color.red);
		offg.drawString("Game Over !", 56, 150);
		offg.setColor(Color.blue);
		offg.drawString("Score: " + score, 56, 170);
	} // gameOver end
	
 // Game ���� �� ȭ��
	public void drawTitle() {
		offg.setColor(Color.white);
		offg.fillRect(29,120,123,70);
		offg.setColor(Color.black);
		offg.drawRect(31, 125, 120, 60);
		offg.setColor(Color.red);
		offg.drawString("TETRIS", 70, 150);
		offg.setColor(Color.yellow);
		offg.drawString("Press START button!!", 35, 170);
	} // drawTitle End
	
 // Block �׸���
	public void dropBlock() {
		removeBlock();
		
		if(checkDrop()) {
			for(int i=0; i<4; i++) {
				blockY[i] = blockY[i] + 1;
			}
		}else {
			drawBlock();
			nextBlock();
		}
		score++;
		strScore.setText(String.valueOf(score));
	} // dropBlock end
	
 // Block ���� �ϼ� �� �ʱ�ȭ �� Block �ӵ� ����
	public void delLine() {
		boolean delOK;
		for(int row=20; row>=0; row--) {
			delOK=true;
			for(int col=0; col<12; col++) {
				if(map[col][row] == 0)
					delOK = false;
			}
			if(delOK) {
				score += 10;
				strScore.setText(String.valueOf(score));
			  // ���� ���� �� �� �������� �ð� ����.
				if(score<1000) {
					delayTime = 1000 - score;
				}else {
					delayTime = 100;
				}
			  // ���� ������ ����(��) �Ʒ��� ����
				for(int delRow=row; delRow>0; delRow--) {
					for(int delCol=0; delCol<12; delCol++) {
						map[delCol][delRow] = map[delCol][delRow-1];
					}
				}
			  // ���� �� ����
				for(int i=0; i<12; i++) {
					map[0][i] = 0;
				}
				row++; 		// ������ �κ� �˻�����
			}
		}
	} // delLine end
	
 // ���� Block �ο�
	public void nextBlock() {
		delLine();
		blockType = Math.abs(r.nextInt()% 7) + 1;
		blockpos = 0;
		setBlockXY(blockType);
		checkGameOver();
	} //nextBlock end
	
 // Block Start ��ġ�� ���� �ִ��� ������ �˻�
	public void checkGameOver() {
		for(int i=0; i<4; i++) {
		// Block Start ���� ���� ������ Game Over 
			if(map[blockX[i]][blockY[i]] != 0) {
				bGame = false;
				gameOver();
			}
		}
	} // checkGameOver
	
 // Block �����
	public void removeBlock() {
		for(int i=0; i<4; i++) {
			map[blockX[i]][blockY[i]] = 0;
		}
	} // removeBlock end
	
 // Block �׸���
	public void drawBlock() {
		for(int i=0; i<4; i++) {
			map[blockX[i]][blockY[i]] = blockType;
		}
	} // drawBlock end
	
 // map �׸���
	public void drawMap() {
		for(int i=0; i<12; i++) {
			for(int j=0; j<21; j++) {
				offg.setColor(colorType[map[i][j]]);
				offg.fillRect(i*15, j*15, 15, 15);
			}
		}
	} // drawMap end
	
 // ȭ�鿡 ���׸���
	public void drawGrid() {
		offg.setColor(new Color(190,190,190));
		
		for(int i = 0; i<12; i++) {
			for(int j=0; j<21; j++) {
				offg.drawRect(i*15, j*15, 15, 15);
			}
		}
	}	// drawGrid end
	
 // Block�� �Ʒ��� �����ϼ� �ִ��� ����
	public boolean checkDrop() {
		boolean dropOk = true;
		for(int i = 0; i<4; i++) {
			if((blockY[i]+1)<21) {
				if(map[blockX[i]][blockY[i]+1] != 0)
					dropOk = false;
			}else {
				dropOk = false;
			}
		}
		return dropOk;
	} // checkDrop end
	
 // Block ȸ���������� ����
	public boolean checkTurn() {
		boolean turnOk = true;
		for(int i=0; i<4; i++) {
			if((blockX[i]>=0) && (blockX[i]<12)&& (blockY[i]>=0)&& (blockY[i]<21)) {
				if(map[blockX[i]][blockY[i]] != 0) turnOk = false;
			}else {
				turnOk = false;
			}
		}
		return turnOk;
	} // checkTurn end
	
 // Block�� ��,��� ������ �� �ִ��� ����
	public boolean checkMove(int dir) {
		boolean moveOk = true;
		removeBlock();
		
		for(int i=0; i<4; i++) {
			if(((blockX[i]+dir)>=0)&&((blockX[i]+dir)<12)){
				if(map[blockX[i]+dir][blockY[i]] != 0) moveOk = false;
			}else {
				moveOk = false;
			}
		}
		if(!moveOk) drawBlock();
		return moveOk;
	} // checkMove end
	
 // ��, ��, ��, �� Key Event ó��
	public void keyPressed(int keyCode) {
		if(keyCode == KeyEvent.VK_LEFT || keyCode== KeyEvent.VK_A) {
			if(checkMove(-1)) {
				for(int i=0; i<4; i++) {
					blockX[i] = blockX[i]-1;
				}
			}
		}else if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
			if(checkMove(1)) {
				for(int i=0; i<4; i++) {
					blockX[i] = blockX[i] + 1;
				}
			}
		}else if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
			removeBlock();
			score += 2;
			strScore.setText(String.valueOf(score));
			
			if(checkDrop()) {
				for(int i=0; i<4; i++) {
					blockY[i] = blockY[i] + 1;
				}
			}else {
				drawBlock();
			}
		} else if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
			int [] tempX = new int[4];
			int [] tempY = new int[4];
			for(int i=0; i<4; i++) {
				tempX[i] = blockX[i];
				tempY[i] = blockY[i];
			}
			removeBlock();
			turnBlock(blockType);
			if(checkTurn()) {
				if(blockpos<3) {
					blockpos++;
				}else {
					blockpos = 0;
				}
			}else {
			 // ȸ���� �Ұ����ϸ� �� ��ġ
				for(int i=0; i<4; i++) {
					blockX[i] = tempX[i];
					blockY[i] = tempY[i];
					map[blockX[i]][blockY[i]] = blockType;
				}
			}
		}
		drawBlock();
		drawMap();
		drawGrid();
		repaint();
	}
	
 // Block ȸ��
	public void turnBlock(int type) {
	 // Block ȸ�� ��ǥ
		int turnDataX[][][] = 
		{	{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
			{{0,0,0,-1},{-1,1,1,0},{1,0,0,0},{0,-1,-1,1}},
			{{-2,1,0,-1},{0,0,1,-1},{1,0,-1,2},{1,-1,0,0}},
			{{1,1,-1,-1},{-2,-1,1,0},{1,1,-1,-1},{0,-1,1,2}},
			{{1,2,-1,0},{-1,-2,1,0},{1,2,-1,0},{-1,-2,1,0}},
			{{-1,1,0,2},{1,-1,0,-2},{-1,1,0,2},{1,-1,0,-2}},
			{{2,1,0,-1,},{-2,-1,0,1},{2,1,0,-1},{-2,-1,0,1}},
			{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}} };
		int turnDataY[][][] =
		{	{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
			{{0,0,0,1},{0,-1,-1,-1},{0,1,1,1},{0,0,0,-1}},
			{{0,-1,0,1},{0,0,-1,-1},{0,1,2,1},{0,0,-1,-1}},
			{{0,0,1,1},{0,-1,-2,-1},{0,0,1,1},{0,1,0,-1}},
			{{0,-1,0,-1},{0,1,0,1},{0,-1,0,-1},{0,1,0,1}},
			{{0,-1,0,-1},{0,1,0,1},{0,-1,0,-1},{0,1,0,1}},
			{{0,1,2,3},{0,-1,-2,-3},{0,1,2,3},{0,-1,-2,-3}},
			{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}} };
		
		for(int i=0; i<4; i++) {
			blockX[i] += turnDataX[type][blockpos][i];
			blockY[i] += turnDataY[type][blockpos][i];
		}
	} // turnBlock end
	
 // Game ����
	public void gameStop() {
		bGame = false;
	} // gameStop end
	
	public void gameStart() {
		blockType = Math.abs(r.nextInt()% 7) + 1;
		setBlockXY(blockType);
		System.out.println(blockType);
		blockpos=0;
	  // map �ʱ�ȭ
		for(int i=0; i<12; i++) {
			for(int j=0; j<21; j++) {
				map[i][j] = 0;
			}
		}
		drawBlock();
		drawMap();
		drawGrid();
		score = 0;
		delayTime = 1000;
		
		gameThread = new game1();
		gameThread.start();
		bGame = true;
	}
	
 // Game Start
	class game1 extends Thread{
		public void run() {
			while(bGame) {
				try {
					Thread.sleep(delayTime);		// Block �������� �ð�
				}catch(InterruptedException ie) {}
				dropBlock();
				drawBlock();
				drawMap();
				drawGrid();
				repaint();
			}
			gameOver();
		}
	} // game1 Thread class end
} // TetrisPaenl class end
