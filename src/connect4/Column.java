package connect4;

import java.util.List;

import connect4.exceptions.ColumnFullException;

public interface Column {

    String getId();

    int getSize();

    Status getStatus();

    void put(Coin coin) throws ColumnFullException;

    List<Coin> getCoins();

    boolean tryPut(Coin coin);

    enum Status {
        Available,
        Full
    }
}
