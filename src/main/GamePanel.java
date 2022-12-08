package main;

import inputs.Keyboardinputs;
import inputs.Mouseinputs;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private Mouseinputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private float xDir=1f, yDir=1f;
    private int frames =0;
    private long lastCheck =0;
    private Color color= new Color(55,77,99);
    private Random random;

    public GamePanel() {

        random=new Random();
        mouseInputs = new Mouseinputs(this);
        addKeyListener(new Keyboardinputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void changeXDelta(int value) {
        this.xDelta += value;

    }

    public void changeYDelta(int value) {
        this.yDelta += value;

    }

    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateRectangle();

        g.setColor(color);
        g.fillRect((int)xDelta,(int)yDelta,50,100);

    }
    private void updateRectangle() {
        xDelta += xDir;
        if ( xDelta>400 || xDelta<0) {
            xDir *= -1;
            color = getRndColor();
        }
        yDelta += yDir;
        if (yDelta>400 || yDelta<0)
            yDir*=-1;
        color= getRndColor();

    }
    private Color getRndColor() {
        int r= random.nextInt(255);
        int g=random.nextInt(255);
        int b=random.nextInt(255);
        return new Color(r,g,b);
}
}

