import java.awt.*;
import javax.swing.*;
import java.awt.event.*; 

public class Primary extends JPanel
{
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("피해피해");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Primary primary = new Primary();
        
	    frame.getContentPane().add(primary);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	private StartScreen  startPanel; //첫 번째 화면(START SCREEN)
    private GamePanel    gamePanel; // 두 번째 화면(GAME)
 
	public static ButtonListener buttonL;
	
	public Primary()
	{
		
	// 기본 Primary 의 크기와 Layout 을 조정하기위한 null 값 지정
	setBackground(Color.white);
	setPreferredSize(new Dimension(900,630));
	setLayout(null);
	//setFocusable(true);
	
	
	// ButtonListener 선언후 생성
	buttonL = new ButtonListener();
	
	//startPanel 생성후 크기지정후 add, 맨 첫화면이기에 setVisible(true) 선언
	startPanel = new StartScreen();
	startPanel.setBounds(0,0,900,630);
	add(startPanel);
	startPanel.setVisible(true);
    
  
    // gamePanel 생성후 크기지정후 add, 두 번째 화면이기에 setVisible(false)
    gamePanel = new GamePanel();
    gamePanel.setBounds(0,0,900,630);
    gamePanel.setVisible(false);
    add(gamePanel);  
  

    
    } // Primary()
    
    //*****************************************************************
    // Represents the listener
   //*****************************************************************
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			Object obj = event.getSource();
			
			// StartScreen Panel에서 btnStart 에 대한 ActionListener
	
			if(obj == StartScreen.btnStart)
			{
				startPanel.setVisible(false);
				gamePanel.setVisible(true);
				gamePanel.requestFocus(); // keyEvent 받을 컴포넌트gamePanel로 강제 설정
			}
			
		
		} //actionPerformed()

	} // ButtonListener class
	
} //Primary class

