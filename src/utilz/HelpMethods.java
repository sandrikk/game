package utilz;

import main.Game;

public class HelpMethods {
    public static boolean CanMoveHere(float x, float y, int width, int height, int[][] levelData) {
        if (!IsSolid(x,y,levelData)) {
            if (!IsSolid(x+width,y+height,levelData)) {
                if (!IsSolid(x,y+height,levelData)) {
                    if (!IsSolid(x + width,y,levelData)) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    private static boolean IsSolid(float x, float y, int[][] levelData) {
        if (x < 0 || x >= Game.game_width) {
            return true;
        }
        if (y < 0 || y >= Game.game_height) {
            return true;
        }

        float xIndex = x / Game.tiles_size;
        float yIndex = y / Game.tiles_size;

        int value = levelData[(int) yIndex][(int) xIndex];

        if (value < 0 || value >= 48 || value != 11)
            return true;

        return false;


    }
}
