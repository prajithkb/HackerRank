package connect4.impl;

import java.util.Random;

import connect4.Board;
import connect4.Strategy;
import connect4.dagger.BoardModule;

public class RandomStrategy implements Strategy {

    Random rand = new Random();

    @Override
    public int pickNextColumn(Board board) {
        int nextColumnIndex = rand.nextInt(BoardModule.NUMBER_OF_COLUMNS);
        while (board.getColumns().get(nextColumnIndex).getSize() >= BoardModule.NUMBER_OF_ROWS) {
            nextColumnIndex = rand.nextInt(BoardModule.NUMBER_OF_COLUMNS);
        }
        return nextColumnIndex;
    }
}
