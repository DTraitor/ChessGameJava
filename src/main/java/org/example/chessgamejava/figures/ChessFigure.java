package org.example.chessgamejava.figures;

import javafx.util.Pair;
import org.example.chessgamejava.IBoard;

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

    public boolean canMove(int x, int y, IBoard board){
        return board.getCell(x, y).getKey() != (getColor() ? IBoard.CellFill.WHITE : IBoard.CellFill.BLACK);
    }
    public abstract List<Pair<Integer, Integer>> getTiles(int x, int y, IBoard board);

    public abstract IBoard.Figure getType();
}
