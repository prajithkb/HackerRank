package connect4.dagger;

import javax.inject.Singleton;

import com.googlecode.lanterna.terminal.Terminal;
import connect4.Game;
import dagger.Component;

@Component(modules = { GameModule.class, CoinModule.class, BoardModule.class, PlayerModule.class, TerminalModule.class })
@Singleton
public interface Connect4 {

    Game game();

    Terminal terminal();

}
