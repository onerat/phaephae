import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.util.*; 

public  class GamePanel extends JPanel implements KeyListener, Runnable 
{ 
   private int f_width; //screen ����� ���� ����
   private int f_height; //screen ����� ���� ����
   private int x, y ;  //character�� ��ġ�� ���� ����
   private int nHp =50 , nScore =0;//Hp�� Score���� ���� ����
   private boolean KeyLeft = false; //character ��ġ�̵��� ���� ����
   private boolean KeyRight = false; //character ��ġ�̵��� ���� ����
   private int cnt; //���� Ÿ�̹� ������ ���� ���� ������ ī������ ���� 
   private Thread th; //������
   private Toolkit tk = Toolkit.getDefaultToolkit(); //�̹����� ������������ Toolkit�� getDefaultToolkit�� tk�� ����
   private Image character;  //ĳ���� �̹����� �޾Ƶ��� �̹��� ����
   private Image icecream; //���̽�ũ�� �̹����� �޾Ƶ��� �̹��� ����
   private Image banana; //�ٳ��� �̹����� �޾Ƶ��� �̹��� ���� 
   private ArrayList icecream_List = new ArrayList(); //�ټ��� ���̽�ũ�� �̹����� ���� ���Ѿ� �ϹǷ� �迭�� �̿�.
   private ArrayList banana_List = new ArrayList(); //�ټ��� �ٳ��� �̹����� ���� ���Ѿ� �ϹǷ� �迭�� �̿�. 
   private Image buffImage; 
   private Graphics buffg;  
   private JFrame frame = new JFrame("��������");
   private Item item; //������ Ŭ���� ���� Ű 
  
   public GamePanel() 
   {      
      setBackground(Color.white);
      init(); 
      start(); 
      //setTitle("��������"); 
      setSize(f_width, f_height); 
      Dimension screen = tk.getScreenSize(); //screen ������ ����
      int f_xpos = (int) (screen.getWidth() / 2 - f_width / 2); 
      int f_ypos = (int) (screen.getHeight() / 2 - f_height / 2); 
      setLocation(f_xpos, f_ypos);//��ũ����ġ ����
      //setResizable(false); 
      setVisible(true);
      
   } 
   
   public void init() 
   { 
      x = 300; 
      y = 500; 
      f_width = 900; 
      f_height = 630; 
      character = tk.getImage("java32.jpg");//ĳ�����̹��� ���� 
      icecream = tk.getImage("java33.jpg");//���̽�ũ�� �̹��� ���� 
      banana = tk.getImage("java39.jpg");//�ٳ��� �̹��� ���� 
   } 
   
   public void start() 
   { 
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      addKeyListener(this);  
      th = new Thread(this); //������ ����
      th.start();
      setFocusable(true); 
   } 
  
   public void run() 
   { 
      
      try 
      { 
         while (true) //���ѷ���
         {  
            
            KeyProcess(); //ĳ������ �������� ���� ó�� �޼ҵ�
            ItemProcess(); // ������ ���� ó�� �޼ҵ�
         CheckCollision();//�����۰� ĳ���Ͱ� �浹�� ó�� �޼ҵ� 
            repaint(); 
            Thread.sleep(10); 
            cnt++;//���� ���� ī���� 
            if (nHp == 0)//Hp���� 0�̵Ǹ� ����
            wait();
            
            
         } 
      } 
      catch (Exception e) 
      {} 
   } 
   
   public void ItemProcess() 
   {//������ ó�� �޼ҵ� 
      for (int i = 0; i < icecream_List.size(); ++i) 
      { 
         item = (Item) (icecream_List.get(i)); //���̽�ũ��
         //�迭�� �������� �����Ǿ����� �� �ش�Ǵ� �������� �Ǻ� 
         item.move(); //�ش� �������� �̵���Ų��. 
         if (item.y > 550) 
         { //�������� ��ǥ�� ȭ�� ������ �Ѿ�� 
            icecream_List.remove(i); // �ش� ���̽�ũ���� �迭���� ���� 
         } 
      } 
      if (cnt % 50 == 0) 
      { //���� ī��Ʈ 50ȸ ���� �����ۻ��� 
         item = new Item((int) (getWidth() * Math.random()), f_height - 600);//���� ���������� �����ϰ� 
         icecream_List.add(item); 
      } 

     for (int i = 0; i < banana_List.size(); ++i) //�ٳ���
      { 
         item = (Item) (banana_List.get(i)); 
         //�迭�� �������� �����Ǿ����� �� �ش�Ǵ� �������� �Ǻ� 
         item.move(); //�ش� �������� �̵���Ų��. 
         if (item.y > 550) 
         { //�������� ��ǥ�� ȭ�� ������ �Ѿ�� 
            banana_List.remove(i); // �ش� �������� �迭���� ���� 
         } 
      } 
      if (cnt % 25 == 0) 
      { //���� ī��Ʈ 25ȸ ���� ������ 
         item = new Item((int) (getWidth() * Math.random()), f_height - 600);//���� ���������� �����ϰ� 
         banana_List.add(item); 
      } 
   } 

