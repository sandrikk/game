package main;

import inputs.Keyboardinputs;
import inputs.Mouseinputs;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

public class GamePanel extends JPanel {
    private Mouseinputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private float xDir=1f, yDir=1f;
    private int frames =0;
    private long lastCheck =0;

    private BufferedImage img;
    private long lastTime = new Date().getTime()/1000;

    private BufferedImage[][] animations;

    private int aniTick, aniIndex, aniSpeed = 15;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false;
    private int count = 300;
private Game game;

    public GamePanel(Game game) {
        mouseInputs = new Mouseinputs(this);
        this.game=game;
        importImg();
        loadAnimations();
        setPanelSize();
        addKeyListener(new Keyboardinputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void loadAnimations() {
        animations = new BufferedImage[5][5];

        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
            }

        }
    }

    private void importImg () {
        InputStream istream = getClass().getResourceAsStream("/characters8.png");
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

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
            }
        }

    }

    public void updateGame() {


    }
 //   public void paintComponent(Graphics g) {
 //       super.paintComponent(g);
//
 //       updateAnimationTick();

  //      g.drawImage(animations[aniIndex], (int) xDelta,(int) yDelta,120,80,null);


    public void setAnimation() {
        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
    }

    public void setRectPos(int x,int y) {
        this.xDelta = x;
        this.yDelta = y;
    }

    public void setDirection(int direction) {
        this.playerDir = direction;
        moving = true;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private void updatePos() {
        if (moving) {
            switch (playerDir) {
                case LEFT:
                    xDelta -= 5;
                    break;
                case UP:
                    yDelta -= 5;
                    break;
                case RIGHT:
                    xDelta += 5;
                    break;
                case DOWN:
                    yDelta += 5;
                    break;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        game.render(g);


     //   g.drawImage(animations[playerAction][aniIndex], (int) xDelta,(int) yDelta,64,40,null);

        long currentTime = new Date().getTime()/1000;
        g.setFont(new  Font("TimesRoman",Font.PLAIN,40));
        if (currentTime - lastTime >= 1){
            count--;
            this.lastTime = new Date().getTime()/1000;
        }
        g.drawString(String.valueOf(count), 1200, 50);

        updateAnimationTick();
        setAnimation();
        updatePos();

    }
public Game getGame() {
        return game;
}

}

