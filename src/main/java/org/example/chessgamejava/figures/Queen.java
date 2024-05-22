package org.example.chessgamejava.figures;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Queen extends ChessFigure {
    public Queen(boolean color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public String getIcon() {
        return getColor() ? "\u2655" : "\u265B";
    }

    @Override
    public boolean canMove(int x, int y) {
        int dx = x - getX();
        int dy = y - getY();
        return dx == 0 || dy == 0 || Math.abs(dx) == Math.abs(dy);
    }

    @Override
    public List<Pair<Integer, Integer>> getTiles(int x, int y) {
        if (!canMove(x, y)) {
            throw new IllegalArgumentException("Queen can't move to this tile");
        }

        List<Pair<Integer, Integer>> tiles = new ArrayList<>();
        int dx = x - getX();
        int dy = y - getY();
        if (dx == 0 || dy == 0) {
            int stepX = Integer.compare(dx, 0);
            int stepY = Integer.compare(dy, 0);
            int i = getX() + stepX;
            int j = getY() + stepY;
            while (i != x + stepX || j != y + stepY) {
                tiles.add(new Pair<>(i, j));
                i += stepX;
                j += stepY;
            }
        } else if (Math.abs(dx) == Math.abs(dy)) {
            int stepX = dx > 0 ? 1 : -1;
            int stepY = dy > 0 ? 1 : -1;
            int i = getX() + stepX;
            int j = getY() + stepY;
            while (i != x + stepX) {
                tiles.add(new Pair<>(i, j));
                i += stepX;
                j += stepY;
            }
        }
        return tiles;
    }
}
