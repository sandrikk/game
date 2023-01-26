package gamestates;

import main.Game;
import ui.CharacterButton;
import ui.MenuButton;

import java.awt.event.MouseEvent;

public class State {

    protected Game game;
    public boolean isIn(MouseEvent e, MenuButton mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }

    public boolean isInCh(MouseEvent e, CharacterButton chb) {
        return chb.getBounds().contains(e.getX(), e.getY());
    }

    public State(Game game) {
        this.game = game;
    }
    public Game getGame() {
        return game;
    }
}

