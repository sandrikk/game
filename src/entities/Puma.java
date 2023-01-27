package entities;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

public class Puma extends Enemy {
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;
    
    public Puma(float x, float y) {
        super(x, y, PUMA_WIDTH, PUMA_HEIGHT, PUMA);
        initImagebox(x,y,(int)(22* Game.scaling),(int)(19*Game.scaling));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (52 * Game.scaling), (int) (19 * Game.scaling));
        attackBoxOffsetX = (int) (Game.scaling * 15);
    }

    public void update(int[][] lvlData, Player player) {
        updateMove(lvlData,player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackBox.x = imagebox.x - attackBoxOffsetX;
        attackBox.y = imagebox.y;
    }

    private void updateMove(int[][] lvlData, Player player) {
        if (firstUpdate)
            checkFirstUpdate(lvlData);
        if (inAir) {
        airUpdate(lvlData);
        } else {
            switch (enemyState) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if (canSeeThePlayer(lvlData,player))
                        turnToThePlayer(player);
                    if (isThePlayerCloseForTheAttack(player))
                        newState(ATTACK);
                    move(lvlData);

                case ATTACK:
                    if (aniIndex == 0) {
                        attackChecked = false;
                    }
                    if (aniIndex == 3 && !attackChecked) {
                        checkEnemyHit(attackBox, player);
                    }
                    break;
                case HIT:
            }
        }
    }



    public void drawAttackBox(Graphics g, int xLvlOffset) {
        //g.setColor(Color.red);
       // g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    public int flipX() {
        if (walkDir == RIGHT)
            return width;
        else
            return 0;
    }

    public int flipW() {
        if (walkDir == RIGHT)
            return -1;
        else
            return 1;

    }
}