   public void paint(Graphics g) 
   { 
      buffImage = createImage(f_width, f_height); 
      buffg = buffImage.getGraphics(); 
      update(g); 
   } 
   public void update(Graphics g) 
   { 
      Draw_Char(); 
      Draw_Item(); 
      Draw_Hpbar();
      g.drawImage(buffImage, 0, 0, this); //buffimage �׸���
   } 
   public void Draw_Char() 
   { 
      buffg.clearRect(0, 0, f_width, f_height); //f_width,f_height�� ũ��� �� �簢���׸���//�ֱ� ��������
      buffg.drawImage(character, x, y, this); //character �׸���
   } 
  
   public void Draw_Item() 
   { // ������ �̹����� �׸��� �κ� 
      for (int i = 0; i < icecream_List.size(); ++i) 
      { 
         item = (Item) (icecream_List.get(i)); 
         buffg.drawImage(icecream, item.x, item.y, this); 
         //�迭�� ������ �� ���̽�ũ���� �Ǻ��Ͽ� �̹��� �׸��� 
      } 

     for (int i = 0; i < banana_List.size(); ++i) 
      { 
         item = (Item) (banana_List.get(i)); 
         buffg.drawImage(banana, item.x, item.y, this); 
         //�迭�� ������ �� �ٳ����� �Ǻ��Ͽ� �̹��� �׸��� 
      } 
   } 

     public void Draw_Hpbar() {
      buffg.setFont(new Font("Consolas",Font.BOLD,15));
      buffg.setColor(Color.black);
      buffg.drawString("HP : " + nHp+"/50",280,63);//hp ���� ��Ÿ���� ���� ��Ʈ��
      buffg.setFont(new Font("Consolas",Font.BOLD,15));
      buffg.setColor(Color.black);
      buffg.drawString("SCORE : " + nScore,700,63);//score ���� ��Ÿ���� ���� ��Ʈ��
        buffg.setColor(Color.lightGray); // hpbar�� �ٱ��κ�
        buffg.fillRect(15,50, 250, 20); 
        buffg.setColor(Color.red);//������ hp�� ��Ÿ���� �κ�
        buffg.fillRect(15,50, nHp*5, 16); 
    }



   public void CheckCollision() {
      for (int i = 0; i < icecream_List.size(); ++i) {//���̽�ũ���� ����
         item = (Item) (icecream_List.get(i));
      if (item!= null) {
       if (x+30 > item.x && x-30 < item.x && y+30 > item.y && y-30 < item.y) { // ������ �κ�(ĳ���� �̹��� ��ǥ ����)�� ������ �̹����� ���� ���
          nScore+=10;//���� 10�� ����
          if(nHp<50){ //hp�� 50�� �ְ��̱� ������ 50���� ���� ��츸 5 �� ����
          nHp+=5;}
          icecream_List.remove(i);//�̹����� ĳ���Ϳ� ���� ��� �̹�������Ʈ���� ����
}}}
      for (int i = 0; i < banana_List.size(); ++i) {//�ٳ����� ����
         item = (Item) (banana_List.get(i));
      if (item!= null) {
       if (x+30 > item.x && x-30 < item.x && y+30 > item.y && y-30 < item.y) { // ������ �κ�(ĳ���� �̹��� ��ǥ ����)�� ������ �̹����� ���� ���
          nHp-=10;//hp 10�� ����
          banana_List.remove(i);//�̹����� ĳ���Ϳ� ���� ��� �̹�������Ʈ���� ����
}}}
}

   public void keyPressed(KeyEvent e) //keypressed �϶� boolean �� ����
   { 
      switch (e.getKeyCode()) 
      { 
         case KeyEvent.VK_LEFT: 
         KeyLeft = true; 
         break; 
         case KeyEvent.VK_RIGHT: 
         KeyRight = true; 
         break; 

      } 
   } 
   public void keyReleased(KeyEvent e) //keyreleased �϶� boolean �� ����
   { 
      switch (e.getKeyCode()) 
      { 
         case KeyEvent.VK_LEFT: 
         KeyLeft = false; 
         break; 
         case KeyEvent.VK_RIGHT: 
         KeyRight = false; 
         break;  
 
      } 
   } 
  public void keyTyped(KeyEvent e)  //keytyped �϶� keyprocess()�޼ҵ� 
   { 
   } 
  public void KeyProcess() 
   { 
      if (KeyLeft == true) 
      {  //x��ǥ -7��ŭ �̵���Ŵ
         x -= 7; 
      } 
      if (KeyRight == true) 
      { //x��ǥ +7��ŭ �̵���Ŵ
         x += 7; 
      } 
   } 
} 

 class Item 
{ // ������ ��ġ �ľ� �� �̵��� ���� Ŭ���� 
   int x; 
   int y; 
   Item(int x, int y) 
   { // ��������ǥ�� �޾� ��üȭ ��Ű�� ���� �޼ҵ� 
      this.x = x; 
      this.y = y; 
   } 
   public void move() 
   { // y��ǥ +3 ��ŭ �̵� ��Ű�� ��� �޼ҵ� 
      y += 5; 
   } 
}