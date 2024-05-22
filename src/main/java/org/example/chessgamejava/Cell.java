package org.example.chessgamejava;

import javafx.scene.control.Label;
import org.example.chessgamejava.figures.ChessFigure;

public class Cell {
    public Label colour = null;
    public Label figure = null;
    public ChessFigure chessFigure = null;
    public int x;
    public int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
