import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import javax.imageio.*;
import java.awt.image.*; 
import java.io.*; 

// diff class for the screens?

class Level{
  // *** - same for every level therefore likly found in "buildGenLevel()" method
  // no notes - same variable for every level but diff values for each level 
  // level # - variable that only appears for that level num
  
  // --- BACKGROUNDS --- //
  private int bgNum;
  private Image []biomePics; 
  private BufferedImage []maskPics;
  
  // --- ALWAYS DISPLAY ON SCREEN --- //
  private Image heartPic;
  private Image yarnOutline;
  private Font font1 = Util.makeFont(20f);
//  private Font font1;
  // --- COLOURS --- // ***
  public static Color TEAL = new Color(104,237,215), 
    BROWN = new Color(133,86,32), 
    GREY = new Color(140,130,130),
    LBLACK = new Color(35,35,35),
    LBROWN = new Color(200,150,75),
    LGREY = new Color(190,185,200);
  
  // --- PLAYER --- // ***
  private Cat kitty; 
  private int health; 
  private boolean alive; 
  
  // --- GAME SETTINGS --- // ***
  private boolean reset = false;
  private boolean complete = false;
  
  // --- TEMP --- //
  private Rectangle temp;
  // --- UNMOVING OBSTACLES --- // player immediately dies upon contact
  // player immediately dies upon contact
  // water blocks
  private ArrayList<Rectangle> waterObs = new ArrayList<Rectangle>();
  private Image []water1;
  private Image []water2;
  
  // lava blocks
  private ArrayList<Rectangle> lavaObs = new ArrayList<Rectangle>();
  private Image []lava1;
  private Image []lava2;
  
  // lowers health of player
  // spike blocks
  private ArrayList<Rectangle> spikesObs = new ArrayList<Rectangle>(); 
  
  // --- PATH --- //
  private ArrayList<Rectangle> climbRects = new ArrayList<Rectangle>(); 
  
  // --- WEAPONRY --- //
  private Image slingPic; // lev 1
  private Rectangle slingRect; // lev 1
  
  private boolean holdingWeap = false; // ***
  
  private ArrayList<Shots> shots = new ArrayList<Shots>(); // stores the shot that is about to be shot (only ever stores a singular shot)  ***
  private boolean noShot; // confirms that the above array is storing no shots so that a shot is allowed to be added ***
  private int shotsStored; // keeps track of the number of shots the player collected therefore actually has (display this num on screen) ***
  public static Color []shotsCols = {LBLACK,GREY,LGREY,BROWN,LBROWN}; // diff colors of the pebbles (which is what is being shot) ***
  
  private ArrayList<Circle> collectShots = new ArrayList<Circle>(); // array of the shots (circle objects as they are unmoving) that are on the map and have not been collected
  private ArrayList<Color> shotColors = new ArrayList<Color>();
  private ArrayList<Integer> shotXList = new ArrayList<Integer>(); // x vals of each of the circs in the above array 
  
  // --- ITEMS TO PICK UP --- //
  private Image []yarnPics; // ***
  private ArrayList<Rectangle> yarnRects = new ArrayList<Rectangle>(); 
  private int yarns; // ***
  
  private ArrayList<Powerups> powerups = new ArrayList<Powerups>();
  private ArrayList<Rectangle> powerRects = new ArrayList<Rectangle>();
  // ---- ENEMIES ---- //
  private Enemies enemy;
  private int ex;
  
  // --- ANIMATION ---- // ***
  private int frames = 0;
  private int delay = 0;
  public static int WAIT = 5;
  
  // --- OTHER --- //
  private Image flagPic; // ***
  private Rectangle flagRect; // initialize in the level method
  
  // ---- CONSTANTS BELONGING TO CLASS--- // 
  public static int CAT = 69, SHOT = 420;
  
  private int n; // level num
  public Level(int nn) { 
    maskPics = new BufferedImage[3];
    maskPics = Util.addImages("backgrounds","bgMask",maskPics);
    n = nn;
    if(n == 1){
      buildLevelOne();
    }
    if(n == 2){
      buildLevelTwo();
    }
    
     if(n == 3){
     buildLevelThree(); 
     }
     
  }
  
  
  public void getImages(Image []bgPics, Image []yPics){  // getting images from main
    biomePics = bgPics;
    yarnPics = yPics;
    yarnRects.add(new Rectangle(500,125,yarnPics[0].getWidth(null), yarnPics[0].getHeight(null)));
    yarnRects.add(new Rectangle(1780,50,yarnPics[0].getWidth(null), yarnPics[0].getHeight(null)));
    yarnRects.add(new Rectangle(3210,170,yarnPics[0].getWidth(null), yarnPics[0].getHeight(null)));    
  }
  
