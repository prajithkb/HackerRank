package connect4;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
public class StatusCheckerResult {

    @NonNull @Getter private final GameStatus status;

    @Builder.Default @Getter private Optional<Player> winner = Optional.empty();
}
