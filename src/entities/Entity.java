package entities;

import java.awt.*;

public abstract class Entity {

    protected float x, y;
    protected int width, height;
    protected Rectangle imagebox;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        initImagebox();
    }

    protected void drawImagebox(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(imagebox.x, imagebox.y, imagebox.width, imagebox.height);
    }

    private void initImagebox() {
        imagebox = new Rectangle((int) x,(int) y,width,height);
    }

    public void updateImagebox() {
        imagebox.x = (int) x;
        imagebox.y = (int) y;
    }

    public Rectangle getImagebox() {
        return imagebox;
    }
}
