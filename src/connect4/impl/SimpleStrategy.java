package connect4.impl;

import java.util.List;

import connect4.Board;
import connect4.Column;
import connect4.Strategy;

public class SimpleStrategy implements Strategy {

    @Override
    public int pickNextColumn(Board board) {
        List<Column> cols = board.getColumns();
        for (int i = 0; i < cols.size(); i++) {
            if (cols.get(i).getStatus() != Column.Status.Full) {
                return i;
            }
        }
        return -1;

    }
}