  public void buildGenLev(){ // level variables that are the same for each level
    // ---- BACKGROUND ----- //
    bgNum = n;

    // --- ALWAYS DISPLAY ON SCREEN --- //
    heartPic = Util.loadImage("items/heart1.png");
    yarnOutline = Util.loadImage("items/yarnOutline.png");
    
    // --- PLAYER --- //
    kitty = new Cat(130,200,false,maskPics[bgNum-1]);
    health = 150;      
    alive = true;
    
    // --- ITEMS TO COLLECT --- //
    yarns = 3;
    
    // --- WEAPONRY --- //
    noShot = true;
    shotsStored = 0;
    
    // --- OTHER ---- //
    flagPic = Util.loadImage("path/flag2.png");
  }
  
  public void buildLevelOne(){
    buildGenLev();
    // --- BACKGROUNDS --- //
    bgNum = 1;
    
    
    // --- UNMOVING OBSTACLES --- //
    water1 = new Image[5];
    water1 = Util.addImages("obstacles","water1","jpg",water1);
    water2 = new Image[5];
    water2 = Util.addImages("obstacles","water2","jpg",water2);
    
    waterObs.add(new Rectangle(960,510,1,1));
    waterObs.add(new Rectangle(2955,530,1,1));
    
    lava1 = new Image[5];
    lava1 = Util.addImages("obstacles","lava1","jpg",lava1);
    lava2 = new Image[5];
    lava2 = Util.addImages("obstacles","lava2","jpg",lava2);
    
    lavaObs.add(new Rectangle(560,510,1,1));
    lavaObs.add(new Rectangle(2600,530,1,1));

    spikesObs.add(new Rectangle(1230,61,55,55));
    spikesObs.add(new Rectangle(2300,385,55,55));
    spikesObs.add(new Rectangle(2916,385,70,75));
    
    // --- PATH --- // 
    climbRects.add(new Rectangle(410,200,30,195));
    climbRects.add(new Rectangle(1020,215,60,125));
    climbRects.add(new Rectangle(1945,70,40,75));
    climbRects.add(new Rectangle(3035,240,45,200));
    
    // --- ENEMY --- //
    enemy = new Enemies(3935,320,-8,1); 
    ex = 3935;
    // ---- ITEMS TO COLLECT ---- //
    powerups.add(new Powerups(1689,233,Powerups.SPEED,kitty));
    powerRects.add(powerups.get(0).getRect());

    // --- WEAPONRY --- //
    slingPic = Util.loadImage("items/slingshot1.png");
    // slingRect = new Rectangle(3496, 346, slingPic.getWidth(null), slingPic.getHeight(null));
    
    collectShots.add(new Circle(423,273,8));
    shotXList.add(423);
    collectShots.add(new Circle(694,135,8));
    shotXList.add(694);
    collectShots.add(new Circle(996,331,8));
    shotXList.add(996);
    collectShots.add(new Circle(1349,105,8));
    shotXList.add(1349);
    collectShots.add(new Circle(1703,248,8));
    shotXList.add(1703);
    collectShots.add(new Circle(1969,111,8));
    shotXList.add(1969);
    collectShots.add(new Circle(2483,349,8));
    shotXList.add(2483);
    collectShots.add(new Circle(2910,426,8));
    shotXList.add(2917);
    collectShots.add(new Circle(3067,284,8));
    shotXList.add(3067);
    collectShots.add(new Circle(3289,304,8));
    shotXList.add(3289);
    
  }
  public void buildLevelTwo(){
    buildGenLev();
    // --- BACKGROUNDS --- //
    bgNum = 2;
    
    
    // --- UNMOVING OBSTACLES --- //
    //water1 = new Image[5];
    //water1 = Util.addImages("obstacles","water1","jpg",water1);
   // water2 = new Image[5];
   // water2 = Util.addImages("obstacles","water2","jpg",water2);
    
   // waterObs.add(new Rectangle(960,510,1,1));
    //waterObs.add(new Rectangle(2955,530,1,1));
    
    lava1 = new Image[5];
    lava1 = Util.addImages("obstacles","lava3","jpg",lava1);
    lava2 = new Image[5];
    lava2 = Util.addImages("obstacles","lava4","jpg",lava2);
    
    lavaObs.add(new Rectangle(436,480,1,1));
    lavaObs.add(new Rectangle(3114,420,1,1));

    spikesObs.add(new Rectangle(1565,310,50,60));
    spikesObs.add(new Rectangle(2435,310,80,80));
    spikesObs.add(new Rectangle(3880,235,50,60));
    
     // --- PATH --- // 
    climbRects.add(new Rectangle(1829,103,54,109));
    climbRects.add(new Rectangle(2625,103,53,281));
    climbRects.add(new Rectangle(3227,142,53,168));
    
    // --- ENEMY --- //
    //enemy = new Enemies(3935,320,-8,1); 
    //ex = 3935;
    // ---- ITEMS TO COLLECT ---- //
    powerups.add(new Powerups(900,220,Powerups.JUMP,kitty));
    powerRects.add(powerups.get(0).getRect());

    // --- WEAPONRY --- //
   // slingPic = Util.loadImage("items/slingshot1.png");
    // slingRect = new Rectangle(3496, 346, slingPic.getWidth(null), slingPic.getHeight(null));
    
  
    collectShots.add(new Circle(374,359,8));
    shotXList.add(374);
    collectShots.add(new Circle(683,264,8));
    shotXList.add(683);
    collectShots.add(new Circle(878,236,8));
    shotXList.add(878);
    collectShots.add(new Circle(1475,350,8));
    shotXList.add(1475);
    collectShots.add(new Circle(2354,359,8));
    shotXList.add(2354);
    collectShots.add(new Circle(2749,87,8));
    shotXList.add(2749);
    collectShots.add(new Circle(2723,453,8));
    shotXList.add(2723);
    collectShots.add(new Circle(3359,111,8));
    shotXList.add(3359);
    collectShots.add(new Circle(3320,106,8));
    shotXList.add(3320);
    collectShots.add(new Circle(3487,273,8));
    shotXList.add(3289);
   
    
  }
  
