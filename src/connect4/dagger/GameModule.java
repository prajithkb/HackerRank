package connect4.dagger;

import javax.inject.Named;
import javax.inject.Singleton;

import com.google.common.collect.ImmutableList;
import connect4.Board;
import connect4.Game;
import connect4.Player;
import connect4.StatusChecker;
import connect4.impl.DefaultGame;
import connect4.impl.DefaultStatusChecker;
import dagger.Module;
import dagger.Provides;
import lombok.val;

@Module
public class GameModule {

    @Provides
    @Singleton
    Game providesGame(@Named("player1") Player player1, @Named("player2") Player player2, Board board, StatusChecker statusChecker) {
        val players = ImmutableList.of(player1, player2);
        return new DefaultGame(players, board, statusChecker);
    }

    @Provides
    @Singleton
    StatusChecker providesStatusChecker() {
        return new DefaultStatusChecker();
    }
}
