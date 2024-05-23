package org.example.chessgamejava.figures;

import javafx.util.Pair;
import org.example.chessgamejava.IBoard;

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
    public boolean canMove(int x, int y, IBoard board) {
        if (!super.canMove(x, y, board)) {
            return false;
        }

        int dx = x - getX();
        int dy = y - getY();
        int maxStep = firstMove ? 2 : 1;

        if (Math.abs(dx) > 0 && Math.abs(dy) > 1) {
            return false;
        }

        if (Math.abs(dx) > 1 || Math.abs(dy) > maxStep) {
            return false;
        }

        if(Math.signum(dy) != (getColor() ? 1 : -1))
            return false;

        var cell = board.getCell(x, y);
        if (dx == 0 && cell.getKey() != IBoard.CellFill.EMPTY) {
            return false;
        }

        if(dx != 0 && cell.getKey() == IBoard.CellFill.EMPTY)
            return false;

        cell = board.getCell(this.getX(), this.getY() + (getColor() ? 1 : -1));
        if (Math.abs(dy) != 1 && cell.getKey() != IBoard.CellFill.EMPTY)
            return false;

        return true;
    }

    @Override
    public List<Pair<Integer, Integer>> getTiles(int x, int y, IBoard board) {
        if (!canMove(x, y, board)) {
            throw new IllegalArgumentException("Pawn can't move to this tile");
        }
        return new ArrayList<>();
    }

    @Override
    public IBoard.Figure getType() {
        return IBoard.Figure.PAWN;
    }
}