  public void buildLevelThree(){

    
  }
  
  // <----------------------- MOVE ELEMENTS OF GAME ----------------------------->
  
  // GENERAL MOVE FUNCTIONS THAT ARE NEEDED FOR ALL LEVELS
  public void climb(Rectangle catRect, int offset){
    int i = 0;
    for(Rectangle c : climbRects){
      Rectangle c2 = new Rectangle(c);
      c2.translate(offset,0);
      if(catRect.intersects(c2)){
        kitty.setLad(true);
      }
      else{
        i++;
      }
    }
    if(i == climbRects.size()){
      kitty.setLad(false);
      
    }
  }
 
  public void spikes(Rectangle catRect, int offset){
    for(Rectangle s : spikesObs){
      Rectangle s2 = new Rectangle(s);
      s2.translate(offset,0);
      if(catRect.intersects(s2)){
        health-=4;
      }
    }

  }

  public void shoot(boolean[] keys, int ky){
    if(keys[KeyEvent.VK_E] && noShot && shotsStored > 0 && holdingWeap == true){ // requirements for the player to be able to shoot 
      shots.add(new Shots(180,ky,13));
    }
    if(shots.size() != 0){ // only one shot at a time
      noShot = false; 
    }
    else{
      noShot = true; 
    }
  }

  public void addShots(Rectangle catRect, int offset){
    int d = 0;
    for(Circle shot : collectShots){
      Circle shot2 = new Circle(shot);
      shot2.translate(offset + shotXList.get(d));
      if(shot2.intersects(catRect)){
        collectShots.remove(d);
        shotXList.remove(d);
        shotsStored++;
        break;
      }
      d++;
    }
  }
  
