package connect4.dagger;

import java.util.List;
import javax.inject.Named;
import javax.inject.Singleton;

import com.google.common.collect.ImmutableList;
import connect4.CoinFactory;
import connect4.CoinTypes;
import connect4.Player;
import connect4.Strategy;
import connect4.impl.DefaultPlayer;
import connect4.impl.RandomStrategy;
import connect4.impl.SequentialStrategy;
import dagger.Module;
import dagger.Provides;
import lombok.val;

@Module
public class PlayerModule {

    @Provides
    @Named("player1")
    Player providesPlayer1(CoinFactory coinFactory, @Named("Sequential") Strategy strategy) {
        return new DefaultPlayer(String.format("Player-1:[%c]", CoinTypes.Red.value()), CoinTypes.Red, coinFactory, strategy);
    }

    @Provides
    @Named("player2")
    Player providesPlayer2(CoinFactory coinFactory, @Named("Random") Strategy strategy) {
        return new DefaultPlayer(String.format("Player-2:[%c]", CoinTypes.Yellow.value()), CoinTypes.Yellow, coinFactory, strategy);
    }

    @Provides
    @Singleton
    List<Player> providesPlayers(@Named("player1") Player player1, @Named("player2") Player player2) {
        val players = ImmutableList.of(player1, player2);
        return players;
    }

    @Provides
    @Named("Sequential")
    Strategy providesSimpleStrategy() {
        return new SequentialStrategy();
    }

    @Provides
    @Named("Random")
    Strategy providesRandomStrategy() {
        return new RandomStrategy();
    }
}
