import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import javax.imageio.*;
import java.awt.image.*; 
import java.io.*; 

class Util{
  private static Font font;
  private static BufferedImage buffPic;
  public static int randint(int a, int b){
    return (int)(Math.random()*(b-a+1) + a);
  }
  public static boolean clear(int x, int y, BufferedImage mask){ // change back to clear 
    int WALL = 0xFF0000FF;
    int WALL2 = 0x000000FF;
    if(x<0 || x>= mask.getWidth(null) || y<0 || y>= mask.getHeight(null)){ // out of bounds lmfao
      return false;
    }
    int c = mask.getRGB(x, y);
    return (c != WALL && c != WALL2);
  }
  
  public static Image loadImage(String str){
    return  new ImageIcon(str).getImage();
  } 
  
  
  public static boolean onCol(Rectangle rect, int col, BufferedImage mask){
    return mask.getRGB((int)rect.getX(), (int)(rect.getY()+ rect.getHeight()) - 2) == col || mask.getRGB((int)(rect.getX() + rect.getWidth()), (int)(rect.getY()+ rect.getHeight()) - 2) == col;
  }
  
  public static void drawImage(Graphics g, Image img, Rectangle rect){
    if(rect != null){
      g.drawImage(img,(int)rect.getX(),(int)rect.getY(),null);
    }
  }
  public static Image[] addImages(String file, String name, String ext, Image[] pics){
    for(int i=0; i<pics.length; i++){
      Image pic = loadImage(file + "/" + name + (i+1) + "." + ext);
      pics[i] = pic;
    }
    return pics;
  }
  
  public static BufferedImage[] addImages(String file, String name, BufferedImage[] pics){
    for(int i=0; i<pics.length; i++){
      BufferedImage pic = loadBuffImage(file + "/" + name + (i+1) + ".png");
      pics[i] = pic;
    }
    return pics;
  }

  public static void fill(Graphics g, Rectangle rect, Color col){
    g.setColor(col);
    g.fillRect((int)rect.getX(),(int)rect.getY(),(int)rect.getWidth(),(int)rect.getHeight());
  }
  
  public static void moveWithScr(Rectangle rect,int offset){
   Rectangle rect2 = new Rectangle(rect);
   rect2.translate(offset,0);
  }
  public static void drawWithScr(Graphics g, Rectangle rect, Image img, int offset){
    Rectangle r2 = new Rectangle(rect);
   r2.translate(offset,0);
   Util.drawImage(g,img,r2);
    
  }
  public static void drawWithScr(Graphics g,ArrayList<Rectangle> rects,int offset, Image img){
    for(Rectangle r : rects){
      Rectangle r2 = new Rectangle(r);
      r2.translate(offset,0);
      Util.drawImage(g,img,r2);
    }
  }
   public static void drawWithScr(Graphics g, Circle circ, int offset){
    Circle c2 = new Circle(circ);
    c2.translate(offset);
    c2.draw(g);
    
  }
  public static Font makeFont(float size ){
    try {
      font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/pixelFont.ttf")).deriveFont(size);
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(font);
    }
    catch (IOException e){}
    catch (FontFormatException f){}
    return font;
 
  }
  
  public static BufferedImage loadBuffImage(String str){
     try{
      buffPic = ImageIO.read(new File(str));
    } 
    catch (IOException e){
      System.out.println(e);
    }
    return buffPic;
  }
  public static boolean unsafeGround(int x, int y, BufferedImage mask){
    int UNSAFE = 0xFF000000; 
    if(x<0 || x>= mask.getWidth(null) || y<0 || y>= mask.getHeight(null)){
      return false;
    }
    int c = mask.getRGB(x, y);
    return c == UNSAFE;
  }
  
}
