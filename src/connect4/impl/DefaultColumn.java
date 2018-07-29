package connect4.impl;

import java.util.List;

import com.google.common.collect.ImmutableList;
import connect4.Coin;
import connect4.Column;
import connect4.dagger.BoardModule;
import connect4.exceptions.ColumnFullException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultColumn implements Column {

    private final List<Coin> coins;

    private final String id;

    public DefaultColumn(List<Coin> coins, String id) {
        this.coins = coins;
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getSize() {
        return coins.size();
    }

    @Override
    public Status getStatus() {
        return BoardModule.NUMBER_OF_ROWS == coins.size() ? Status.Full : Status.Available;
    }

    public void put(Coin coin) throws ColumnFullException {
        if (coins.size() >= BoardModule.NUMBER_OF_ROWS) {
            throw new ColumnFullException();
        }
        log.info("Inserted coin @ {}, for column {}", coins.size(), id);
        coins.add(coin);
    }

    @Override
    public List<Coin> getCoins() {
        return ImmutableList.copyOf(coins);
    }

    public boolean tryPut(Coin coin) {
        try {
            put(coin);
        } catch (ColumnFullException e) {
            return false;
        }
        return true;
    }

}
