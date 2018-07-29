package connect4;

import java.util.List;
import java.util.Optional;

public interface Game {

    String id();

    void start();

    List<Player> getPlayers();

    Board getBoard();

    void addEventListener(EventListener eventListener);

    void removeEventListener(EventListener eventListener);

    GameStatus getStatus();

    @FunctionalInterface
    interface EventListener {

        void onMoveComplete(GameStatus currentStatus, Player currentPlayer, Optional<Player> winner);

    }

}
