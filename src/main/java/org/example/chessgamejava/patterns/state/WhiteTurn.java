package org.example.chessgamejava.patterns.state;

import org.example.chessgamejava.figures.ChessFigure;

public class WhiteTurn implements IGameState {
    @Override
    public String getText() {
        return "White's turn";
    }

    @Override
    public boolean canSelectFigure(ChessFigure figure) {
        return figure.getColor();
    }

    @Override
    public IGameState nextTurn() {
        return new BlackTurn();
    }
}
