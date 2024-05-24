package org.chessgamejava.figures;

import javafx.util.Pair;
import org.chessgamejava.IBoard;

import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessFigure {
    public Rook(boolean color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public String getIcon() {
        return getColor() ? "\u2656" : "\u265C";
    }

    @Override
    public boolean canMove(int x, int y, IBoard board) {
        if (!super.canMove(x, y, board)) {
            return false;
        }

        int dx = x - getX();
        int dy = y - getY();
        if(dx != 0 && dy != 0)
            return false;

        int stepX = Integer.compare(dx, 0);
        int stepY = Integer.compare(dy, 0);
        int i = getX() + stepX;
        int j = getY() + stepY;
        while (i != x || j != y) {
            var cell = board.getCell(i, j);
            if(cell.getKey() != IBoard.CellFill.EMPTY)
                return false;
            i += stepX;
            j += stepY;
        }

        return true;
    }

    @Override
    public List<Pair<Integer, Integer>> getTiles(int x, int y, IBoard board) {
        if (!canMove(x, y, board)) {
            throw new IllegalArgumentException("Rook can't move to this tile");
        }

        List<Pair<Integer, Integer>> tiles = new ArrayList<>();

        int dx = x - getX();
        int dy = y - getY();
        int stepX = dx > 0 ? 1 : -1;
        int stepY = dy > 0 ? 1 : -1;
        int i = getX() + stepX;
        int j = getY() + stepY;

        while (i != x || j != y) {
            tiles.add(new Pair<>(i, j));
            i += stepX;
            j += stepY;
        }

        return tiles;
    }

    @Override
    public IBoard.Figure getType() {
        return IBoard.Figure.ROOK;
    }
}
