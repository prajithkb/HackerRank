package connect4.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import connect4.Board;
import connect4.Coin;
import connect4.Column;
import connect4.dagger.BoardModule;
import connect4.exceptions.ColumnFullException;
import connect4.exceptions.IllegalColumnIndexException;
import lombok.Getter;
import lombok.val;

public class DefaultBoard implements Board {

    public DefaultBoard(List<Column> columns) {
        this.columns = columns;
    }

    private final List<Column> columns;

    @Override
    public int getNumberOfColumns() {
        return columns.size();
    }

    @Override
    public List<Column> getColumns() {
        return ImmutableList.copyOf(columns);
    }

    public void insertAtColumn(int columnIndex, Coin coin) throws IllegalColumnIndexException, ColumnFullException {
        if (columnIndex >= BoardModule.NUMBER_OF_COLUMNS || columnIndex < 0) {
            throw new IllegalColumnIndexException();
        }
        columns.get(columnIndex).put(coin);
    }

    public boolean tryInsertAtColumn(int columnIndex, Coin coin) {
        try {
            insertAtColumn(columnIndex, coin);
        } catch (IllegalColumnIndexException | ColumnFullException e) {
            return false;
        }
        return true;

    }

    @Override
    public char[][] boardAsString() {
        val values = new char[BoardModule.NUMBER_OF_ROWS][BoardModule.NUMBER_OF_COLUMNS];
        for (int i = 0; i < BoardModule.NUMBER_OF_COLUMNS; i++) {
            for (int j = 0; j < BoardModule.NUMBER_OF_ROWS; j++) {
                char value = ' ';
                if(i < columns.size()){
                    val col = columns.get(i);
                    if( j < col.getSize()){
                        val coins = col.getCoins();
                        value = coins.get(j).getValueAsString();
                    }
                }
                values[BoardModule.NUMBER_OF_ROWS - j - 1][i] = value;
            }
        } return values;
    }

    @Override
    public Coin getCoinAt(int row, int col) {
        if( col < columns.size()){
            val coins = columns.get(col).getCoins();
            if (row < coins.size()){
                return coins.get(row);
            }
        }
        return null;
    }

}
