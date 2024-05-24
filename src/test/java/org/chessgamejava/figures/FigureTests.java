package org.chessgamejava.figures;

import javafx.util.Pair;
import org.chessgamejava.IBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FigureTests {
    private static class TestBoard implements IBoard {
        @Override
        public Pair<CellFill, Figure> getCell(int x, int y) {
            return new Pair<>(CellFill.EMPTY, null);
        }
    }

    private IBoard board;
    private Bishop bishop;
    private King king;
    private Knight knight;
    private Pawn pawn;
    private Queen queen;
    private Rook rook;

    @BeforeEach
    void setUp() {
        board = new TestBoard();
        bishop = new Bishop(true, 0, 0);
        king = new King(true, 0, 0);
        knight = new Knight(true, 0, 0);
        pawn = new Pawn(true, 0, 0);
        queen = new Queen(true, 0, 0);
        rook = new Rook(true, 0, 0);
    }

    @Test
    void bishopCanMoveToValidPosition() {
        assertTrue(bishop.canMove(3, 3, board));
    }

    @Test
    void kingCanMoveToValidPosition() {
        assertTrue(king.canMove(1, 1, board));
        assertTrue(king.canMove(1, 0, board));
        assertTrue(king.canMove(0, 1, board));
    }

    @Test
    void knightCanMoveToValidPosition() {
        assertTrue(knight.canMove(2, 1, board));
    }

    @Test
    void pawnCanMoveToValidPosition() {
        assertTrue(pawn.canMove(0, 2, board));
    }

    @Test
    void queenCanMoveToValidPosition() {
        assertTrue(queen.canMove(3, 3, board));
    }

    @Test
    void rookCanMoveToValidPosition() {
        assertTrue(rook.canMove(0, 3, board));
    }

    @Test
    void bishopCannotMoveToInvalidPosition() {
        assertFalse(bishop.canMove(4, 2, board));
    }

    @Test
    void kingCannotMoveToInvalidPosition() {
        assertFalse(king.canMove(5, 5, board));
    }

    @Test
    void knightCannotMoveToInvalidPosition() {
        assertFalse(knight.canMove(3, 3, board));
    }

    @Test
    void pawnCannotMoveToInvalidPosition() {
        assertFalse(pawn.canMove(2, 2, board));
    }

    @Test
    void queenCannotMoveToInvalidPosition() {
        assertFalse(queen.canMove(4, 2, board));
    }

    @Test
    void rookCannotMoveToInvalidPosition() {
        assertFalse(rook.canMove(3, 2, board));
    }
}