package org.example.chessgamejava.figures;

import javafx.util.Pair;

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
    public boolean canMove(int x, int y) {
        int dx = x - getX();
        int dy = y - getY();
        return dx == 0 || dy == 0;
    }

    @Override
    public List<Pair<Integer, Integer>> getTiles(int x, int y){
        if (!canMove(x, y)) {
            throw new IllegalArgumentException("Rook can't move to this tile");
        }

        var tiles = new ArrayList<Pair<Integer, Integer>>();
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
        }

        return tiles;
    }
}
