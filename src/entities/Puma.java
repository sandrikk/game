package entities;
import main.Game;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

public class Puma extends Enemy {
    public Puma(float x, float y) {
        super(x, y, PUMA_WIDTH, PUMA_HEIGHT, PUMA);
        initImagebox(x,y,(int)(22* Game.scaling),(int)(19*Game.scaling));
    }

    public void update(int[][] lvlData, Player player) {
        updateMove(lvlData,player);
        updateAnimationTick();
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
                    break;
            }
        }
    }
}
