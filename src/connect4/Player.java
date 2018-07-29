package connect4;

import connect4.exceptions.InvalidMoveException;

public interface Player {

    String name();

    CoinTypes getType();

    void makeAMove(Board board) throws InvalidMoveException;

}
