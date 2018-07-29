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
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class DefaultStatusChecker implements StatusChecker {

    Map<CoinTypes, Player> coinTypesPlayerMap;

    public DefaultStatusChecker(List<Player> players) {
        coinTypesPlayerMap = players.stream().collect(Collectors.toMap(Player::getType, Function.identity()));
    }

    @Override
    public StatusCheckerResult check(Game game) {
        val statusCheckerResultBuilder = StatusCheckerResult.builder().status(GameStatus.CONTINUE);
        for (int i = 0; i < BoardModule.NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < BoardModule.NUMBER_OF_COLUMNS; j++) {
                boolean redWon = didWin(game.getBoard(), CoinTypes.Red, i, j);
                boolean yellowWon = didWin(game.getBoard(), CoinTypes.Yellow, i, j);
                if (redWon) {
                    statusCheckerResultBuilder.winner(Optional.of(coinTypesPlayerMap.get(CoinTypes.Red))).status(GameStatus.WIN);
                } else if (yellowWon) {
                    statusCheckerResultBuilder.winner(Optional.of(coinTypesPlayerMap.get(CoinTypes.Yellow))).status(GameStatus.WIN);

                }
            }
        }
        return statusCheckerResultBuilder.build();
    }

    public boolean didWin(Board board, CoinTypes typeToCheck, int row, int col) {
        return didWin(board, typeToCheck, row, col, 1, 0) || didWin(board, typeToCheck, row, col, -1, 0) || didWin(board,
                typeToCheck,
                row,
                col,
                0,
                1) || didWin(board, typeToCheck, row, col, 0, -1) || didWin(board, typeToCheck, row, col, 1, 1) || didWin(board,
                typeToCheck,
                row,
                col,
                -1,
                -1) || didWin(board, typeToCheck, row, col, -1, 1) || didWin(board, typeToCheck, row, col, 1, -1);
    }

    public boolean didWin(Board board, CoinTypes typeToCheck, int row, int col, int rowDelta, int colDelta) {
        boolean match = true;
        int matches = 0;
        while (row < BoardModule.NUMBER_OF_ROWS && row >= 0 && col < BoardModule.NUMBER_OF_COLUMNS && col >= 0) {
            val coin = board.getCoinAt(row, col);
            CoinTypes type = coin == null ? null : coin.getType();
            log.debug("typeToCheck {}, checkedAgainst {}, [{},{}]", typeToCheck, type, row, col);
            if (typeToCheck != type && match) {
                break;
            } else if (typeToCheck == type) {
                match = true;
                matches++;
            }
            row += rowDelta;
            col += colDelta;

        }
        return matches == 4;

    }
}
