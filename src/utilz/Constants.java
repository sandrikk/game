package utilz;

import main.Game;

public class Constants {

    public static class EnemyConstants {
        public static final int PUMA = 0;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int PUMA_WIDTH_DEFAULT = 72;
        public static final int PUMA_HEIGHT_DEFAULT = 32;

        public static final int PUMA_HEIGHT = (int) (PUMA_HEIGHT_DEFAULT * Game.scaling);
        public static final int PUMA_WIDTH = (int) (PUMA_WIDTH_DEFAULT * Game.scaling);

        public static int GetSpriteAmount(int enemy_type, int enemy_state){
            switch (enemy_type) {
                case PUMA:
                    switch (enemy_state) {
                        case IDLE:
                            return 9;
                        case RUNNING:
                            return 6;
                        case ATTACK:
                            return 7;
                        case HIT:
                            return 4;
                        case DEAD:
                            return 5;
                    }
            }

            return 0;
        }
    }
    public static class UI {
        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 55;
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.scaling);
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.scaling);
        }
    }
    public static class Directions{
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
        public static final int UP = 2;
        public static final int DOWN = 3;
    }
    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;

        public static final int JUMP = 2;

        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case RUNNING:
                    return 5;
                case IDLE:
                    return 2;
                case JUMP:
                    return 3;
                default:
                    return 1;
            }

        }
    }
}
