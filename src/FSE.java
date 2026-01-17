import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import javax.imageio.*;
import java.awt.image.*; 
import java.io.*; 

class FSE extends BaseFrame{
 private Font font;
 private Image []bgPics;
 private BufferedImage []mPics;
 private Image []yPics;
 
 private int levelNum;
 private Level level;
 
 public FSE(){
  super("FSE", 800,600);
  
  bgPics = new Image[3];
  bgPics = Util.addImages("backgrounds","bg","jpg",bgPics);

  yPics = new Image[3];
  Image yPic = Util.loadImage("items/yarn1.png");
  for(int i=0; i<3; i++){
   yPics[i] = yPic;
  }
  levelNum = 1;
  level = new Level(levelNum);
  level.getImages(bgPics,yPics);
 } 
 
 public void move(){
  if(level == null) return;
   if(level.reset()){
    level = new Level(levelNum); 
    level.getImages(bgPics,yPics);
    
   }
   if(level.complete()){
     levelNum+=1;
     level.setReset(true);
   }
   level.move(keys);
 }

 @Override
 public void draw(Graphics g){
  Graphics2D g2d = (Graphics2D)g; // graphics object
  if(level == null) return;
  level.draw(g2d);
  }
  
 public static void main(String[] args) {
  new FSE();
  
    } 
}