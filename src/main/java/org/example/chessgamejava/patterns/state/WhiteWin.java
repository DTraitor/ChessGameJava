package org.example.chessgamejava.patterns.state;

import org.example.chessgamejava.figures.ChessFigure;

public class WhiteWin implements IGameState {
    @Override
    public String getText() {
        return "White wins!";
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
