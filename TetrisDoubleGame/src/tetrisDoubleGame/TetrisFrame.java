package tetrisDoubleGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TetrisFrame extends JFrame implements ActionListener{
	JButton gameStartBt;			// 시작버튼
	JButton gameStopBt;				// 정지버튼
	JPanel bottomPanel;				// 시작, 정지버튼 Panel
	TetrisPanel leftPanel;			// 1p Tetris main
	TetrisPanel rightPanel;			// 2p Tetris main
	
	Color[] colorType;
	public TetrisFrame() {}
	public TetrisFrame(String title) {
		super(title);
		colorType = new Color[8];		// 7종류 Block 별 색상 설정
		
		setLayout(null);
		
		leftPanel = new TetrisPanel(colorType);		// 1p 화면 객체 생성
		leftPanel.setBounds(10,10,181,370);			// 1p 화면 나타낼 dnlc 크기 지정
		leftPanel.setBackground(Color.CYAN);
		leftPanel.initForm();
		getContentPane().add(leftPanel);
		
		rightPanel = new TetrisPanel(colorType);	// 2p 화면 객체 생성
		rightPanel.setBounds(395, 10, 181, 370);	// 2p 화면 나타낼 dnlc 크기 지정
		rightPanel.setBackground(Color.ORANGE);
		rightPanel.initForm();
		getContentPane().add(rightPanel);
		
		bottomPanel = new JPanel();					// bottom Panel 제작
		bottomPanel.setBounds(10,400,571,36);
		bottomPanel.setBackground(Color.yellow);
		gameStartBt = new JButton("Game Start");	// Start Button
		gameStartBt.addActionListener(this);
		bottomPanel.add(gameStartBt);
		gameStopBt = new JButton("Game Stop");		// Stop Button
		gameStopBt.addActionListener(this);
		bottomPanel.add(gameStopBt);
		getContentPane().add(bottomPanel);
		
		addKeyListener(new MyKeyHandle1()); 		// Game 조종키설정
		
	} // 생성자 End
	
  // Block 7종류 별 색상 설정
	public void setColorType() {					
		colorType[0] = Color.white;
		colorType[1] = Color.cyan;
		colorType[2] = Color.gray;
		colorType[3] = Color.green;
		colorType[4] = Color.magenta;
		colorType[5] = Color.orange;
		colorType[6] = Color.pink;
		colorType[7] = Color.yellow;
	}	// setColorType End
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==gameStartBt) {
			leftPanel.gameStart();
			rightPanel.gameStart();
			this.requestFocus();
		}
		else {
			leftPanel.gameStop();
			rightPanel.gameStop();
		}
	} // Button Event End
	
	class MyKeyHandle1 extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			int keyCode=(int)e.getKeyCode();
		// 2p Key 설정
			if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP) {
				rightPanel.keyPressed(keyCode);
			}
		// 1p Key 설정
			if(keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_W) {
				leftPanel.keyPressed(keyCode);
			}
		}
	}	// MyKeyHandle class end
}  // TetrisFrame class End
