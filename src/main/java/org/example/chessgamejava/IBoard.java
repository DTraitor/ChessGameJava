package org.example.chessgamejava;

import javafx.util.Pair;

public interface IBoard {
    enum Figure {
        ROOK,
        KNIGHT,
        BISHOP,
        QUEEN,
        KING,
        PAWN
    }

    enum CellFill {
        EMPTY,
        WHITE,
        BLACK
    }

    Pair<CellFill, Figure> getCell(int x, int y);
}
