package utilz;

import main.Game;

import java.awt.geom.Rectangle2D;

public class HelpMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData) {
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

    public static float GetEntityXPosWall(Rectangle2D.Float imagebox, float xSpeed) {
        int currentTile = (int) (imagebox.x / Game.tiles_size);
        if (xSpeed > 0) {
            int tileXPos = currentTile * Game.tiles_size;
            int xOffset = (int)(Game.tiles_size - imagebox.width);
            return tileXPos + xOffset - 1;
        } else {
            return currentTile * Game.tiles_size;
        }
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float imagebox, float airSpeed) {
        int currentTile = (int)(imagebox.y / Game.tiles_size);
        if (airSpeed > 0) {
            //Falling
            int tileYPos = currentTile * Game.tiles_size;
            int yOffset = (int)(Game.tiles_size - imagebox.height);
            return tileYPos + yOffset - 1;
        } else {
            // Jumping
            return currentTile * Game.tiles_size;
        }
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float imagebox, int[][] levelData) {
        if (!IsSolid(imagebox.x, imagebox.y + imagebox.height + 1, levelData )) {
            if (!IsSolid(imagebox.x + imagebox.width, imagebox.y + imagebox.height + 1, levelData )) {
                return false;
            }
        }
        return true;
    }
}
