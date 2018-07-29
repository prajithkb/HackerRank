package connect4.dagger;

import java.util.List;
import javax.inject.Singleton;

import com.google.common.collect.Lists;
import connect4.Board;
import connect4.Coin;
import connect4.Column;
import connect4.impl.DefaultBoard;
import connect4.impl.DefaultColumn;
import dagger.Module;
import dagger.Provides;
import lombok.val;

@Module
public class BoardModule {

    public static final int NUMBER_OF_COLUMNS = 7;

    public static final int NUMBER_OF_ROWS = 6;

    @Singleton
    @Provides
    Board providesBoard(List<Column> columns) {
        return new DefaultBoard(columns);
    }

    @Singleton
    @Provides
    List<Column> providesColumns() {
        val columns = Lists.<Column>newArrayList();
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            List<Coin> coins = Lists.<Coin>newArrayList();
            columns.add(new DefaultColumn(coins, String.valueOf(i)));
        }
        return columns;
    }

}
