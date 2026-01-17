
import java.awt.*;

class Powerups {
  public static final int SPEED = 456, JUMP = 345; // types of powerups
  private int time = 200; // the number of frames that occur in 4 seconds
  
  private Image milkPic = Util.loadImage("items/milk1.png");
  private Image springPic = Util.loadImage("items/spring1.png");
  private Image powerPic;
  
  private int x;
  private int y;
  private int type; 
  private Cat kitty; 
  
  private boolean acti = false; // activation = false

  public Powerups(int x, int y, int t,Cat kitty){ // constructor 
    this.x = x; 
    this.y = y;
    type = t;
    if(type == SPEED){ // diff pic depending on the type of powerup
      powerPic = milkPic;
    }
    if(type == JUMP){
      powerPic = springPic;

    }
    this.kitty = kitty;
  }
  
  public void activate(boolean act){
    acti = act;
  }

  public void activate(){ 
    if(acti == true){
      time--; // powerup can only be used for a set amount of time (4 secs)
      if(time > 0){ 
      if(type == SPEED){
        kitty.setV(9);
        }
      if(type == JUMP){
        kitty.setJ(-21);

      }
      }
      else{
        returnNorm();
      
      }
      }
  }

  public void returnNorm(){ // making the player return to normal settings
    kitty.setV(6);
    kitty.setJ(-12);
  }

  public void translate(int dx){ 
    x = dx; 
   }

  public void setX(int x){
    this.x = x;
   }

  public Rectangle getRect(){ 
    return new Rectangle(x,y,powerPic.getWidth(null),powerPic.getHeight(null));
  }

  public void draw(Graphics g){ 
    if(acti == false){ // since acti == false when the player has not yet picked up the powerup, the powerup is not drawn on screen when
                       // the acti == true and the player is using the powerup;
      g.drawImage(powerPic,x,y,null); 
    }
    else{ // when acti == true
      if(time > 0){  // display in the upper screen that shows the current powerup the player is using
        g.drawImage(powerPic,650,10,null);
      }
    }
  }

}