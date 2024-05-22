package org.example.chessgamejava.figures;

import javafx.util.Pair;

import java.util.List;

public abstract class ChessFigure {
    private final boolean color;  // true - white, false - black
    private int x;
    private int y;

    public ChessFigure(boolean color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public abstract String getIcon();

    public boolean getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract boolean canMove(int x, int y);
    public abstract List<Pair<Integer, Integer>> getTiles(int x, int y);
    public List<Pair<Integer, Integer>> getMoveTiles(int x, int y) {
        var result = getTiles(x, y);
        result.removeLast();
        return result;
    }
}
