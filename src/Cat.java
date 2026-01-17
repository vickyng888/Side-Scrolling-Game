import java.awt.event.*;
import java.awt.*;
import java.awt.image.*; 

class Cat{
  private Image []catPics1;
  private Image cp; // current cat frame
  private int frame = 0;
  
  private int catX, catY;
  private int cv = 6;
  private int vy;
  private int moving;
    
  private BufferedImage bgMask;
  private static int BLUE = 0xFF0000FF;
  
  private boolean onGround;
  private boolean onLadder = false;
  private int GRAV = 1, JUMP = -13;

  // CONSTANTS
  private int dir = RIGHT; // direction the cat is facing
  public static int RIGHT=1, LEFT=2, UP=3; // up is for vines and ladders
  
  
  private boolean alive = true;
  
  public Cat(int xx, int yy, boolean OG, BufferedImage mask){
    catPics1 = new Image[6];
    Util.addImages("animals","cat1","png",catPics1);
    catX = xx;
    catY = yy;
    bgMask = mask;
    onGround = OG;
    
  }
  
  
  
  public void move(boolean []keys){
    
    if(keys[KeyEvent.VK_RIGHT] && catX < 4096){
      dir = RIGHT;
      moving = RIGHT; 
      move();
    }
    else if(keys[KeyEvent.VK_LEFT] && catX > 130){ // && Util.clear(catX + 47 + cv, catY, platMask)
      dir = LEFT;
      moving = LEFT; 
      move();
    }
    else{
     moving = 0; 
    }
    if(keys[KeyEvent.VK_SPACE] && onGround == true ){ // prob would need to include clear here somehow 
      vy = JUMP;
      onGround = false;
    } 
    
    catY += vy;
    vy += GRAV;
    if(onLadder == true){
      onGround = true; // same effect as it was on the ground -> gravity ends up not affecting cat 
      vy = 0;
      dir = UP;
      if(keys[KeyEvent.VK_UP]){
        catY -= 4;
        frame++;
      }
      if(keys[KeyEvent.VK_DOWN]){
        catY += 4; 
        frame++;
      }
    }
    if(onGround()){  
      onGround = true; 
    }
    else{
      onGround = false; 
    }
    if(onGround == true){
      vy = 0; // no effect on catY when cat is on the ground
    }   
    
  }
  public int isMoving(){
   return moving; 
    
  }
  public int getV(){
    return cv;
    
  }
  public void move(){
    if(dir == RIGHT){
      if(inBounds()){
        if(Util.clear(catX + 35 + cv, catY, bgMask)){
          frame++;
          catX += cv;
        }
      }
      else{
        frame++;
        catX += cv;
      }
    }
    if(dir == LEFT){
      if(inBounds()){
        if(Util.clear(catX - cv, catY, bgMask)){
          frame++;
          catX -= cv;
        }
      }
      else{
        frame++;
        catX -= cv;
      }
    }
  }
  
  public boolean onGround(){
    if(inBounds()){
      if(bgMask.getRGB(catX, catY + 32) == BLUE || bgMask.getRGB(catX + 35, catY + 32) == BLUE){
        return true; 
      }
    }
    
    return false;
  }
  
  public void setLad(boolean lad){
    onLadder = lad; 
  }
  
  public void setOG(boolean og){
    onGround = og; 
  }
  
  public void setJ(int j){
    JUMP = j;
  }
  
  public void setV(int vx){
    cv = vx;
  }
  
  public int getX(){
    return catX; 
  }
  public int getY(){
    return catY; 
  }
  
  public Rectangle getRect(){
    return new Rectangle(130,catY,35,35); 
  }
  
  public boolean inBounds(){
    if(catY >= 0 && catY + 35 <=600){
      return true; 
    }
    return false;
  }
  
  public boolean getAlive(){
    if(inBounds()){
      if(Util.unsafeGround(catX, catY + 18, bgMask)){ // pu tthis somewhere else
        alive = false; 
      }
    }
    return alive; 
  }
  
  public void draw(Graphics g){
    // maybe include a red hue every time the cat gets hit (low priority lol) 
    int cn = catPics1.length/2;
    if(dir == RIGHT || dir == LEFT){
      cp = catPics1[frame/2 % cn]; // cycling thru the array
    }
    else{
      cp = catPics1[3 + (frame/2 % cn)]; // cycling thru the array
    }
    if(dir == RIGHT || dir == UP){
      g.drawImage(cp, 130, catY, null);
    }
    else{
      int w = cp.getWidth(null);
      int h = cp.getHeight(null);
      g.drawImage(cp, 130 + w, catY, -w, h, null); // flipping the images in the array
    }
  }
  
  public static void main(String[] args) {
    new FSE();
  }   
}
