package org.chessgamejava.patterns.state;

import org.chessgamejava.figures.ChessFigure;

public interface IGameState {
    String getText();
    boolean canSelectFigure(ChessFigure figure);
    IGameState nextTurn();
}
