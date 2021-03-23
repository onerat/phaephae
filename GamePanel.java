import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.util.*; 

public  class GamePanel extends JPanel implements KeyListener, Runnable 
{ 
   private int f_width; //screen 사이즈를 위한 변수
   private int f_height; //screen 사이즈를 위한 변수
   private int x, y ;  //character의 위치를 위한 변수
   private int nHp =50 , nScore =0;//Hp와 Score값을 위한 변수
   private boolean KeyLeft = false; //character 위치이동을 위해 설정
   private boolean KeyRight = false; //character 위치이동을 위해 설정
   private int cnt; //각종 타이밍 조절을 위해 무한 루프를 카운터할 변수 
   private Thread th; //스레드
   private Toolkit tk = Toolkit.getDefaultToolkit(); //이미지를 가져오기위해 Toolkit의 getDefaultToolkit을 tk로 선언
   private Image character;  //캐릭터 이미지를 받아들일 이미지 변수
   private Image icecream; //아이스크림 이미지를 받아들일 이미지 변수
   private Image banana; //바나나 이미지를 받아들일 이미지 변수 
   private ArrayList icecream_List = new ArrayList(); //다수의 아이스크림 이미지를 등장 시켜야 하므로 배열을 이용.
   private ArrayList banana_List = new ArrayList(); //다수의 바나나 이미지를 등장 시켜야 하므로 배열을 이용. 
   private Image buffImage; 
   private Graphics buffg;  
   private JFrame frame = new JFrame("피해피해");
   private Item item; //아이템 클래스 접근 키 
  
   public GamePanel() 
   {      
      setBackground(Color.white);
      init(); 
      start(); 
      //setTitle("피해피해"); 
      setSize(f_width, f_height); 
      Dimension screen = tk.getScreenSize(); //screen 사이즈 설정
      int f_xpos = (int) (screen.getWidth() / 2 - f_width / 2); 
      int f_ypos = (int) (screen.getHeight() / 2 - f_height / 2); 
      setLocation(f_xpos, f_ypos);//스크린위치 설정
      //setResizable(false); 
      setVisible(true);
      
   } 
   
   public void init() 
   { 
      x = 300; 
      y = 500; 
      f_width = 900; 
      f_height = 630; 
      character = tk.getImage("java32.jpg");//캐릭터이미지 생성 
      icecream = tk.getImage("java33.jpg");//아이스크림 이미지 생성 
      banana = tk.getImage("java39.jpg");//바나나 이미지 생성 
   } 
   
   public void start() 
   { 
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      addKeyListener(this);  
      th = new Thread(this); //스레드 생성
      th.start();
      setFocusable(true); 
   } 
  
   public void run() 
   { 
      
      try 
      { 
         while (true) //무한루프
         {  
            
            KeyProcess(); //캐릭터의 움직임을 위한 처리 메소드
            ItemProcess(); // 아이템 생성 처리 메소드
         CheckCollision();//아이템과 캐릭터가 충돌시 처리 메소드 
            repaint(); 
            Thread.sleep(10); 
            cnt++;//무한 루프 카운터 
            if (nHp == 0)//Hp값이 0이되면 정지
            wait();
            
            
         } 
      } 
      catch (Exception e) 
      {} 
   } 
   
