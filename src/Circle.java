import java.awt.*;

class Circle { 
    private int x, y;
    private int r;
    private Color col;
    
    public Circle(int x, int y, int r) { 
        this.x = x;
        this.y = y; 
        this.r = r;
        int rand = Util.randint(0,4);
        col = Level.shotsCols[rand];
   }

    public Circle(Circle circ){
        int []info = circ.getInfo();
        x = info[0];
        y = info[1];
        r = info[2];
    }

    public int[] getInfo(){
        int []info = new int[3];
        info[0] = x;
        info[1] = y;
        info[2] = r; 
        return info;
    }

    public int getRadius() { 
        return r;
    }

    public boolean intersects(Rectangle rect){
        if(x >=  rect.getX() && x <= rect.getX() + rect.getWidth() && y >= rect.getY() && y <= rect.getY() + rect.getHeight()){
        return true; 
        }
        return false; 
    }
    
    public void translate(int dx){
        x = dx; 
    }

    public void draw(Graphics g){
        g.setColor(col);
        g.fillOval(x,y,r,r);
    }
    
    }