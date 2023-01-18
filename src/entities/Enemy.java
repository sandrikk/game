package entities;

import main.Game;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;


public abstract class Enemy extends Entity {
    protected int aniIndex, enemyState, enemyType;
    protected int aniTick, aniSpeed = 25;
    protected boolean firstUpdate = true;
    protected boolean inAir;
    protected float fallSpeed;
    protected float gravity = 0.05f * Game.scaling;
    protected float walkSpeed = 0.4f * Game.scaling;
    protected int walkDir = LEFT;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initImagebox(x, y, width, height);
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

    protected void canSeeThePlayer(int[][] lvlData, Player player) {
        int playerTileY = player.getHitbox

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

    public int getAniIndex() {
        return aniIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }
}