  public void usePowerups(Rectangle catRect, int offset){
    int f = 0;
    for(Rectangle p : powerRects){
      Rectangle p2 = new Rectangle(p);
      Powerups pow = powerups.get(f);
      p2.translate(offset,0);
      if(catRect.intersects(p2)){
        powerRects.remove(f);
        pow.activate(true);
        break;
        }
        powerups.get(f).setX((int)p2.getX());
      f++;
      }
  }

// MOVING ELEMENTS IN EACH LEVEL
  public void moveLevelOne(boolean[] keys){
    
    if(kitty == null) return;
    kitty.move(keys);
    
    int kx = kitty.getX();
    int ky = kitty.getY();
    int kv = kitty.getV();
    Rectangle catRect = kitty.getRect();
    int offset = 130 - kx;
  
    
    if(holdingWeap == false){
      slingRect = new Rectangle(3496, 346, slingPic.getWidth(null), slingPic.getHeight(null));
      Rectangle s2 = new Rectangle(slingRect);
      s2.translate(offset,0);
      if(catRect.intersects(s2)){
        holdingWeap = true;  
      }
      slingRect = new Rectangle(s2);
    }
    
    addShots(catRect, offset);

    Rectangle enTerr = enemy.getTerritory(1);  // the block in which the enemy moves
    Rectangle enTerr2 = new Rectangle(enTerr);
    enTerr2.translate(offset,0);
    if(catRect.intersects(enTerr2)){
      enemy.setPrey(true);
      enemy.moveWithPlayer(kv,kitty.isMoving());
    }
    else{
      enemy.setPrey(false);
      enemy.translate(ex + offset);
    }
    
    enemy.move();
    Rectangle eneRect = enemy.getRect();
    boolean eneAlive = enemy.getAlive();
    if(catRect.intersects(eneRect) && eneAlive == true){ // where the player gets hurt by the enemy
      health-=15;
      enemy.gotHit(true,CAT);
    }
    
    shoot(keys,ky);
 
    for(Shots shot : shots){
      shot.move();
      if(shot.collide(eneRect) && eneAlive == true){ // temp (keep the shot.getX() > 800)
        enemy.gotHit(true,SHOT);
        shots.remove(shot);
        shotsStored -= 1;
        break;
      }
      else if(shot.getX() > 800){
        shots.remove(shot);
        shotsStored -= 1;
        break;
      }
    }
    
    climb(catRect,offset);

    spikes(catRect,offset);

    int j = 0;
    for(Rectangle y : yarnRects){
      Rectangle y2 = new Rectangle(y);
      y2.translate(offset,0);
      if(catRect.intersects(y2)){
        yarns -= 1;
        yarnRects.remove(j);
        break;
      }
      j++;
    }
    
    usePowerups(catRect,offset);
    for(Powerups pow : powerups){
      pow.activate();
    }
    
    flagRect = new Rectangle(4030,330,flagPic.getWidth(null),flagPic.getHeight(null));
    Rectangle f2 = new Rectangle(flagRect);
    f2.translate(offset,0); 
    if(catRect.intersects(f2) && yarns == 0){
      complete = true; // temp
    }
    else if(catRect.intersects(f2) && yarns > 0){
      reset = true; 
    }
     
    alive = kitty.getAlive();
    checkAlive(alive,health);
  }
  
  
  public void moveLevelTwo(boolean[] keys){
    kitty.move(keys);
     
    int kx = kitty.getX();
    int ky = kitty.getY();
    int kv = kitty.getV();
    Rectangle catRect = kitty.getRect(); 
    int offset = 130 - kx;
    
    addShots(catRect, offset);
    shoot(keys,ky);
 
    usePowerups(catRect,offset);
    for(Powerups pow : powerups){
      pow.activate();
    }

    climb(catRect,offset);
    spikes(catRect,offset);

    alive = kitty.getAlive();
    checkAlive(alive,health);

  }

  public void moveLevelThree(boolean []keys){
    
  }
  
    
  public void move(boolean []keys){
    if(n == 1){
      moveLevelOne(keys);
    }
    if(n == 2){
     moveLevelTwo(keys); 
    }
    if(n == 3){
      moveLevelThree(keys);
    }
  }
  
  // -------------------------- GAME SETTINGS ------------------------>  
  
  public boolean reset(){
    return reset;
  }
  
  public void setReset(boolean r){
    reset = r; 
  } 
  
  public boolean complete(){ // gives whether this particular level for this level obj is completed
    return complete; 
  }
  
  public void checkAlive(boolean alive, int health){
    if(health <= 0){

      alive = false;
    }
    if(alive == false){
        reset = true;
    }
    
  }
  // <-------------------------- DRAW GAME -----------------------------> 
  
  // DRAW FUNCTIONS THAT EVERY LEVEL NEEDS

  public void displayStat(Graphics g){
    g.setColor(TEAL);
    for(int a=0; a<health; a++){
      for(int b=0; b<20; b++){
        g.drawLine(35 + a, 20 + b, 35 + a, 20 + b); 
      }    
    }
    g.drawImage(heartPic,12,15,null);

    g.setFont(font1);
    g.setColor(Color.WHITE);
    g.drawString("# of Shots: " + shotsStored,370,40);
    g.drawString("||      Power-up: ",520,40);

  }

