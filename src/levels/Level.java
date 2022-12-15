package levels;


public class Level {
    private int[][] levelInfo;

    public Level(int[][] levelInfo) {
        this.levelInfo = levelInfo;
    }

    public int getSpriteIndex(int x, int y) {
        return levelInfo[y][x];
    }
}
