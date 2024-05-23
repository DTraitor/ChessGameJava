package org.example.chessgamejava.patterns.state;

import org.example.chessgamejava.figures.ChessFigure;

public class BlackTurn implements IGameState {
    @Override
    public String getText() {
        return "Black's turn";
    }

    @Override
    public boolean canSelectFigure(ChessFigure figure) {
        return !figure.getColor();
    }

    @Override
    public IGameState nextTurn() {
        return new WhiteTurn();
    }
}
