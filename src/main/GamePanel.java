package main;

import inputs.Keyboardinputs;
import inputs.Mouseinputs;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private Mouseinputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private float xDir=1f, yDir=1f;
    private int frames =0;
    private long lastCheck =0;

    private BufferedImage img;


    public GamePanel() {
        mouseInputs = new Mouseinputs(this);
        importImg();
        setPanelSize();
        addKeyListener(new Keyboardinputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void importImg() {
            InputStream istream = getClass().getResourceAsStream("/character1.png");
            try {
                img = ImageIO.read(istream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    private void setPanelSize() {
        Dimension size = new Dimension(1280,800);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);

    }

    public void changeXDelta(int value) {
        this.xDelta += value;

    }

    public void changeYDelta(int value) {
        this.yDelta += value;

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(img,(int) xDelta,(int) yDelta,120,200,null);


    }

    public void setRectPos(int x,int y) {
        this.xDelta = x;
        this.yDelta = y;
    }





}

