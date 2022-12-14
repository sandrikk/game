package entities;

import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.PlayerConstants.*;

public class Player extends Entity {
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 15;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false;


    public Player(float x, float y) {
        super(x, y);
        loadAnimations();

    }


    public void update() {
        updateAnimationTick();
        setAnimation();
        updatePos();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) x,(int) y,120,80,null);
    }
    public void setDirection(int direction) {
        this.playerDir = direction;
        moving = true;
    }
    public void setMoving(boolean moving) {
        this.moving = moving;
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
    public void setAnimation() {
        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
    }
    private void updatePos() {
        if (moving) {
            switch (playerDir) {
                case LEFT:
                    x -= 5;
                    break;
                case UP:
                    y -= 5;
                    break;
                case RIGHT:
                    x += 5;
                    break;
                case DOWN:
                    y += 5;
                    break;
            }
        }
    }



    private void loadAnimations() {
        InputStream istream = getClass().getResourceAsStream("/characters8.png");
        try {
            BufferedImage img = ImageIO.read(istream);

            animations = new BufferedImage[5][5];
            for (int j = 0; j < animations.length; j++)
                for (int i = 0; i < animations[j].length; i++)
                    animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                istream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}

