package entities;

import gamestates.ChooseCharacter;
import gamestates.Playing;
import main.Game;
import ui.CharacterButton;
import utilz.LoadPlayerSave;



import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;
import ui.CharacterButton.*;


public class Player extends Entity {
    private static BufferedImage[][] animations; //Deals with switching images for animations.
    private Player player;
    private int aniTick, aniIndex, aniSpeed = 15;
    private int playerAction = IDLE;
    private boolean moving, attacking = false;
    private boolean left, up, right, down, jump;
    private float playerSpeed= 1.5f;
    private int[][] levelData;
    private float xDrawOffset = 21 * Game.scaling;
    private float yDrawOffset = 4 * Game.scaling;
    private int index;

    // Jumping + gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.scaling;
    private float jumpSpeed = -2.25f * Game.scaling;
    private float fallSpeedAfterCollision = 0.5f * Game.scaling;
    private boolean inAir = false;

    // StatusBarUI
    private static BufferedImage statusBarImg;

    private int statusBarWidth = (int) (192 * Game.scaling);
    private int statusBarHeight = (int) (58 * Game.scaling);
    private int statusBarX = (int) (10 * Game.scaling);
    private int statusBarY = (int) (10 * Game.scaling);

    private int healthBarWidth = (int) (150 * Game.scaling - 33);
    private int healthBarHeight = (int) (4 * Game.scaling);
    private int healthBarXStart = (int) (34 * Game.scaling +63);
    private int healthBarYStart = (int) (14 * Game.scaling);

    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;

    // AttackBox
    private Rectangle2D.Float attackBox;

    private int flipX = 0;
    private int flipW = 1;

    private boolean attackChecked;
    private Playing playing;
    //private static Character character = Character.character;


    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        loadAnimations();
        initImagebox(x,y,20*Game.scaling,30*Game.scaling);
        initAttackBox();
    }


    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.scaling), (int) (20 * Game.scaling));
    }


    public void update() {
        updateHealthBar();
        if (currentHealth <= 0) {
            playing.setGameOver(true);
            return;
        }

        updateAttackBox();
        updatePos();
        if (attacking) {
            checkAttack();
        }
        updateAnimationTick();
        setAnimation();

    }

    private void checkAttack() {
        if (attackChecked || aniIndex != 1) {
            return;
        }
        attackChecked = true;
        playing.checkEnemyHit(attackBox);
    }

    private void updateAttackBox() {
        if (right)
            attackBox.x = imagebox.x + imagebox.width + (int) (Game.scaling * 10);
        else if (left)
            attackBox.x = imagebox.x - imagebox.width - (int) (Game.scaling * 10);

        attackBox.y = imagebox.y + (Game.scaling * 10);
    }


    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
    }

    public void render(Graphics g, int xLevelOffset) {
        g.drawImage(animations[playerAction][aniIndex], (int)(imagebox.x - xDrawOffset) - xLevelOffset + flipX,(int)(imagebox.y - yDrawOffset),width * flipW,height,null);
        //drawImagebox(g, xLevelOffset);
        //drawAttackBox(g, xLevelOffset);
        drawUI(g);
    }

    /*
    private void drawAttackBox(Graphics g, int lvlOffsetX) {
        g.setColor(Color.red);
        g.drawRect((int) attackBox.x - lvlOffsetX, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

     */

    private void drawAttackBox(Graphics g, int lvlOffsetX) {
        g.setColor(Color.red);
        g.drawRect((int) attackBox.x - lvlOffsetX, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);

    }

    private void drawUI(Graphics g) {
        g.drawImage(statusBarImg, 50, 18, 300, 100 , null);
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
    }


    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }

    }
    public void setAnimation() {

        int startAni = playerAction;

        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;

        if (inAir) {
            if (airSpeed < 0)
                playerAction = JUMP;
            else
                playerAction = FALLING;
        }

        if (attacking) {
            playerAction = ATTACK;
            if (startAni != ATTACK) {
                aniIndex = 1;
                aniTick = 0;
                return;
            }
        }
        if (startAni != playerAction)
            resetAniTick();
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
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
            flipX = width;
            flipW = -1;
        }
        if (right){
            xSpeed += playerSpeed;
            flipX = 0;
            flipW = 1;
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
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
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

    public void changeHealth(int value) {
        currentHealth += value;

        if (currentHealth <= 0)
            currentHealth = 0;
        else if (currentHealth >= maxHealth)
            currentHealth = maxHealth;
    }


    public static void loadAnimations() {
        BufferedImage img = LoadPlayerSave.GetSpriteAtlas(LoadPlayerSave.Player_Atlas1);
        animations = new BufferedImage[7][8];
        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; i++)
                animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
        statusBarImg = LoadPlayerSave.GetSpriteAtlas(LoadPlayerSave.Status_Bar);
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

    public void resetAll() {
        resetDirectionsBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        playerAction = IDLE;
        currentHealth = maxHealth;

        imagebox.x = x;
        imagebox.y = y;

        if (!IsEntityOnFloor(imagebox, levelData))
            inAir = true;
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

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}

