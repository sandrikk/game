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

    public void update(int[][] lvlData) {
        updateMove(lvlData);
        updateAnimationTick();
    }
    private void updateMove(int[][] lvlData) {
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
                    move(lvlData);
                    break;
            }
        }
    }
}
