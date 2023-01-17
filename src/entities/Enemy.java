package entities;

import main.Game;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;


public abstract class Enemy extends Entity {
    private int aniIndex, enemyState, enemyType;
    private int aniTick, aniSpeed = 25;
    private boolean firstUpdate = true;
    private boolean inAir;
    private float fallSpeed;
    private float gravity = 0.05f * Game.scaling;
    private float walkSpeed = 0.4f * Game.scaling;
    private int walkDir = LEFT;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initImagebox(x, y, width, height);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
                aniIndex = 0;
            }
        }
    }

    public void update(int[][] lvlData) {
        updateMove(lvlData);
        updateAnimationTick();
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

    private void changeWalkDir() {
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
