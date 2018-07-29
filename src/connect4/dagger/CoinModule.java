package connect4.dagger;

import javax.inject.Singleton;

import connect4.CoinFactory;
import connect4.impl.DefaultCoinFactory;
import dagger.Module;
import dagger.Provides;

@Module
public class CoinModule {

    public static final int NUMBER_OF_COINS = 6;

    @Provides
    @Singleton
    CoinFactory providesCoinFactory() {
        return new DefaultCoinFactory();
    }

}
