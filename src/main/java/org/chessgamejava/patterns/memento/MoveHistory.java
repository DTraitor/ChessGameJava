package org.chessgamejava.patterns.memento;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class MoveHistory implements Iterable<ChessMoveMemento> {
    public static class GameData {
        public String state;
        public long time;
        public boolean timed;
        public long player1Time;
        public long player2Time;
    }

    private List<ChessMoveMemento> mementos = new ArrayList<>();

    public void saveToFile(String filepath, GameData data){
        try {
            FileWriter writer = new FileWriter(filepath);
            writer.write(data.state + "|" + data.time + "|" + data.timed + "|" + data.player1Time + "|" + data.player2Time + "\n");
            for (ChessMoveMemento memento : mementos) {
                writer.write(memento.getX() + " " + memento.getY() + " " + memento.getNewX() + " " + memento.getNewY() + " " + memento.isFigureMoved() + " " + memento.isFigureKilled() + "\n");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GameData loadFromFile(String filepath){
        try {
            mementos.clear();
            java.io.File file = new java.io.File(filepath);
            java.util.Scanner scanner = new java.util.Scanner(file);
            String firstLine = scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(" ");
                mementos.add(new ChessMoveMemento(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Boolean.parseBoolean(data[4]), Boolean.parseBoolean(data[5])));
            }
            scanner.close();
            GameData state = new GameData();
            String[] firstLineData = firstLine.split("\\|");
            state.state = firstLineData[0];
            state.time = Long.parseLong(firstLineData[1]);
            state.timed = Boolean.parseBoolean(firstLineData[2]);
            state.player1Time = Long.parseLong(firstLineData[3]);
            state.player2Time = Long.parseLong(firstLineData[4]);
            return state;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addMemento(ChessMoveMemento memento) {
        mementos.add(memento);
    }

    public ChessMoveMemento getLastMemento() {
        return mementos.getLast();
    }

    public Iterator<ChessMoveMemento> iterator() {
        return new MoveHistoryIterator();
    }

    private class MoveHistoryIterator implements Iterator<ChessMoveMemento> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < mementos.size();
        }

        @Override
        public ChessMoveMemento next() {
            return mementos.get(index++);
        }
    }
}
