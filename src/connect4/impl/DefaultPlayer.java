package connect4.impl;

import javax.inject.Inject;

import connect4.Board;
import connect4.Coin;
import connect4.CoinFactory;
import connect4.CoinTypes;
import connect4.Player;
import connect4.Strategy;
import connect4.exceptions.ColumnFullException;
import connect4.exceptions.IllegalColumnIndexException;
import connect4.exceptions.InvalidMoveException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultPlayer implements Player {

    private final String name;

    private final CoinTypes type;

    private final Strategy strategy;

    private CoinFactory coinFactory;

    @Inject
    public DefaultPlayer(String name, CoinTypes type, CoinFactory coinFactory, Strategy strategy) {
        this.name = name;
        this.type = type;
        this.coinFactory = coinFactory;
        this.strategy = strategy;
        log.info("Created player {}, with coinType - {}, with strategy - {}", name, type, strategy);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public CoinTypes getType() {
        return type;
    }

    @Override
    public void makeAMove(Board board) throws InvalidMoveException {
        log.info("{} to make a move", name);
        Coin coin = coinFactory.getCoin(type);
        int indexToInsert = strategy.pickNextColumn(board);
        if (indexToInsert < 0) {
            log.error("Unable to make a move, board is full, indexToInsert: {}", indexToInsert);
        }
        try {
            log.info("{} Moving coin to column - {}", name, indexToInsert);
            board.insertAtColumn(indexToInsert, coin);
        } catch (IllegalColumnIndexException | ColumnFullException e) {
            log.error("Unable to make a move {}", e);
            throw new InvalidMoveException(e);
        }
    }

}
