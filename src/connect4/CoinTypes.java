package connect4;

public enum CoinTypes {
    Red('X'),
    Yellow('O'),
    Empty(' ');

    private final char value;

    CoinTypes(char value) {
        this.value = value;
    }

    public char value() {
        return value;
    }
}
