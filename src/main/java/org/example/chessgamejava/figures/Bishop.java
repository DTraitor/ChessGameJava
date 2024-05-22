package org.example.chessgamejava.figures;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessFigure {
    public Bishop(boolean color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public String getIcon() {
        return getColor() ? "\u2657" : "\u265D";
    }

    @Override
    public boolean canMove(int x, int y) {
        int dx = x - getX();
        int dy = y - getY();
        return Math.abs(dx) == Math.abs(dy);
    }

    @Override
    public List<Pair<Integer, Integer>> getTiles(int x, int y) {
        if (!canMove(x, y)) {
            throw new IllegalArgumentException("Bishop can't move to this tile");
        }
        List<Pair<Integer, Integer>> tiles = new ArrayList<>();
        int dx = x - getX();
        int dy = y - getY();
        if (Math.abs(dx) == Math.abs(dy)) {
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
