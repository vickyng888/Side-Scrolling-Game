import java.awt.*;

class Enemies {
  private Image [][]enePics;
  private Image []snakePics;
  private int num; 
  private int frames = 0;
  private int delay = 0;
  private int DEATHFR = 7;

  private int x, y;
  private int vx;
  private double vxRev = 7;
  
  private boolean prey = false;
  private boolean hit = false;
  private int objThatHit; 
  
  private int health; // draw in the draw method here
  private boolean alive = true;
  
  private int type;
  public static int FELINE = 3478, SNAKE = 2387;
  public Enemies(int x, int y, int vx, int num){ 
    this.x = x;
    this.y = y;
    this.vx = vx; // vx is always neg for the large cats and positive for the snakes
    this.num = num;
    if(vx < 0){
      health = 80;
      type = FELINE;
    }
    else{
      health = 20; // for snakes
      type = SNAKE;

    }
    enePics = new Image[1][8];  
    enePics[num - 1] = Util.addImages("animals","lion1", "png",enePics[num -1]);
    snakePics = new Image[5];
    snakePics = Util.addImages("animals","snake","png",snakePics);
  }
  public void translate(int dx){
    x = dx;
  }
  public void moveWithPlayer(int pv, int moving){ // use for both large cats and snakes
    if(moving == Cat.RIGHT){
      x = x - pv;
    }          
    if(moving == Cat.LEFT){
      x = x + pv; 
    }
  }
  public void move(){ // only for large cats
    if(prey == true && alive == true){
      if(hit == false){
        x += vx;
      }
      else{
        knockback(vx);
      }
    }
  }            

  public void move(int lBounds, int rBounds){ // only for snakes
    if(alive == true){
      if(hit == false){
        x+=vx;
        if(x < lBounds || x > rBounds){
          vx*=-1;
        }
    }
    else{
      knockback(vx);
    }
  }
  }

  public void lowerHealth(){
    health -= 18;
    if(health <= 0){
      alive = false; 
    }
  }
  
  public void setPrey(boolean sp){
    prey = sp; 
    
  }
  public void knockback(int vxx){  // for both large cats and snakes
    if(vxx > 0){
      vxRev*=-1;
      vxRev+=0.25;
    }
    else{
      vxRev-=0.25;  // gradually slows
    }
    if(vxRev == 0){
      x+=vxRev;
    }
    else{
      hit = false; 
      vxRev = 7;
      if(objThatHit == Level.SHOT){
        lowerHealth(); 
      }
    }
  }
  public Rectangle getRect(int type){
    if(type == FELINE){
      return new Rectangle(x,y,enePics[num-1][0].getWidth(null),enePics[num-1][0].getHeight(null));
    }
    else{
      return new Rectangle(x,y,snakePics[0].getWidth(null),snakePics[0].getHeight(null));
    }
  }
  
  public void gotHit(boolean h, int oth){
    hit = h;
    objThatHit = oth;
  }
  public boolean getAlive(){
   return alive; 
  }
  public void draw(Graphics g){
    g.setColor(Color.RED);
    for(int a=0; a<health; a++){
      for(int b=0; b<8; b++){
        g.drawLine(x + a, (y-15) + b, x + a, (y-15) + b); 
      }    
    }
    
    delay++;
    if(delay % Level.WAIT == 0){
      frames++; 
    }

    if(type == FELINE){
    Image enePic = enePics[num-1][frames/2 % 5];
    if(prey == true){
      if(hit == true){
        g.drawImage(enePics[num-1][6], x, y,null);
      }
      if(hit == false && alive == true){
        g.drawImage(enePic, x, y, null); 
      }
      if(alive == false){
        g.drawImage(enePics[num-1][DEATHFR], x, y+20, null); 
      }
    }
    else{
      g.drawImage(enePics[num-1][5], x, y, null); 
    }
  }
  else{
    Image snakePic = snakePics[frames/2 % 5];
    if(alive == true){
      if(vx < 0){
        g.drawImage(snakePic, x, y, null);
      }
      if(vx > 0){ // when it is moving right;
        int w = snakePic.getWidth(null);
        int h = snakePic.getHeight(null);
        g.drawImage(snakePic, x, y, -w, h, null); // flipping the images in the array
      }
    }
  }
    
  }
  
  public Rectangle getTerritory(int level){
    if(level == 1){
      return new Rectangle(3412,200,684,180); 
    }
    else{
      return new Rectangle(1,1,1,1); // temp
    }
  }
}
