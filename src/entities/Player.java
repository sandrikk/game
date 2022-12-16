package entities;

import utilz.LoadPlayerSave;

import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.CanMoveHere;

public class Player extends Entity {
    private BufferedImage[][] animations; //Deals with switching images for animations.
    private int aniTick, aniIndex, aniSpeed = 15;
    private int playerAction = IDLE;
    private boolean moving = false;
    private boolean left, up, right, down;
    private float playerSpeed= 1.5f;
    private int[][] levelData;


    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
    }


    public void update() {

        updatePos();
        updateImagebox();
        updateAnimationTick();
        setAnimation();

    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) x,(int) y,120,80,null);
        drawImagebox(g);
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
        moving = false;
        if (!left && !right && !up && !down) {
            return;
        }

        float xSpeed = 0, ySpeed = 0;

        if(left && !right) {
            xSpeed = -playerSpeed;

        }else if (right && !left){
            xSpeed = playerSpeed;
        }

        if(up && !down) {
            ySpeed = -playerSpeed;
        }else if(down && !up) {
            ySpeed = playerSpeed;

        }

        if (CanMoveHere(x + xSpeed, y + ySpeed, width, height, levelData)) {
            this.x += xSpeed;
            this.y += ySpeed;
            moving = true;
        }

    }


    private void loadAnimations() {
        InputStream istream = getClass().getResourceAsStream("/characters8.png");

            BufferedImage img = LoadPlayerSave.GetSpriteAtlas(LoadPlayerSave.Player_Atlas);

            animations = new BufferedImage[5][5];
            for (int j = 0; j < animations.length; j++)
                for (int i = 0; i < animations[j].length; i++)
                    animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);

    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
    }



    public void resetDirectionsBooleans() {
        right=false;
        left=false;
        down=false;
        up=false;
    }

    public boolean isLeft() {
        return left;
    }
    public void setLeft(boolean left) {
        this.left = left;
    }
    public boolean isUp() {
        return up;
    }
    public void setUp(boolean up) {
        this.up = up;
    }
    public boolean isRight() {
        return right;
    }
    public void setRight(boolean right) {
        this.right=right;
    }
    public boolean isDown() {
        return down;
    }
    public void setDown(boolean down) {
        this.down = down;
    }
}

