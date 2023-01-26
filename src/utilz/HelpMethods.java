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
        int maximumWidth = levelData[0].length * Game.tiles_size;
        if (x < 0 || x >= maximumWidth) {
            return true;
        }
        if (y < 0 || y >= Game.game_height) {
            return true;
        }

        float xIndex = x / Game.tiles_size;
        float yIndex = y / Game.tiles_size;



        return IsTheTileSolid((int) xIndex, (int) yIndex,levelData);
    }

    public static boolean IsTheTileSolid(int xTile, int yTile,int[][] levelData) {
        int value = levelData[yTile][xTile];

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
    public static boolean IsFloor(Rectangle2D.Float imagebox, float xSpeed, int[][] levelData ) {
        if (xSpeed > 0)
            return IsSolid(imagebox.x + imagebox.width, imagebox.y + imagebox.height + 1, levelData);
        else
            return IsSolid(imagebox.x + xSpeed, imagebox.y + imagebox.height + 1, levelData);
    }

    public static boolean AreAllTilesWalkable (int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++){
            if (IsTheTileSolid(xStart + i,y,lvlData))
                return false;
            if (!IsTheTileSolid(xStart + i,y +1,lvlData))
                return false;


        }
        return true;

}
    public static boolean isTheSightClear(int[][] lvlData, Rectangle2D.Float theFirstHitbox, Rectangle2D.Float theSecondHitbox, int tileY) {
        int theFirstXTile = (int) (theFirstHitbox.x / Game.tiles_size);
        int theSecondXTile = (int) (theSecondHitbox.x / Game.tiles_size);

        if (theFirstXTile > theSecondXTile) {
            return AreAllTilesWalkable(theSecondXTile,theFirstXTile,tileY,lvlData);
        } else {
            return AreAllTilesWalkable(theFirstXTile,theSecondXTile,tileY,lvlData);

        }
    }
}
