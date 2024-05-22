package org.example.chessgamejava;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.example.chessgamejava.figures.*;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private GridPane gameGrid;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void initialize() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Cell(i, j);
                Label cell = new Label();
                cell.setPrefSize(40, 40);
                cell.setStyle("-fx-background-color: " + ((i + j) % 2 == 0 ? "white" : "grey"));
                cell.setOnMouseClicked(event -> cellClicked(cell));
                board[i][j].colour = cell;
                gameGrid.add(cell, i, j);
            }
        }
        colourCells();
        createFigures();
        drawFigures();
    }

    private void colourCells(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Cell cell = board[i][j];
                if (cell == selectedCell)
                    cell.colour.setStyle("-fx-background-color: blue");
                else
                    cell.colour.setStyle("-fx-background-color: " + ((i + j) % 2 == 0 ? "white" : "grey"));
            }
        }
    }

    private void cellClicked(Label cell){
        int x = GridPane.getColumnIndex(cell);
        int y = GridPane.getRowIndex(cell);

        if (board[x][y].chessFigure != null && board[x][y].chessFigure.getColor() != blackTurn)
            selectFigure(x, y);
        else if(selectedCell != null) {
            tryMoveFigure(x, y);
        }

    }

    private void selectFigure(int x, int y){
        selectedCell = board[x][y];
        colourCells();

        for (Cell[] boardCellRow : board) {
            for (Cell boardCell : boardCellRow) {
                if (boardCell.chessFigure == null && board[x][y].chessFigure.canMove(boardCell.x, boardCell.y)) {
                    // can move - colour orange
                    for (Pair<Integer, Integer> move : selectedCell.chessFigure.getTiles(boardCell.x, boardCell.y)) {
                        if (board[move.getKey()][move.getValue()].chessFigure != null)
                            break;
                        board[move.getKey()][move.getValue()].colour.setStyle("-fx-background-color: orange");
                    }
                }
            }
        }
    }

    private void tryMoveFigure(int x, int y){
        if (!selectedCell.chessFigure.canMove(x, y))
            return;
        for (Pair<Integer, Integer> move : selectedCell.chessFigure.getMoveTiles(x, y)) {
            if (board[move.getKey()][move.getValue()].chessFigure != null)
                return;
        }

        moveFigures(x, y);
    }

    private void moveFigures(int x, int y){
        board[x][y].chessFigure = selectedCell.chessFigure;
        selectedCell.chessFigure.move(x, y);
        board[selectedCell.x][selectedCell.y].chessFigure = null;
        gameGrid.getChildren().remove(board[x][y].figure);
        gameGrid.getChildren().remove(selectedCell.figure);
        gameGrid.add(selectedCell.figure, x, y);
        board[x][y].figure = selectedCell.figure;
        selectedCell.figure = null;
        selectedCell = null;
        blackTurn = !blackTurn;
        colourCells();
    }

    private void createFigures() {
        // White figures
        board[0][0].chessFigure = new Rook(true, 0, 0);
        board[1][0].chessFigure = new Knight(true, 1, 0);
        board[2][0].chessFigure = new Bishop(true, 2, 0);
        board[3][0].chessFigure = new Queen(true, 3, 0);
        board[4][0].chessFigure = new King(true, 4, 0);
        board[5][0].chessFigure = new Bishop(true, 5, 0);
        board[6][0].chessFigure = new Knight(true, 6, 0);
        board[7][0].chessFigure = new Rook(true, 7, 0);
        for (int i = 0; i < 8; i++) {
            board[i][1].chessFigure = new Pawn(true, i, 1);
        }

        // Black figures
        board[0][7].chessFigure = new Rook(false, 0, 7);
        board[1][7].chessFigure = new Knight(false, 1, 7);
        board[2][7].chessFigure = new Bishop(false, 2, 7);
        board[3][7].chessFigure = new Queen(false, 3, 7);
        board[4][7].chessFigure = new King(false, 4, 7);
        board[5][7].chessFigure = new Bishop(false, 5, 7);
        board[6][7].chessFigure = new Knight(false, 6, 7);
        board[7][7].chessFigure = new Rook(false, 7, 7);
        for (int i = 0; i < 8; i++) {
            board[i][6].chessFigure = new Pawn(false, i, 6);
        }
    }

    private void drawFigures() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessFigure figure = board[i][j].chessFigure;
                if (figure != null) {
                    Label label = new Label(figure.getIcon());
                    label.setStyle("-fx-font-size: 40; -fx-alignment: center;");
                    label.setMouseTransparent(true);
                    board[i][j].figure = label;
                    gameGrid.add(label, i, j);
                }
            }
        }
    }

    private Cell[][] board = new Cell[8][8];
    private Cell selectedCell = null;
    private boolean blackTurn = false;
}