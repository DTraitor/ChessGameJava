package org.example.chessgamejava.figures;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pawn extends ChessFigure {
    private boolean firstMove = true;

    public Pawn(boolean color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public String getIcon() {
        return getColor() ? "\u2659" : "\u265F";
    }

    @Override
    public void move(int x, int y) {
        super.move(x, y);
        firstMove = false;
    }

    @Override
    public boolean canMove(int x, int y) {
        int dx = x - getX();
        int dy = y - getY();
        if (getColor()) {
            if (dx == 0 && dy == 1) {
                return true;
            }
            return firstMove && dx == 0 && dy == 2;
        } else {
            if (dx == 0 && dy == -1) {
                return true;
            }
            return firstMove && dx == 0 && dy == -2;
        }
    }

    @Override
    public List<Pair<Integer, Integer>> getTiles(int x, int y) {
        if (!canMove(x, y)) {
            throw new IllegalArgumentException("Pawn can't move to this tile");
        }
        var result = new ArrayList<Pair<Integer, Integer>>();
        result.add(new Pair<>(x, y));
        return result;
    }
}
