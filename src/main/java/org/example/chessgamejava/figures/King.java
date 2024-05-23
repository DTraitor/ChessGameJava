package org.example.chessgamejava.figures;

import javafx.util.Pair;
import org.example.chessgamejava.IBoard;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessFigure {
    private boolean firstMove = true;

    public King(boolean color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public String getIcon() {
        return getColor() ? "\u2654" : "\u265A";
    }

    @Override
    public void move(int x, int y) {
        super.move(x, y);
        firstMove = false;
    }

    @Override
    public boolean canMove(int x, int y, IBoard board) {
        if (!super.canMove(x, y, board)) {
            return false;
        }

        int dx = x - getX();
        int dy = y - getY();
        return Math.abs(dx) <= 1 && Math.abs(dy) <= 1;
    }

    @Override
    public List<Pair<Integer, Integer>> getTiles(int x, int y, IBoard board) {
        if (!canMove(x, y, board)) {
            throw new IllegalArgumentException("King can't move to this tile");
        }
        return new ArrayList<>();
    }

    @Override
    public IBoard.Figure getType() {
        return IBoard.Figure.KING;
    }
}
