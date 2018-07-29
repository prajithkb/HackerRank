package connect4.impl;

import connect4.Coin;
import connect4.CoinFactory;
import connect4.CoinTypes;

public class DefaultCoinFactory implements CoinFactory {
    @Override
    public Coin getCoin(CoinTypes coinType) {
        return Coin.builder().valueAsString(coinType.value()).type(coinType).build();
    }
}
