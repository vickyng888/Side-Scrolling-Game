
import java.awt.*;

class Shots {
  private int shotX,shotY;
  private int shotV;
  private Color col;
  public static final int SHOT = 420;

  public Shots(int sx,int sy,int sv) { 
    shotX = sx;
    shotY = sy;
    shotV = sv;
    int rand = Util.randint(0,4);
    col = Level.shotsCols[rand];
  }
  
  public void move(){
    shotX += shotV;
  }
  public int getX(){
    return shotX; 
  }
  public int getY(){
    return shotY; 
  }
  
  public boolean collide(Rectangle rect){
    if(shotX >= rect.getX() && shotX <= rect.getX() + rect.getWidth() 
         && shotY >= rect.getY() && shotY <= rect.getY() + rect.getHeight()){
      return true;
    }
    return false;  
  }
  
  public void draw(Graphics g){
    g.setColor(col);
    g.fillOval(shotX,shotY,8,8);
  }
  
}
