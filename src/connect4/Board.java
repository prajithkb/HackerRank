package connect4;

import java.util.List;

import connect4.exceptions.ColumnFullException;
import connect4.exceptions.IllegalColumnIndexException;

public interface Board {

    int getNumberOfColumns();

    List<Column> getColumns();

    void insertAtColumn(int columnIndex, Coin coin) throws IllegalColumnIndexException, ColumnFullException;

    boolean tryInsertAtColumn(int columnIndex, Coin coin);

    char[][] boardAsString();

    Coin getCoinAt(int row, int col);
}
