package org.example.chessgamejava;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.example.chessgamejava.figures.*;
import org.example.chessgamejava.patterns.memento.ChessMoveMemento;
import org.example.chessgamejava.patterns.memento.MoveHistory;
import org.example.chessgamejava.patterns.state.*;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class HelloController implements IBoard {
    @FXML
    private GridPane gameGrid;
    @FXML
    private Label turnLabel;
    @FXML
    private VBox moveHistory;
    @FXML
    private Label totalTime;
    @FXML
    private Label totalMoves;
    @FXML
    private Label figuresKilled;
    @FXML
    private TextField timerField;
    @FXML
    private CheckBox timerCheckbox;
    @FXML
    private Label timerOne;
    @FXML
    private Label timerTwo;
    private LocalDateTime startTime;
    private LocalDateTime lastTickerUpdate = LocalDateTime.now();
    private boolean timerRunning = false;
    private Duration playerOneTimeLeft = Duration.ofSeconds(-1);
    private Duration playerTwoTimeLeft = Duration.ofSeconds(-1);

    @FXML
    private void onCheckTimer(){
        timerField.setDisable(!timerCheckbox.isSelected());
    }

    @FXML
    private void onRestartGame(){
        restartGame();
    }

    @FXML
    protected void initialize() {
        timerField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("([0-5]?\\d|60):([0-5]?\\d|60)")) {
                timerField.setText(oldValue);
            }
        });

        playerTurn = new WhiteTurn();
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
        resetFigures();
        drawFigures();
        turnLabel.setText(playerTurn.getText());

        startTime = LocalDateTime.now();
        //Update timers
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                LocalDateTime currentTime = LocalDateTime.now();
                long delta = java.time.Duration.between(startTime, currentTime).getSeconds();
                long minutes = delta / 60;
                long seconds = delta % 60;
                totalTime.setText("Total time: " + minutes + ":" + (seconds < 10 ? "0" : "") + seconds);

                if (timerRunning) {
                    if (playerTurn instanceof WhiteTurn) {
                        playerOneTimeLeft = playerOneTimeLeft.minus(java.time.Duration.between(lastTickerUpdate, currentTime));
                        if (playerOneTimeLeft.getSeconds() <= 0) {
                            playerTurn = new BlackWin();
                            turnLabel.setText(playerTurn.getText());
                            timerRunning = false;
                        }
                    } else {
                        playerTwoTimeLeft = playerTwoTimeLeft.minus(java.time.Duration.between(lastTickerUpdate, currentTime));
                        if (playerTwoTimeLeft.getSeconds() <= 0) {
                            playerTurn = new WhiteWin();
                            turnLabel.setText(playerTurn.getText());
                            timerRunning = false;
                        }
                    }
                    //get seconds returns total seconds, so we need to get minutes and seconds separately
                    minutes = playerOneTimeLeft.getSeconds() / 60;
                    seconds = playerOneTimeLeft.getSeconds() % 60;
                    timerOne.setText("White: " + minutes + ":" + (seconds < 10 ? "0" : "") + seconds);

                    minutes = playerTwoTimeLeft.getSeconds() / 60;
                    seconds = playerTwoTimeLeft.getSeconds() % 60;
                    timerTwo.setText("Black: " + minutes + ":" + (seconds < 10 ? "0" : "") + seconds);
                }
                lastTickerUpdate = currentTime;
            }
        };
        timer.start();
    }

    private void restartGame(){
        startTime = LocalDateTime.now();
        moves = 0;
        killed = 0;
        moveHistory.getChildren().clear();
        resetFigures();
        drawFigures();
        playerTurn = new WhiteTurn();
        turnLabel.setText(playerTurn.getText());
        totalTime.setText("Total time: 0:00");
        totalMoves.setText("Total moves: 0");
        figuresKilled.setText("Figures killed: 0");

        if (timerCheckbox.isSelected()) {
            timerRunning = true;
            String[] time = timerField.getText().split(":");
            long minutes = Long.parseLong(time[0]);
            long seconds = Long.parseLong(time[1]);
            playerOneTimeLeft = Duration.ofSeconds(minutes * 60L + seconds);
            playerTwoTimeLeft = Duration.ofSeconds(minutes * 60L + seconds);
        }
        else {
            timerRunning = false;
            playerOneTimeLeft = Duration.ofSeconds(-1);
            playerTwoTimeLeft = Duration.ofSeconds(-1);
            timerOne.setText("White: Unlimited Time");
            timerTwo.setText("Black: Unlimited Time");
        }
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

        if (board[x][y].chessFigure != null && playerTurn.canSelectFigure(board[x][y].chessFigure))
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
                if (board[x][y].chessFigure.canMove(boardCell.x, boardCell.y, this)) {
                    // can move - colour orange
                    for (Pair<Integer, Integer> move : selectedCell.chessFigure.getTiles(boardCell.x, boardCell.y, this)) {
                        board[move.getKey()][move.getValue()].colour.setStyle("-fx-background-color: orange");
                    }
                    if(boardCell.chessFigure != null)
                        boardCell.colour.setStyle("-fx-background-color: red");
                    else
                        boardCell.colour.setStyle("-fx-background-color: orange");
                }
            }
        }
    }

    private void tryMoveFigure(int x, int y){
        if (!selectedCell.chessFigure.canMove(x, y, this))
            return;

        moveFigures(x, y);
    }

    private void moveFigures(int x, int y){
        history.addMemento(new ChessMoveMemento(
                selectedCell.x,
                selectedCell.y,
                x, y,
                true,
                board[x][y].chessFigure != null
        ));
        moveHistory.getChildren().add(
                new Label(generateMoveText(selectedCell.x, selectedCell.y, x, y))
        );

        if (board[x][y].chessFigure != null) {
            killed++;
            figuresKilled.setText("Figures killed: " + killed);
        }

        board[x][y].chessFigure = selectedCell.chessFigure;
        selectedCell.chessFigure.move(x, y);
        board[selectedCell.x][selectedCell.y].chessFigure = null;
        gameGrid.getChildren().remove(board[x][y].figure);
        gameGrid.getChildren().remove(selectedCell.figure);
        gameGrid.add(selectedCell.figure, x, y);
        board[x][y].figure = selectedCell.figure;
        selectedCell.figure = null;
        selectedCell = null;
        playerTurn = playerTurn.nextTurn();
        colourCells();
        checkForWin();
        turnLabel.setText(playerTurn.getText());
        moves++;
        totalMoves.setText("Total moves: " + moves);
    }

    private void resetFigures() {
        for (Cell[] boardCellRow : board) {
            for (Cell boardCell : boardCellRow) {
                boardCell.chessFigure = null;
            }
        }

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
                gameGrid.getChildren().remove(board[i][j].figure);
                board[i][j].figure = null;
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

    @Override
    public Pair<CellFill, Figure> getCell(int x, int y) {
        if (board[x][y].chessFigure != null) {
            return new Pair<>(board[x][y].chessFigure.getColor() ? CellFill.WHITE : CellFill.BLACK, board[x][y].chessFigure.getType());
        } else {
            return new Pair<>(CellFill.EMPTY, null);
        }
    }

    public void checkForWin(){
        boolean whiteKing = false;
        boolean blackKing = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].chessFigure != null && board[i][j].chessFigure.getType() == Figure.KING) {
                    if (board[i][j].chessFigure.getColor())
                        whiteKing = true;
                    else
                        blackKing = true;
                }
            }
        }
        if (!whiteKing){
            playerTurn = new BlackWin();
            timerRunning = false;
        }
        if (!blackKing) {
            playerTurn = new WhiteWin();
            timerRunning = false;
        }

        boolean colour = playerTurn instanceof WhiteTurn;
        for(Cell[] row : board){
            for(Cell cell : row){
                if(cell.chessFigure != null && cell.chessFigure.getColor() == colour){
                    for(int i = 0; i < 8; i++){
                        for(int j = 0; j < 8; j++){
                            if(cell.chessFigure.canMove(i, j, this)){
                                return;
                            }
                        }
                    }
                }
            }
        }

        playerTurn = new Draw();
        timerRunning = false;
    }

    @FXML
    private void onLoadGame(){
        File file = new File(savePath);
        if (!file.exists()) {
            return;
        }

        moveHistory.getChildren().clear();
        MoveHistory.GameData gameState = history.loadFromFile(savePath);
        resetFigures();

        for (ChessMoveMemento memento : history) {
            board[memento.getX()][memento.getY()].chessFigure.move(memento.getNewX(), memento.getNewY());
            board[memento.getNewX()][memento.getNewY()].chessFigure = board[memento.getX()][memento.getY()].chessFigure;
            board[memento.getX()][memento.getY()].chessFigure = null;
            moveHistory.getChildren().add(
                    new Label(generateMoveText(memento.getX(), memento.getY(), memento.getNewX(), memento.getNewY()))
            );
        }

        drawFigures();

        switch (gameState.state) {
            case "White's turn":
                playerTurn = new WhiteTurn();
                break;
            case "Black's turn":
                playerTurn = new BlackTurn();
                break;
            case "White wins!":
                playerTurn = new WhiteWin();
                break;
            case "Black wins!":
                playerTurn = new BlackWin();
                break;
            case "Draw":
                playerTurn = new Draw();
                break;
        }

        startTime = LocalDateTime.now().minusSeconds(gameState.time);
        playerOneTimeLeft = Duration.ofSeconds(gameState.player1Time);
        playerTwoTimeLeft = Duration.ofSeconds(gameState.player2Time);
        timerRunning = gameState.timed;

        turnLabel.setText(playerTurn.getText());
        totalMoves.setText("Total moves: " + moves);
        figuresKilled.setText("Figures killed: " + killed);

    }

    @FXML
    private void onSaveGame() {
        MoveHistory.GameData data = new MoveHistory.GameData();
        data.state = playerTurn.getText();
        data.time = Duration.between(startTime, LocalDateTime.now()).getSeconds();
        data.timed = timerRunning;
        data.player1Time = playerOneTimeLeft.getSeconds();
        data.player2Time = playerTwoTimeLeft.getSeconds();
        history.saveToFile(savePath, data);
    }

    private String generateMoveText(int x1, int y1, int x2, int y2){
        String result = "Moved ";
        result += numToLetter(x1+1);
        result += y1+1;
        result += " to ";
        result += numToLetter(x2+1);
        result += y2+1;
        return result;
    }

    private char numToLetter(int input){
        return switch (input) {
            case 1 -> 'a';
            case 2 -> 'b';
            case 3 -> 'c';
            case 4 -> 'd';
            case 5 -> 'e';
            case 6 -> 'f';
            case 7 -> 'g';
            case 8 -> 'h';
            default -> 'X';
        };
    }

    private Cell[][] board = new Cell[8][8];
    private Cell selectedCell = null;
    private IGameState playerTurn;
    private MoveHistory history = new MoveHistory();
    private String savePath = "save.txt";
    private int moves = 0;
    private int killed = 0;
}