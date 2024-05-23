package org.example.chessgamejava.patterns.state;

import org.example.chessgamejava.figures.ChessFigure;

public interface IGameState {
    String getText();
    boolean canSelectFigure(ChessFigure figure);
    IGameState nextTurn();
}
