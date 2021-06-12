package tetrisDoubleGame;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class TetrisPanel extends JPanel{
	Thread gameThread;
	BufferedImage off;		// 가성화면
	Graphics offg;			// 가상화면 Graphics Context
	
	Random r;				// Block 모양 Random 패턴 랜덤부여
	int [][] map;			// Tetris game map
	Color[] colorType;		// 7 종류 Block
	
	int blockType;			// Block 모양 저장
	int [] blockX;			// Block 현재 'X'위치 저장
	int [] blockY;			// Block 현재 'Y'위치 저장	
	int blockpos;			// Block 회전 모양 지정
	int score;				// Tetris 점수
	int delayTime;			// Block 내려오는 속도 저장
	boolean bGame;			// Game 진행여부
	JLabel strScore;		// 점수
	
	public TetrisPanel() {}
	public TetrisPanel(Color [] c) {
		this.colorType = c;
		setLayout(null);
		strScore = new JLabel("			0");
		strScore.setBounds(10,320,100,56);
		
		strScore.setHorizontalAlignment(JLabel.RIGHT);		// 글씨 오른쪽으로 정렬
		add(strScore);
	}	// 생성자 end
	
	public void initForm() {
		off = new BufferedImage(15*12+1, 15*21+1, BufferedImage.TYPE_INT_RGB);		// 가상화면 생성 그림크기(15*15)개수 (12*21)개수
		offg=off.getGraphics();		// 가상화면 Graphics Context 얻기
		
		map = new int[12][21];		// 12*21 map생성, Block이 있으면 Block(1-7), 없으면 0
		blockX = new int[4];		// Block 현재위치 저장
		blockY = new int[4];
		r = new Random();
		
		drawMap();
		drawGrid();
		drawTitle();
	}
	
	public void setBlockXY(int type) {
	  //  Block 모양 초기 좌표	int[8][2][4]
		int initData[][][] = {{{0,0,0,0},{0,0,0,0}}, {{6,5,6,7},{0,1,1,1}},
							  {{7,5,6,7},{0,1,1,1}}, {{5,5,6,7},{0,1,1,1}},
							  {{5,5,6,6},{0,1,1,2}}, {{6,5,6,5},{0,1,1,2}},
							  {{4,5,6,7},{0,0,0,0}}, {{5,6,5,6},{0,0,1,1}} };
	  // Block 모양 저장
		for(int i=0; i<4; i++) {
			blockX[i] = initData[type][0][i];
			blockY[i] = initData[type][1][i];
		}
	} // setBlockXY end
	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(off, 0, 0, this);		// 가상화면을 실제 화면으로
	} // paint end
	
  // Game Over(종료)시 화면에 나타나는 문구
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
	
 // Game 시작 전 화면
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
	
 // Block 그리기
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
	
 // Block 한줄 완성 시 초기화 및 Block 속도 조절
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
			  // 점수 증가 시 블럭 내려가는 시간 감소.
				if(score<1000) {
					delayTime = 1000 - score;
				}else {
					delayTime = 100;
				}
			  // 위에 축적된 정보(블럭) 아래로 내림
				for(int delRow=row; delRow>0; delRow--) {
					for(int delCol=0; delCol<12; delCol++) {
						map[delCol][delRow] = map[delCol][delRow-1];
					}
				}
			  // 꽉찬 행 지움
				for(int i=0; i<12; i++) {
					map[0][i] = 0;
				}
				row++; 		// 내려온 부분 검사위해
			}
		}
	} // delLine end
	
 // 다음 Block 부여
	public void nextBlock() {
		delLine();
		blockType = Math.abs(r.nextInt()% 7) + 1;
		blockpos = 0;
		setBlockXY(blockType);
		checkGameOver();
	} //nextBlock end
	
 // Block Start 위치에 블럭이 있는지 없는지 검사
	public void checkGameOver() {
		for(int i=0; i<4; i++) {
		// Block Start 윛에 블럭이 있으면 Game Over 
			if(map[blockX[i]][blockY[i]] != 0) {
				bGame = false;
				gameOver();
			}
		}
	} // checkGameOver
	
 // Block 지우기
	public void removeBlock() {
		for(int i=0; i<4; i++) {
			map[blockX[i]][blockY[i]] = 0;
		}
	} // removeBlock end
	
 // Block 그리기
	public void drawBlock() {
		for(int i=0; i<4; i++) {
			map[blockX[i]][blockY[i]] = blockType;
		}
	} // drawBlock end
	
 // map 그리기
	public void drawMap() {
		for(int i=0; i<12; i++) {
			for(int j=0; j<21; j++) {
				offg.setColor(colorType[map[i][j]]);
				offg.fillRect(i*15, j*15, 15, 15);
			}
		}
	} // drawMap end
	
 // 화면에 선그리기
	public void drawGrid() {
		offg.setColor(new Color(190,190,190));
		
		for(int i = 0; i<12; i++) {
			for(int j=0; j<21; j++) {
				offg.drawRect(i*15, j*15, 15, 15);
			}
		}
	}	// drawGrid end
	
 // Block이 아래로 움직일수 있는지 결정
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
	
 // Block 회전가능한지 결정
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
	
 // Block이 좌,우로 움직일 수 있는지 결정
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
	
 // 상, 하, 좌, 우 Key Event 처리
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
			 // 회전이 불가능하면 원 위치
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
	
 // Block 회전
	public void turnBlock(int type) {
	 // Block 회전 좌표
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
	
 // Game 종료
	public void gameStop() {
		bGame = false;
	} // gameStop end
	
	public void gameStart() {
		blockType = Math.abs(r.nextInt()% 7) + 1;
		setBlockXY(blockType);
		System.out.println(blockType);
		blockpos=0;
	  // map 초기화
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
					Thread.sleep(delayTime);		// Block 떨어지는 시간
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