  public void shotsToCollect(Graphics g, int offset){
    int d = 0;
    g.setColor(GREY);
    for(Circle shot : collectShots){
      Circle shot2 = new Circle(shot);
      shot2.translate(offset + shotXList.get(d));
      shot2.draw(g);
      d++;
    }
  }

  public void drawLevelOne(Graphics g){
    int kx = kitty.getX();
    int ky = kitty.getY();
    int kv = kitty.getV();
    Rectangle catRect = kitty.getRect();
    int offset = 130 - kx;

    g.drawImage(biomePics[bgNum-1], offset, 0, null); // background

    displayStat(g);

    flagRect = new Rectangle(4030,330,flagPic.getWidth(null),flagPic.getHeight(null));
    Rectangle f2 = new Rectangle(flagRect);
    f2.translate(offset,0);
    Util.drawImage(g,flagPic,f2);
    
    delay++;
    if(delay % WAIT == 0){
      frames++;
    }
    Image water1Pic = water1[frames/2 % 5];
    Image water2Pic = water2[frames/2 % 5];
    Image lava1Pic = lava1[frames/2 % 5];
    Image lava2Pic = lava2[frames/2 % 5];
    
    Rectangle w2 = new Rectangle(waterObs.get(0));
    w2.translate(offset,0);
    g.drawImage(water1Pic,(int)w2.getX(),(int)w2.getY(),null);
    
    Rectangle w22 = new Rectangle(waterObs.get(1));
    w22.translate(offset,0);
    g.drawImage(water2Pic,(int)w22.getX(),(int)w22.getY(),null);
    
    Rectangle l2 = new Rectangle(lavaObs.get(0));
    l2.translate(offset,0);
    Util.drawImage(g,lava1Pic,l2);
    
    Rectangle l22 = new Rectangle(lavaObs.get(1));
    l22.translate(offset,0);
    Util.drawImage(g,lava2Pic,l22);
   
  
    for(int q=0; q<yarns; q++){
      Rectangle y2 = new Rectangle(yarnRects.get(q));
      y2.translate(offset,0);
      Util.drawImage(g,yarnPics[q],y2);
    }
    for(int u=1;u<4;u++){
      g.drawImage(yarnOutline,170+(45*u),20,null);
    }
    for(int r=1; r<(3-yarns) + 1; r++){
      g.drawImage(yarnPics[0],170+(45*r),20,null); 
    }
    
    for(Shots shot : shots){
      shot.draw(g); 
    }
    
    if(holdingWeap == false){
      slingRect = new Rectangle(3496, 346, slingPic.getWidth(null), slingPic.getHeight(null));
      Util.drawWithScr(g,slingRect,slingPic,offset);
    }
    else{
      g.drawImage(slingPic, 150, ky, null); 
      
    }
    
    shotsToCollect(g,offset);

    for(Powerups p : powerups){
      p.draw(g);
    }

    kitty.draw(g);
    
    enemy.draw(g);
    
  }
  
  
  public void drawLevelTwo(Graphics g){
    int kx = kitty.getX();
    int ky = kitty.getY();
    int kv = kitty.getV();
    Rectangle catRect = kitty.getRect();
    
    int offset = 130 - kx;
    g.drawImage(biomePics[bgNum-1], offset, 0, null); // background
    displayStat(g);
    
    delay++;
    if(delay % WAIT == 0){
      frames++;
    }

    Image lava1Pic = lava1[frames/2 % 5];
    Image lava2Pic = lava2[frames/2 % 5];
    
    Rectangle l2 = new Rectangle(lavaObs.get(0));
    l2.translate(offset,0);
    Util.drawImage(g,lava1Pic,l2);
    
    Rectangle l22 = new Rectangle(lavaObs.get(1));
    l22.translate(offset,0);
    Util.drawImage(g,lava2Pic,l22);
  
    shotsToCollect(g,offset);

    for(Powerups p : powerups){
      p.draw(g);
    }

    kitty.draw(g);
  }
  public void drawLevelThree(Graphics g){


  }
  
  public void draw(Graphics g){
    if(n == 1){
      drawLevelOne(g); 
    }
    if(n== 2){
      drawLevelTwo(g);
    }
    if(n == 3){
      drawLevelThree(g);
    }
    }
  
  
  
  public static void main(String[] args) { 
    new FSE();
  }
}
