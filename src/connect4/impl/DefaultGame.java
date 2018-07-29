package connect4.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.inject.Inject;

import connect4.Board;
import connect4.Game;
import connect4.GameStatus;
import connect4.Player;
import connect4.StatusChecker;
import connect4.StatusCheckerResult;
import connect4.exceptions.InvalidMoveException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultGame implements Game {

    private final String id = UUID.randomUUID().toString();

    private final List<Player> players;

    private final Board board;

    private final StatusChecker statusChecker;

    private GameStatus status = GameStatus.CONTINUE;

    private int firstPlayer = new Random().nextInt(10) % 2;

    private final CopyOnWriteArrayList<EventListener> listeners = new CopyOnWriteArrayList<>();

    @Inject
    public DefaultGame(List<Player> players, Board board, StatusChecker statusChecker) {
        this.players = players;
        this.board = board;
        this.statusChecker = statusChecker;
        log.info("Created game instance - {}", id);
    }

    private void gameLoop() {
        while (true) {
            Player nextPlayer = nextPlayer();
            StatusCheckerResult result = null;
            try {
                nextPlayer.makeAMove(board);
                result = statusChecker.check(this);
                status = result.getStatus();
            } catch (InvalidMoveException e) {
                result = StatusCheckerResult.builder().status(GameStatus.ERROR).build();
                status = GameStatus.ERROR;
            }
            log.info("Game status - {}", status);
            fireMoveComplete(status, nextPlayer, result.getWinner());
            switch (status) {
                case WIN:
                    log.info("{} won!", result.getWinner().get().name());
                    return;
                case DRAW:
                    log.info("Its a draw!");
                    return;
                case ERROR:
                    log.error("Its an error!");
                    return;
                case CONTINUE:
                    log.info("continuing");
            }
        }
    }

    private Player nextPlayer() {
        return players.get((firstPlayer++) % 2);
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public void start() {
        log.info("Starting the game - {}", id);
        gameLoop();
        log.info("Game - {} over!", id);
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public void addEventListener(EventListener eventListener) {
        listeners.add(eventListener);
    }

    @Override
    public void removeEventListener(EventListener eventListener) {
        listeners.remove(eventListener);
    }

    @Override
    public GameStatus getStatus() {
        return status;
    }

    private void fireMoveComplete(GameStatus status, Player currentPlayer, Optional<Player> winner) {
        for (EventListener listener : listeners) {
            listener.onMoveComplete(status, currentPlayer, winner);
        }
    }
}
