package entities;

import main.Game;

import java.awt.geom.Rectangle2D;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.GetEnemyDmg;
import static utilz.Constants.GetMaxHealth;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;
import static entities.Puma.*;


public abstract class Enemy extends Entity {
    protected int aniIndex, enemyState, enemyType;
    protected int aniTick, aniSpeed = 25;
    protected boolean firstUpdate = true;
    protected boolean inAir;
    protected float fallSpeed;
    protected int tileY;
    protected float distanceOfAttack = Game.tiles_size;
    protected float gravity = 0.05f * Game.scaling;
    protected float walkSpeed = 0.4f * Game.scaling;
    protected int walkDir = LEFT;
    protected int maxHealth;
    protected int currentHealth;
    protected boolean active = true;
    protected boolean attackChecked;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initImagebox(x, y, width, height);
        maxHealth = GetMaxHealth(PUMA);
        currentHealth = maxHealth;
    }
    protected void checkFirstUpdate(int[][] lvlData) {
        if (!IsEntityOnFloor(imagebox, lvlData))
            inAir = true;
        firstUpdate = false;
    }
    protected void airUpdate(int[][] lvlData){
        if (CanMoveHere(imagebox.x, imagebox.y + fallSpeed, imagebox.width, imagebox.height, lvlData)) {
            imagebox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            imagebox.y = GetEntityYPosUnderRoofOrAboveFloor(imagebox, fallSpeed);
            tileY = (int) (imagebox.y / Game.tiles_size);
        }
    }
    protected void move(int[][] lvlData){
        float xSpeed = 0;

        if (walkDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        if (CanMoveHere(imagebox.x + xSpeed, imagebox.y, imagebox.width, imagebox.height, lvlData))
            if (IsFloor(imagebox, xSpeed, lvlData)) {
                imagebox.x += xSpeed;
                return;
            }

        changeWalkDir();


    }

    public void hurt(int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0)
            newState(DEAD);
        else
            newState(HIT);
    }

    protected void checkEnemyHit(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.imagebox)) {
            player.changeHealth(-GetEnemyDmg(enemyType));
        }
        attackChecked = true;
    }


    protected void turnToThePlayer(Player player) {
        if (player.imagebox.x > imagebox.x)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }
    protected boolean canSeeThePlayer(int[][] lvlData, Player player) {
        int playerTileY =(int) (player.getImagebox().y / Game.tiles_size);
        if (playerTileY == tileY)
            if (isPlayerInTheRange(player)) {
                if (isTheSightClear(lvlData,imagebox,player.imagebox,tileY))
                    return true;
            }
        return false;
    }

    protected boolean isPlayerInTheRange(Player player) {
        int absoluteValue = (int) Math.abs(player.imagebox.x - imagebox.x);
        return absoluteValue <= distanceOfAttack * 5;
    }

    protected boolean isThePlayerCloseForTheAttack(Player player) {
        int absoluteValue = (int) Math.abs(player.imagebox.x - imagebox.x);
        return absoluteValue <= distanceOfAttack;

    }

    protected void newState(int enemyState) {
        this.enemyState = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
                aniIndex = 0;

                switch (enemyState) {
                    case ATTACK, HIT -> enemyState = IDLE;
                    case DEAD -> active = false;
                }

            }
        }
    }




    private void updateMove(int[][] lvlData) {
        if (firstUpdate) {
            if (!IsEntityOnFloor(imagebox, lvlData))
                inAir = true;
            firstUpdate = false;
        }
        if (inAir) {
            if (CanMoveHere(imagebox.x, imagebox.y + fallSpeed, imagebox.width, imagebox.height, lvlData)) {
                imagebox.y += fallSpeed;
                fallSpeed += gravity;
            } else {
                inAir = false;
                imagebox.y = GetEntityYPosUnderRoofOrAboveFloor(imagebox, fallSpeed);
            }
        } else {
            switch (enemyState) {
                case IDLE:
                    enemyState = RUNNING;
                    break;
                case RUNNING:
                    float xSpeed = 0;

                    if (walkDir == LEFT)
                        xSpeed = -walkSpeed;
                    else
                        xSpeed = walkSpeed;

                    if (CanMoveHere(imagebox.x + xSpeed, imagebox.y, imagebox.width, imagebox.height, lvlData))
                    if (IsFloor(imagebox, xSpeed, lvlData)) {
                        imagebox.x += xSpeed;
                        return;
                    }

                    changeWalkDir();

                    break;
            }
        }
    }

    protected void changeWalkDir() {
        if (walkDir == LEFT)
            walkDir = RIGHT;
        else
            walkDir = LEFT;

    }

    public void resetEnemy() {
        imagebox.x = x;
        imagebox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDLE);
        active = true;
        fallSpeed = 0;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }

    public boolean isActive() {
        return active;
    }
}
