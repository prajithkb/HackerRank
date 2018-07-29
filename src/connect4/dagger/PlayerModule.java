package connect4.dagger;

import javax.inject.Named;
import javax.inject.Singleton;

import connect4.CoinFactory;
import connect4.CoinTypes;
import connect4.Player;
import connect4.Strategy;
import connect4.impl.DefaultPlayer;
import connect4.impl.SimpleStrategy;
import dagger.Module;
import dagger.Provides;

@Module
public class PlayerModule {

    @Provides
    @Singleton
    @Named("player1")
    Player providesPlayer1(CoinFactory coinFactory, Strategy strategy) {
        return new DefaultPlayer("Player1", CoinTypes.Red, coinFactory, strategy);
    }

    @Provides
    @Singleton
    @Named("player2")
    Player providesPlayer2(CoinFactory coinFactory, Strategy strategy) {
        return new DefaultPlayer("Player2", CoinTypes.Yellow, coinFactory, strategy);
    }

    @Provides
    @Singleton
    Strategy providesSimpleStrategy() {
        return new SimpleStrategy();
    }
}
