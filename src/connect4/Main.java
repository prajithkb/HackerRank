package connect4;

import java.io.IOException;
import java.util.Optional;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.terminal.Terminal;
import connect4.dagger.BoardModule;
import connect4.dagger.Connect4;
import connect4.dagger.DaggerConnect4;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

@Slf4j
public class Main {

    static void configureLogger() {
        ConsoleAppender console = new ConsoleAppender();
        //configure the appender
        String PATTERN = "%d [%p|%c|%C{1}] %m%n";
        console.setLayout(new PatternLayout(PATTERN));
        console.setThreshold(Level.TRACE);
        console.activateOptions();
        Logger.getRootLogger().addAppender(console);
    }

    public static void main(String[] args) throws IOException {
        configureLogger();
        val connect4 = DaggerConnect4.builder().build();
        val terminal = connect4.terminal();
        val game = connect4.game();
        drawLine(terminal);
        val boardPosition = terminal.getCursorPosition();
        printBoard(terminal, game, boardPosition);
        drawLine(terminal);
        val endTerminalPosition = terminal.getCursorPosition();
        Game.EventListener printBoard = (status, nextPlayer, winner) -> {
            try {
                Thread.sleep(1000);
                printBoard(terminal, game, boardPosition);
                printStatus(terminal,status, nextPlayer, winner, endTerminalPosition);
            } catch (IOException | InterruptedException e) {
                log.error("{}", e);
            }
        };
        game.addEventListener(printBoard);
        game.start();
        terminal.flush();
        terminal.close();
        log.info("Stopping");
    }

    private static void drawLine(Terminal terminal) throws IOException {
        for (int i = 0; i < 70; i++) {
            terminal.putCharacter('#');
        }
        terminal.putCharacter('\n');
        terminal.flush();

    }

    private static void printStatus(Terminal terminal,
            GameStatus status,
            Player nextPlayer,
            Optional<Player> winner,
            TerminalPosition position)
            throws IOException {
        terminal.setCursorPosition(position);
        String statusMessage = defaultMessage(nextPlayer, status);
        if( status == GameStatus.WIN){
            statusMessage = victoryMessage(winner.get());
        }
        for (int i = 0; i < statusMessage.length(); i++) {
            terminal.putCharacter(statusMessage.charAt(i));
        }
        terminal.flush();
    }

    private static String defaultMessage(Player nextPlayer, GameStatus status){
        return String.format("Game status: %s, %s to play",status, nextPlayer.name());
    }

    private static String victoryMessage(Player winner){
        return String.format("Game status: %s won!",winner.name());
    }


    private static void printBoard(Terminal terminal, Game game, TerminalPosition boardPosition) throws IOException {
        terminal.setCursorPosition(boardPosition);
        char[][] boardAsString = game.getBoard().boardAsString();
        for (int i = 0; i < BoardModule.NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < BoardModule.NUMBER_OF_COLUMNS; j++) {
                terminal.putCharacter('\t');
                terminal.putCharacter('|');
                terminal.putCharacter(boardAsString[i][j]);
            }
            terminal.putCharacter('\n');
        }
        terminal.putCharacter('\n');
        terminal.flush();
    }

}
