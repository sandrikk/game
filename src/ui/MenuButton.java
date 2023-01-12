package ui;

import gamestates.Gamestate;

import java.awt.image.BufferedImage;

public class MenuButton {
    private int xPos, yPos, rowIndex;
    private Gamestate state;
    private BufferedImage[] images;
    public MenuButton(int xPos, int yPos, int rowIndex, Gamestate state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.state = state;
        this.rowIndex = rowIndex;
        loadImages();
    }

    private void loadImages() {
    }

}


