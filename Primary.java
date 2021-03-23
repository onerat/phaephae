import java.awt.*;
import javax.swing.*;
import java.awt.event.*; 

public class Primary extends JPanel
{
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("��������");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Primary primary = new Primary();
        
	    frame.getContentPane().add(primary);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	private StartScreen  startPanel; //ù ��° ȭ��(START SCREEN)
    private GamePanel    gamePanel; // �� ��° ȭ��(GAME)
 
	public static ButtonListener buttonL;
	
	public Primary()
	{
		
	// �⺻ Primary �� ũ��� Layout �� �����ϱ����� null �� ����
	setBackground(Color.white);
	setPreferredSize(new Dimension(900,630));
	setLayout(null);
	//setFocusable(true);
	
	
	// ButtonListener ������ ����
	buttonL = new ButtonListener();
	
	//startPanel ������ ũ�������� add, �� ùȭ���̱⿡ setVisible(true) ����
	startPanel = new StartScreen();
	startPanel.setBounds(0,0,900,630);
	add(startPanel);
	startPanel.setVisible(true);
    
  
    // gamePanel ������ ũ�������� add, �� ��° ȭ���̱⿡ setVisible(false)
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
			
			// StartScreen Panel���� btnStart �� ���� ActionListener
	
			if(obj == StartScreen.btnStart)
			{
				startPanel.setVisible(false);
				gamePanel.setVisible(true);
				gamePanel.requestFocus(); // keyEvent ���� ������ƮgamePanel�� ���� ����
			}
			
		
		} //actionPerformed()

	} // ButtonListener class
	
} //Primary class

