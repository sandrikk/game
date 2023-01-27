package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float imagebox;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        //initImagebox();
    }

    protected void drawImagebox(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);

        // red rectangle
        //g.drawRect((int)imagebox.x - xLvlOffset, (int)imagebox.y, (int)imagebox.width, (int)imagebox.height);
    }

    protected void initImagebox(float x, float y, float width, float height) {
        imagebox = new Rectangle2D.Float( x,y,width,height);
    }

    /*
    public void updateImagebox() {
        imagebox.x = (int) x;
        imagebox.y = (int) y;
    }

     */

    public Rectangle2D.Float getImagebox() {
        return imagebox;
    }
}
