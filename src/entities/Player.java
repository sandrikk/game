package entities;

import main.Game;
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
import static utilz.HelpMethods.*;

public class Player extends Entity {
    private BufferedImage[][] animations; //Deals with switching images for animations.
    private int aniTick, aniIndex, aniSpeed = 15;
    private int playerAction = IDLE;
    private boolean moving = false;
    private boolean left, up, right, down, jump;
    private float playerSpeed= 1.5f;
    private int[][] levelData;
    private float xDrawOffset = 21 * Game.scaling;
    private float yDrawOffset = 4 * Game.scaling;

    // Jumping + gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.scaling;
    private float jumpSpeed = -2.25f * Game.scaling;
    private float fallSpeedAfterCollision = 0.5f * Game.scaling;
    private boolean inAir = false;


    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initImagebox(x,y,20*Game.scaling,30*Game.scaling);
    }


    public void update() {

        updatePos();
        updateAnimationTick();
        setAnimation();

    }

    public void render(Graphics g, int xLevelOffset) {
        g.drawImage(animations[playerAction][aniIndex], (int)(imagebox.x - xDrawOffset) - xLevelOffset,(int)(imagebox.y - yDrawOffset),120,80,null);
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
        if (jump) {
            jump();
        }
        if (!left && !right && !inAir) {
            return;
        }

        float xSpeed = 0;

        if(left) {
            xSpeed -= playerSpeed;
        }
        if (right){
            xSpeed += playerSpeed;
        }

        if(!inAir) {
            if (!IsEntityOnFloor(imagebox,levelData)) {
                inAir = true;
            }
        }

        if (inAir) {
            if (CanMoveHere(imagebox.x, imagebox.y + airSpeed, imagebox.width, imagebox.height, levelData)) {
                imagebox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                imagebox.y = GetEntityYPosUnderRoofOrAboveFloor(imagebox, airSpeed);
                if (airSpeed > 0) {
                    resetInAir();
                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }

        moving = true;

    }

    private void jump() {
        if (inAir) {
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;

    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(imagebox.x + xSpeed, imagebox.y, imagebox.width, imagebox.height, levelData)) {
            imagebox.x += xSpeed;
        } else {
            imagebox.x = GetEntityXPosWall(imagebox, xSpeed);
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
        if (!IsEntityOnFloor(imagebox, levelData)) {
            inAir = true;
        }
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

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}

