package org.example.chessgamejava.figures;

import javafx.util.Pair;

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
    public boolean canMove(int x, int y) {
        int dx = x - getX();
        int dy = y - getY();
        return (Math.abs(dx) == 2 && Math.abs(dy) == 1) || (Math.abs(dx) == 1 && Math.abs(dy) == 2);
    }

    @Override
    public List<Pair<Integer, Integer>> getTiles(int x, int y) {
        if (!canMove(x, y)) {
            throw new IllegalArgumentException("Knight can't move to this tile");
        }

        var result = new ArrayList<Pair<Integer, Integer>>();
        result.add(new Pair<>(x, y));
        return result;
    }
}
