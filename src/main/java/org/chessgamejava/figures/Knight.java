package org.chessgamejava.figures;

import javafx.util.Pair;
import org.chessgamejava.IBoard;

import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessFigure {
    public Knight(boolean color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public String getIcon() {
        return getColor() ? "\u2658" : "\u265E";
    }

    @Override
    public boolean canMove(int x, int y, IBoard board) {
        if (!super.canMove(x, y, board)) {
            return false;
        }

        int dx = x - getX();
        int dy = y - getY();
        return (Math.abs(dx) == 2 && Math.abs(dy) == 1) || (Math.abs(dx) == 1 && Math.abs(dy) == 2);
    }

    @Override
    public List<Pair<Integer, Integer>> getTiles(int x, int y, IBoard board) {
        if (!canMove(x, y, board)) {
            throw new IllegalArgumentException("Knight can't move to this tile");
        }

        return new ArrayList<>();
    }

    @Override
    public IBoard.Figure getType() {
        return IBoard.Figure.KNIGHT;
    }
}
