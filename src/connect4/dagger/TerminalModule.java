package connect4.dagger;

import java.io.IOException;
import javax.inject.Singleton;

import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import dagger.Module;
import dagger.Provides;

@Module
public class TerminalModule {

    @Provides
    @Singleton
    Terminal providesUnixTerminal() {
        try {
            return new DefaultTerminalFactory().createTerminal();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
