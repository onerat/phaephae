import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class StartScreen extends JPanel
{
	public static JPanel    startPanel; // ���ӽ��� ȭ��
	public static JButton   btnStart; // ���ӽ��� ��ư
	private       ImageIcon baseImage = new ImageIcon("java000.jpg"); // ���ȭ��

	
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
		
		// ���ȭ�� ����
		startPanel.setBounds(0,0,900,630);
		startPanel.setLayout(null);
		add(startPanel);
		
		// ���ӽ��� ��ư���� (���ӽ����� ������ �����гη� �Ѿ�� ����� ���� ��ư�Դϴ�)
		btnStart = new JButton("GAME START");
		btnStart.setFont(new Font("Consolas",Font.BOLD,100));
		btnStart.setLayout (null);
		btnStart.setBorderPainted(false);
		btnStart.setContentAreaFilled(false);
		btnStart.setBounds(0,510,900,120);
		btnStart.addActionListener(Primary.buttonL);

		// ��ư ����
		startPanel.add(btnStart);
		
	} // StartScreen()
	
} // StartScreen class

