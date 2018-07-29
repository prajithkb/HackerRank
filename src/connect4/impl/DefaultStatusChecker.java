package connect4.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import connect4.Board;
import connect4.Coin;
import connect4.CoinTypes;
import connect4.Game;
import connect4.GameStatus;
import connect4.Player;
import connect4.StatusChecker;
import connect4.StatusCheckerResult;
import connect4.dagger.BoardModule;
import lombok.val;

public class DefaultStatusChecker implements StatusChecker {

    Map<CoinTypes, Player> coinTypesPlayerMap;

    DefaultStatusChecker(List<Player> players){
        coinTypesPlayerMap = players.stream().collect(Collectors.toMap(Player::getType, Function.identity()));
    }

    @Override
    public StatusCheckerResult check(Game game) {
        boolean connect4Found = false;
        List<Player> players = game.getPlayers();
        val statuCheckerResultBuilder = StatusCheckerResult.builder().status(GameStatus.CONTINUE);
        for (int i = 0; i < BoardModule.NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < BoardModule.NUMBER_OF_COLUMNS; j++) {
                boolean redWon = didWin(game.getBoard(), CoinTypes.Red,i, j) ;
                boolean yellowWon = didWin(game.getBoard(), CoinTypes.Yellow,i, j);
                if(redWon){
                    statuCheckerResultBuilder
                            .winner(Optional.of(coinTypesPlayerMap.get(CoinTypes.Red)))
                            .status(GameStatus.WIN);
                } else if(yellowWon){
                    statuCheckerResultBuilder
                            .winner(Optional.of(coinTypesPlayerMap.get(CoinTypes.Yellow)))
                            .status(GameStatus.WIN);

                }
            }
        }
        return statuCheckerResultBuilder.build();
    }

    public boolean didWin(Board board, CoinTypes check, int row, int col) {
        return didWin(board, check, row, col, 1, 0) ||
                didWin(board, check, row, col, -1, 0) ||
                didWin(board, check, row, col, 0, 1) ||
                didWin(board, check, row, col, 0, -1) ||
                didWin(board, check, row, col, 1, 1) ||
                didWin(board, check, row, col, -1, -1) ||
                didWin(board, check, row, col, -1, 1) ||
                didWin(board, check, row, col, 1, -1);
    }

    public boolean didWin(Board board, CoinTypes check, int row, int col, int rowDelta, int colDelta) {
        boolean win = true;
        for (int count = 0; count < 4; count++) {
            if (row < BoardModule.NUMBER_OF_ROWS && row >= 0 && col < BoardModule.NUMBER_OF_COLUMNS && col >= 0) {
                val columns = board.getColumns();
                CoinTypes test = board.getCoinAt(row, col).getType();
                if (!test.equals(check)) {
                    win = false;
                    break;
                }
            }
            row += rowDelta;
            col += colDelta;
        }
        return win;

    }
}
