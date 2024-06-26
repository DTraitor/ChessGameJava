package org.chessgamejava.patterns.state;

import org.chessgamejava.figures.ChessFigure;

public class BlackWin implements IGameState {
    @Override
    public String getText() {
        return "Black wins!";
    }

    @Override
    public boolean canSelectFigure(ChessFigure figure) {
        return false;
    }

    @Override
    public IGameState nextTurn() {
        return this;
    }
}