   public void ItemProcess() 
   {//아이템 처리 메소드 
      for (int i = 0; i < icecream_List.size(); ++i) 
      { 
         item = (Item) (icecream_List.get(i)); //아이스크림
         //배열에 아이템이 생성되어있을 때 해당되는 아이템을 판별 
         item.move(); //해당 아이템을 이동시킨다. 
         if (item.y > 550) 
         { //아이템의 좌표가 화면 밖으로 넘어가면 
            icecream_List.remove(i); // 해당 아이스크림을 배열에서 삭제 
         } 
      } 
      if (cnt % 50 == 0) 
      { //루프 카운트 50회 마다 아이템생성 
         item = new Item((int) (getWidth() * Math.random()), f_height - 600);//가로 구간내에서 랜덤하게 
         icecream_List.add(item); 
      } 

     for (int i = 0; i < banana_List.size(); ++i) //바나나
      { 
         item = (Item) (banana_List.get(i)); 
         //배열에 아이템이 생성되어있을 때 해당되는 아이템을 판별 
         item.move(); //해당 아이템을 이동시킨다. 
         if (item.y > 550) 
         { //아이템의 좌표가 화면 밖으로 넘어가면 
            banana_List.remove(i); // 해당 아이템을 배열에서 삭제 
         } 
      } 
      if (cnt % 25 == 0) 
      { //루프 카운트 25회 마다 적생성 
         item = new Item((int) (getWidth() * Math.random()), f_height - 600);//가로 구간내에서 랜덤하게 
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
      g.drawImage(buffImage, 0, 0, this); //buffimage 그리기
   } 
   public void Draw_Char() 
   { 
      buffg.clearRect(0, 0, f_width, f_height); //f_width,f_height의 크기로 된 사각형그리기//최근 색상으로
      buffg.drawImage(character, x, y, this); //character 그리기
   } 
  
   public void Draw_Item() 
   { // 아이템 이미지를 그리는 부분 
      for (int i = 0; i < icecream_List.size(); ++i) 
      { 
         item = (Item) (icecream_List.get(i)); 
         buffg.drawImage(icecream, item.x, item.y, this); 
         //배열에 생성된 각 아이스크림을 판별하여 이미지 그리기 
      } 

     for (int i = 0; i < banana_List.size(); ++i) 
      { 
         item = (Item) (banana_List.get(i)); 
         buffg.drawImage(banana, item.x, item.y, this); 
         //배열에 생성된 각 바나나을 판별하여 이미지 그리기 
      } 
   } 

     public void Draw_Hpbar() {
      buffg.setFont(new Font("Consolas",Font.BOLD,15));
      buffg.setColor(Color.black);
      buffg.drawString("HP : " + nHp+"/50",280,63);//hp 값을 나타내기 위한 스트링
      buffg.setFont(new Font("Consolas",Font.BOLD,15));
      buffg.setColor(Color.black);
      buffg.drawString("SCORE : " + nScore,700,63);//score 값을 나타내기 위한 스트링
        buffg.setColor(Color.lightGray); // hpbar의 바깥부분
        buffg.fillRect(15,50, 250, 20); 
        buffg.setColor(Color.red);//실제로 hp를 나타내는 부분
        buffg.fillRect(15,50, nHp*5, 16); 
    }



   public void CheckCollision() {
      for (int i = 0; i < icecream_List.size(); ++i) {//아이스크림과 근접
         item = (Item) (icecream_List.get(i));
      if (item!= null) {
       if (x+30 > item.x && x-30 < item.x && y+30 > item.y && y-30 < item.y) { // 지정된 부분(캐릭터 이미지 좌표 근접)에 아이템 이미지가 들어가는 경우
          nScore+=10;//점수 10점 증가
          if(nHp<50){ //hp가 50이 최고이기 때문에 50보다 작은 경우만 5 씩 증가
          nHp+=5;}
          icecream_List.remove(i);//이미지가 캐릭터와 닿은 경우 이미지리스트에서 삭제
}}}
      for (int i = 0; i < banana_List.size(); ++i) {//바나나와 근접
         item = (Item) (banana_List.get(i));
      if (item!= null) {
       if (x+30 > item.x && x-30 < item.x && y+30 > item.y && y-30 < item.y) { // 지정된 부분(캐릭터 이미지 좌표 근접)에 아이템 이미지가 들어가는 경우
          nHp-=10;//hp 10씩 감소
          banana_List.remove(i);//이미지가 캐릭터와 닿은 경우 이미지리스트에서 삭제
}}}
}

   public void keyPressed(KeyEvent e) //keypressed 일때 boolean 값 설정
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
   public void keyReleased(KeyEvent e) //keyreleased 일때 boolean 값 설정
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
  public void keyTyped(KeyEvent e)  //keytyped 일때 keyprocess()메소드 
   { 
   } 
  public void KeyProcess() 
   { 
      if (KeyLeft == true) 
      {  //x좌표 -7만큼 이동시킴
         x -= 7; 
      } 
      if (KeyRight == true) 
      { //x좌표 +7만큼 이동시킴
         x += 7; 
      } 
   } 
} 

 class Item 
{ // 아이템 위치 파악 및 이동을 위한 클래스 
   int x; 
   int y; 
   Item(int x, int y) 
   { // 아이템좌표를 받아 객체화 시키기 위한 메소드 
      this.x = x; 
      this.y = y; 
   } 
   public void move() 
   { // y좌표 +3 만큼 이동 시키는 명령 메소드 
      y += 5; 
   } 
}