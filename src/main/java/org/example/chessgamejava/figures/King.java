package org.example.chessgamejava.figures;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessFigure {
    public King(boolean color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public String getIcon() {
        return getColor() ? "\u2654" : "\u265A";
    }

    @Override
    public boolean canMove(int x, int y) {
        int dx = x - getX();
        int dy = y - getY();
        return Math.abs(dx) <= 1 && Math.abs(dy) <= 1;
    }

    @Override
    public List<Pair<Integer, Integer>> getTiles(int x, int y) {
        if (!canMove(x, y)) {
            throw new IllegalArgumentException("King can't move to this tile");
        }
        var tiles = new ArrayList<Pair<Integer, Integer>>();
        tiles.add(new Pair<>(x, y));
        return tiles;
    }
}
