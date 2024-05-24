package org.chessgamejava.patterns.memento;

public class ChessMoveMemento {
    private int x;
    private int y;
    private int newX;
    private int newY;
    private boolean isFigureMoved;
    private boolean isFigureKilled;

    public ChessMoveMemento(int x, int y, int newX, int newY, boolean isFigureMoved, boolean isFigureKilled) {
        this.x = x;
        this.y = y;
        this.newX = newX;
        this.newY = newY;
        this.isFigureMoved = isFigureMoved;
        this.isFigureKilled = isFigureKilled;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNewX() {
        return newX;
    }

    public int getNewY() {
        return newY;
    }

    public boolean isFigureMoved() {
        return isFigureMoved;
    }

    public boolean isFigureKilled() {
        return isFigureKilled;
    }
}
