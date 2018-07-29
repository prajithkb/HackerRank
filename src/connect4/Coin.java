package connect4;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode
public class Coin {

    @Getter private final CoinTypes type;

    @Getter @Builder.Default private char valueAsString = ' ';

}
