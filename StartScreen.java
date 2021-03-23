import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class StartScreen extends JPanel
{
	public static JPanel    startPanel; // 게임시작 화면
	public static JButton   btnStart; // 게임시작 버튼
	private       ImageIcon baseImage = new ImageIcon("java000.jpg"); // 배경화면

	
	public StartScreen()
	{
		setBackground(Color.white);
		setPreferredSize(new Dimension(900,630));
		setLayout(null);

		startPanel = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.drawImage(baseImage.getImage(),0,0,startPanel);
			}
		};
		
		// 배경화면 삽입
		startPanel.setBounds(0,0,900,630);
		startPanel.setLayout(null);
		add(startPanel);
		
		// 게임시작 버튼구성 (게임시작을 누르면 다음패널로 넘어가는 기능을 가진 버튼입니다)
		btnStart = new JButton("GAME START");
		btnStart.setFont(new Font("Consolas",Font.BOLD,100));
		btnStart.setLayout (null);
		btnStart.setBorderPainted(false);
		btnStart.setContentAreaFilled(false);
		btnStart.setBounds(0,510,900,120);
		btnStart.addActionListener(Primary.buttonL);

		// 버튼 삽입
		startPanel.add(btnStart);
		
	} // StartScreen()
	
} // StartScreen class

